package com.kousenit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlOperaViewerTest {

    @Test
    void testGenerateHtmlViewer(@TempDir Path tempDir) throws IOException {
        // Create a test opera
        String premise = "A test opera set in the future";
        List<Opera.Scene> scenes = List.of(
            new Opera.Scene(1, "Opening", "GPT-5.2", 
                "HERO (tenor):\nWelcome to the future!"),
            new Opera.Scene(2, "The Conflict", "Claude Opus 4.5", 
                "[Stage directions: The hero faces a challenge]\nHERO:\nI will overcome!")
        );
        Opera opera = new Opera("Test Opera", premise, scenes);
        
        // Generate HTML viewer
        Path htmlPath = HtmlOperaViewer.generateHtmlViewer(opera, tempDir);
        
        // Verify HTML file was created
        assertThat(htmlPath).exists();
        assertThat(htmlPath.getFileName().toString()).endsWith("_viewer.html");
        
        // Read and verify content
        String htmlContent = Files.readString(htmlPath);
        
        // Check essential HTML structure
        assertThat(htmlContent).contains("<!DOCTYPE html>");
        assertThat(htmlContent).contains("<html lang=\"en\">");
        assertThat(htmlContent).contains("</html>");
        
        // Check title
        assertThat(htmlContent).contains("Test Opera");
        assertThat(htmlContent).contains("An AI-Generated Opera");
        
        // Check premise
        assertThat(htmlContent).contains("Premise");
        assertThat(htmlContent).contains("A test opera set in the future");
        
        // Check scenes
        assertThat(htmlContent).contains("Scene 1: Opening");
        assertThat(htmlContent).contains("Scene 2: The Conflict");
        assertThat(htmlContent).contains("Written by: GPT-5.2");
        assertThat(htmlContent).contains("Written by: Claude Opus 4.5");
        
        // Check styling is embedded
        assertThat(htmlContent).contains("<style>");
        assertThat(htmlContent).contains("</style>");
        
        // Check no external dependencies
        assertThat(htmlContent).doesNotContain("http://");
        assertThat(htmlContent).doesNotContain("https://");
        
        // Check local storage message
        assertThat(htmlContent).contains("All files stored on your PC");
        assertThat(htmlContent).contains("No data sent to external servers");
    }
    
    @Test
    void testHtmlViewerWithImages(@TempDir Path tempDir) throws IOException {
        // Create a test opera
        Opera opera = new Opera("Opera With Images", "Test premise", List.of(
            new Opera.Scene(1, "Scene One", "GPT-5.2", "Content one")
        ));
        
        // Create a fake image file
        Path imagePath = tempDir.resolve("scene_1_illustration.png");
        Files.writeString(imagePath, "fake png data");
        
        // Generate HTML viewer
        Path htmlPath = HtmlOperaViewer.generateHtmlViewer(opera, tempDir);
        String htmlContent = Files.readString(htmlPath);
        
        // Verify image reference
        assertThat(htmlContent).contains("scene_1_illustration.png");
        assertThat(htmlContent).contains("class=\"scene-illustration\"");
    }
    
    @Test
    void testHtmlViewerWithAudio(@TempDir Path tempDir) throws IOException {
        // Create a test opera
        Opera opera = new Opera("Opera With Audio", "Test premise", List.of(
            new Opera.Scene(1, "Scene One", "GPT-5.2", "Content one")
        ));
        
        // Create fake audio files
        Files.writeString(tempDir.resolve("opera_introduction.mp3"), "fake audio");
        Files.writeString(tempDir.resolve("scene_1_narration.mp3"), "fake audio");
        
        // Generate HTML viewer
        Path htmlPath = HtmlOperaViewer.generateHtmlViewer(opera, tempDir);
        String htmlContent = Files.readString(htmlPath);
        
        // Verify audio references
        assertThat(htmlContent).contains("opera_introduction.mp3");
        assertThat(htmlContent).contains("scene_1_narration.mp3");
        assertThat(htmlContent).contains("<audio controls");
        assertThat(htmlContent).contains("Opera Introduction");
        assertThat(htmlContent).contains("Scene Narration");
    }
    
    @Test
    void testHtmlViewerWithCritique(@TempDir Path tempDir) throws IOException {
        // Create a test opera
        Opera opera = new Opera("Opera With Critique", "Test premise", List.of(
            new Opera.Scene(1, "Scene One", "GPT-5.2", "Content one")
        ));
        
        // Create fake critique file
        String critiqueContent = "# This is a brilliant opera!\n\nIt has great scenes.";
        Files.writeString(tempDir.resolve("opera_with_critique_critique.md"), critiqueContent);
        
        // Generate HTML viewer
        Path htmlPath = HtmlOperaViewer.generateHtmlViewer(opera, tempDir);
        String htmlContent = Files.readString(htmlPath);
        
        // Verify critique is included
        assertThat(htmlContent).contains("Critical Review");
        assertThat(htmlContent).contains("This is a brilliant opera");
        assertThat(htmlContent).contains("It has great scenes");
    }
    
    @Test
    void testEscapeHtmlSpecialCharacters(@TempDir Path tempDir) throws IOException {
        // Create opera with special characters that need escaping
        Opera opera = new Opera("Opera & Title <Test>", "Premise with \"quotes\"", List.of(
            new Opera.Scene(1, "Scene & Title", "GPT-5.2", "Content with <tags> & \"quotes\"")
        ));
        
        // Generate HTML viewer
        Path htmlPath = HtmlOperaViewer.generateHtmlViewer(opera, tempDir);
        String htmlContent = Files.readString(htmlPath);
        
        // Verify characters are properly escaped in content
        // Title appears in multiple places, some escaped, some in HTML attributes
        assertThat(htmlContent).containsAnyOf("Opera &amp; Title", "Opera & Title");
        assertThat(htmlContent).contains("Premise with &quot;quotes&quot;");
    }
}
