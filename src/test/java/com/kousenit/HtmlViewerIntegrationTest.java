package com.kousenit;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Integration test to generate a sample HTML viewer that demonstrates
 * the browser viewing capability for locally stored operas.
 */
@Tag("integration")
class HtmlViewerIntegrationTest {

    @Test
    void generateSampleHtmlViewer() throws IOException {
        // Create a sample opera demonstrating all features
        String premise = """
                In a post-climate Connecticut where lush jungles have reclaimed the land, 
                an intrepid explorer seeks the legendary lost city of Hartford. Along the way, 
                she encounters a mysterious poet and a relentless government agent.
                """;
        
        List<Opera.Scene> scenes = List.of(
            new Opera.Scene(1, "Jungle Awakening", "GPT-5.2", """
                [The stage is set in a dense jungle with overgrown ruins in the background. Morning light filters through the canopy.]
                
                SANDRA (soprano, explorer):
                Through emerald vines and ancient stone,
                I venture forth, though I'm alone,
                The lost Hartford calls to me,
                In dreams of what once used to be.
                
                [She examines a moss-covered street sign that reads "Main St"]
                
                What wonders lie beneath this green?
                What stories in these ruins unseen?
                """),
            
            new Opera.Scene(2, "The Poet's Song", "Claude Opus 4.5", """
                [LUCIAN, a young man with wild hair and weathered clothes, emerges from behind a banyan tree]
                
                LUCIAN (tenor, poet):
                Who dares disturb the jungle's sleep?
                Where secrets old and silent keep?
                
                SANDRA (soprano):
                I am Sandra, seeker of the past,
                Of Hartford's glory, lost so fast.
                
                LUCIAN (approaching with curiosity):
                Hartford's daughter seeks her home,
                Through tangled green, no more to roam.
                I know these paths, these hidden ways,
                I'll guide you through the emerald maze.
                """),
            
            new Opera.Scene(3, "Love in the Ruins", "GPT-5.2", """
                [They journey together through spectacular jungle scenery, discovering ancient buildings]
                
                SANDRA & LUCIAN (duet):
                Together we'll uncover the truth,
                Of Hartford's vanished, golden youth.
                In every stone and every tree,
                The city's heart still beats, still free.
                
                [They hold hands as they climb onto an overgrown highway overpass]
                
                SANDRA (gazing at LUCIAN):
                In you I find not just a guide,
                But someone true to stand beside.
                
                LUCIAN (taking her hand):
                And I have found in you a light,
                That brightens even jungle night.
                """)
        );
        
        Opera sampleOpera = new Opera("Hartford Ascending (Sample)", premise, scenes);
        
        // Create output directory for the sample
        Path sampleDir = Paths.get("src/main/resources/sample_html_viewer");
        Files.createDirectories(sampleDir);
        
        // Generate the HTML viewer
        Path htmlPath = HtmlOperaViewer.generateHtmlViewer(sampleOpera, sampleDir);
        
        System.out.println("\n✅ Sample HTML viewer generated successfully!");
        System.out.println("📍 Location: " + htmlPath.toAbsolutePath());
        System.out.println("\n🌐 To view in your browser:");
        System.out.println("   1. Open your file browser");
        System.out.println("   2. Navigate to: " + htmlPath.toAbsolutePath());
        System.out.println("   3. Double-click the file to open in your default browser");
        System.out.println("\n📝 Note: All files are stored locally on your PC.");
        System.out.println("   No data is sent to external servers when viewing.");
    }
}
