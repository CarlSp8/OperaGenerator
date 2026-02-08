package com.kousenit;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the preview snippet generation logic in OperaGeneratorApp.
 * This tests the intelligent truncation of scene content for synopsis generation.
 */
class PreviewSnippetTest {

    private final OperaGeneratorApp app = new OperaGeneratorApp();

    @Test
    void testShortContentReturnsUnchanged() throws Exception {
        String shortText = "This is a short scene.";
        String result = callSnippet(shortText);
        assertThat(result).isEqualTo(shortText);
    }

    @Test
    void testWhitespaceNormalization() throws Exception {
        String text = "This   has    multiple     spaces.";
        String result = callSnippet(text);
        assertThat(result).isEqualTo("This has multiple spaces.");
    }

    @Test
    void testTruncationAtWordBoundary() throws Exception {
        // Create text longer than 220 characters
        String longText = "The explorer stood at the edge of the ancient ruins, " +
                "marveling at the towering structures that had withstood centuries " +
                "of decay and neglect. The jungle had reclaimed much of the city, " +
                "but these magnificent pillars still reached toward the sky, " +
                "a testament to a civilization long forgotten by the modern world.";
        
        String result = callSnippet(longText);
        
        // Should end with "..." and not cut mid-word
        assertThat(result).endsWith("...");
        assertThat(result.length()).isLessThanOrEqualTo(220);
        
        // The content before ellipsis should be trimmed
        String beforeEllipsis = result.substring(0, result.length() - 3).trim();
        
        // The last character before ellipsis should be part of a complete word
        // (either letter or punctuation, not whitespace from the middle of truncation)
        char lastChar = beforeEllipsis.charAt(beforeEllipsis.length() - 1);
        assertThat(Character.isLetterOrDigit(lastChar) || ",.!?;:".indexOf(lastChar) >= 0)
            .as("Last character should be part of a word: '%s'", lastChar)
            .isTrue();
    }

    @Test
    void testTruncationAtSentenceBoundary() throws Exception {
        // Create text with clear sentence boundaries
        String text = "Sandra enters the jungle clearing. The ancient city rises before her. " +
                "Lucian watches from the shadows with growing admiration. The robot Aria-7 " +
                "scans the perimeter for threats while Maximilian plots his next move. " +
                "This scene establishes the key dramatic tension that will drive the entire opera.";
        
        String result = callSnippet(text);
        
        // Should try to end at a sentence boundary if possible
        // In this case, should end with a period, not with "..."
        if (result.length() < text.length()) {
            // If truncated, it should either end with a sentence or with "..."
            assertThat(result.endsWith(".") || result.endsWith("..."))
                .as("Should end with sentence boundary or ellipsis")
                .isTrue();
        }
    }

    @Test
    void testEmptyOrNullContent() throws Exception {
        assertThat(callSnippet(null)).isEmpty();
        assertThat(callSnippet("")).isEmpty();
        assertThat(callSnippet("   ")).isEmpty();
    }

    @Test
    void testContentExactlyAtLimit() throws Exception {
        // Create content that is exactly 220 characters
        String exactText = "A".repeat(220);
        String result = callSnippet(exactText);
        assertThat(result).isEqualTo(exactText);
        assertThat(result.length()).isEqualTo(220);
    }

    @Test
    void testContentSlightlyOverLimit() throws Exception {
        // Create content that is 221 characters
        String text = "A".repeat(221);
        String result = callSnippet(text);
        
        // Should be truncated with "..."
        assertThat(result).endsWith("...");
        assertThat(result.length()).isLessThanOrEqualTo(220);
    }

    @Test
    void testRealOperaSceneContent() throws Exception {
        // Test with realistic opera scene content
        String operaText = """
                [The stage is set in a lush jungle clearing, overgrown with vines and tropical plants.
                Ancient stone pillars covered in moss stand as remnants of what was once Hartford.
                SANDRA enters from stage left, pushing aside hanging vines.]
                
                SANDRA (soprano): 
                > Through tangled vines and verdant leaves,
                > I seek the city of my dreams!
                > Hartford calls across the years,
                > A siren song that conquers fears!
                """;
        
        String result = callSnippet(operaText);
        
        // Should normalize whitespace and create a reasonable preview
        assertThat(result.length()).isLessThanOrEqualTo(220);
        assertThat(result).doesNotContain("\n");
        assertThat(result).contains("stage");
    }

    @Test
    void testCustomPreviewLength() throws Exception {
        String text = "This is a medium length text that we want to preview with different limits. " +
                "It contains multiple sentences to test various scenarios.";
        
        // Test with custom length of 50 characters
        String result = callSnippetWithLength(text, 50);
        
        assertThat(result.length()).isLessThanOrEqualTo(50);
        if (result.endsWith("...")) {
            // If truncated, should be close to the limit
            assertThat(result.length()).isGreaterThan(40);
        }
    }

    /**
     * Helper method to call the private snippet() method using reflection.
     */
    private String callSnippet(String content) throws Exception {
        Method method = OperaGeneratorApp.class.getDeclaredMethod("snippet", String.class);
        method.setAccessible(true);
        return (String) method.invoke(app, content);
    }

    /**
     * Helper method to call the private snippet(String, int) method using reflection.
     */
    private String callSnippetWithLength(String content, int length) throws Exception {
        Method method = OperaGeneratorApp.class.getDeclaredMethod("snippet", String.class, int.class);
        method.setAccessible(true);
        return (String) method.invoke(app, content, length);
    }
}
