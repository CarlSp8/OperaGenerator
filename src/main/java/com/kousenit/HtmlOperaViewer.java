package com.kousenit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Generates standalone HTML viewer for operas that runs entirely in the browser.
 * All assets (images, audio) are embedded or referenced locally - no external dependencies.
 */
public class HtmlOperaViewer {

    /**
     * Generates a standalone HTML file for viewing the opera in a browser.
     * The HTML file includes embedded styling and JavaScript for a rich viewing experience.
     * All resources are stored locally on the user's PC.
     * 
     * @param opera The opera to generate HTML for
     * @param operaDir The directory containing the opera files
     * @return Path to the generated HTML file
     * @throws IOException If file operations fail
     */
    public static Path generateHtmlViewer(Opera opera, Path operaDir) throws IOException {
        String slug = slugify(opera.title());
        Path htmlPath = operaDir.resolve(slug + "_viewer.html");
        
        StringBuilder html = new StringBuilder();
        
        // HTML Header with embedded CSS
        html.append("""
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>%s - Opera Viewer</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Georgia', 'Times New Roman', serif;
            line-height: 1.8;
            background: linear-gradient(135deg, #1a1a2e 0%%, #16213e 100%%);
            color: #e0e0e0;
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: #0f1419;
            border-radius: 15px;
            box-shadow: 0 10px 50px rgba(0, 0, 0, 0.5);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #8e2de2 0%%, #4a00e0 100%%);
            padding: 40px;
            text-align: center;
            border-bottom: 3px solid #ffd700;
        }
        
        .header h1 {
            font-size: 3em;
            color: #ffffff;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            margin-bottom: 10px;
        }
        
        .header .subtitle {
            font-size: 1.2em;
            color: #ffd700;
            font-style: italic;
        }
        
        .audio-controls {
            background: #1a1f2e;
            padding: 20px;
            border-bottom: 2px solid #333;
            text-align: center;
        }
        
        .audio-controls audio {
            width: 100%%;
            max-width: 600px;
            margin: 10px 0;
        }
        
        .audio-label {
            color: #ffd700;
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }
        
        .content {
            padding: 40px;
        }
        
        .premise {
            background: #1a1f2e;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 40px;
            border-left: 5px solid #8e2de2;
        }
        
        .premise h2 {
            color: #ffd700;
            margin-bottom: 15px;
            font-size: 1.8em;
        }
        
        .premise p {
            font-size: 1.1em;
            line-height: 1.8;
        }
        
        .scene {
            margin-bottom: 60px;
            padding: 30px;
            background: #1a1f2e;
            border-radius: 10px;
            border-left: 5px solid #4a00e0;
        }
        
        .scene-header {
            border-bottom: 2px solid #ffd700;
            padding-bottom: 15px;
            margin-bottom: 25px;
        }
        
        .scene-header h3 {
            color: #ffd700;
            font-size: 2em;
            margin-bottom: 10px;
        }
        
        .scene-author {
            color: #8e2de2;
            font-style: italic;
            font-size: 1.1em;
        }
        
        .scene-illustration {
            width: 100%%;
            max-width: 800px;
            height: auto;
            border-radius: 10px;
            margin: 20px auto;
            display: block;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.5);
        }
        
        .scene-content {
            margin-top: 25px;
            font-size: 1.05em;
        }
        
        .scene-content blockquote {
            border-left: 3px solid #ffd700;
            padding-left: 20px;
            margin: 15px 0;
            color: #b8b8b8;
            font-style: italic;
        }
        
        .scene-content strong {
            color: #ffd700;
        }
        
        .scene-audio {
            margin: 20px 0;
            padding: 15px;
            background: #0f1419;
            border-radius: 8px;
        }
        
        .critique {
            background: #1a1f2e;
            padding: 30px;
            border-radius: 10px;
            margin-top: 40px;
            border-left: 5px solid #e02d8e;
        }
        
        .critique h2 {
            color: #e02d8e;
            margin-bottom: 20px;
            font-size: 1.8em;
        }
        
        .footer {
            background: #0a0e14;
            padding: 30px;
            text-align: center;
            color: #666;
            border-top: 2px solid #333;
        }
        
        .footer p {
            margin: 5px 0;
        }
        
        .metadata {
            background: #1a1f2e;
            padding: 20px;
            border-radius: 10px;
            margin-top: 40px;
            font-size: 0.9em;
            color: #999;
        }
        
        .metadata h3 {
            color: #ffd700;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🎭 %s</h1>
            <p class="subtitle">An AI-Generated Opera</p>
        </div>
""".formatted(opera.title(), opera.title()));
        
        // Audio controls for opera introduction if it exists
        Path introAudio = operaDir.resolve("opera_introduction.mp3");
        if (Files.exists(introAudio)) {
            html.append("""
        <div class="audio-controls">
            <span class="audio-label">🎙️ Opera Introduction</span>
            <audio controls>
                <source src="opera_introduction.mp3" type="audio/mpeg">
                Your browser does not support the audio element.
            </audio>
        </div>
""");
        }
        
        html.append("""
        <div class="content">
            <div class="premise">
                <h2>📖 Premise</h2>
                <p>%s</p>
            </div>
""".formatted(escapeHtml(opera.premise())));
        
        // Generate each scene
        for (Opera.Scene scene : opera.scenes()) {
            html.append(generateSceneHtml(scene, operaDir));
        }
        
        // Add critique if it exists
        String critiqueFile = slug + "_critique.md";
        Path critiquePath = operaDir.resolve(critiqueFile);
        if (Files.exists(critiquePath)) {
            String critique = Files.readString(critiquePath);
            html.append("""
            <div class="critique">
                <h2>📰 Critical Review</h2>
                %s
            </div>
""".formatted(markdownToHtml(critique)));
            
            // Add critic audio if it exists
            Path criticAudio = operaDir.resolve("critic_review_audio.mp3");
            if (Files.exists(criticAudio)) {
                html.append("""
                <div class="audio-controls">
                    <span class="audio-label">🎙️ Critic Audio Review</span>
                    <audio controls>
                        <source src="critic_review_audio.mp3" type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                </div>
""");
            }
        }
        
        // Footer with metadata
        html.append("""
            <div class="metadata">
                <h3>ℹ️ Generation Information</h3>
                <p><strong>Scenes:</strong> %d</p>
                <p><strong>Generated by:</strong> GPT-5.2 and Claude Opus 4.5 (alternating)</p>
                <p><strong>Illustrations:</strong> Google Gemini Nano Banana</p>
                <p><strong>Narration:</strong> ElevenLabs Text-to-Speech</p>
                <p><strong>Local Storage:</strong> All files stored on your PC</p>
            </div>
        </div>
        
        <div class="footer">
            <p>🎵 Generated by Opera Generator 🎵</p>
            <p>All assets stored locally on your computer</p>
            <p>No data sent to external servers during viewing</p>
        </div>
    </div>
</body>
</html>
""".formatted(opera.scenes().size()));
        
        // Write the HTML file
        Files.writeString(htmlPath, html.toString());
        System.out.println("✅ HTML viewer created: " + htmlPath);
        
        return htmlPath;
    }
    
    private static String generateSceneHtml(Opera.Scene scene, Path operaDir) throws IOException {
        StringBuilder html = new StringBuilder();
        
        html.append("""
            <div class="scene" id="scene-%d">
                <div class="scene-header">
                    <h3>Scene %d: %s</h3>
                    <p class="scene-author">Written by: %s</p>
                </div>
""".formatted(scene.number(), scene.number(), escapeHtml(scene.title()), escapeHtml(scene.author())));
        
        // Add illustration if it exists
        Path imagePath = operaDir.resolve(scene.getImageFileName());
        if (Files.exists(imagePath)) {
            html.append("""
                <img src="%s" alt="Scene %d Illustration" class="scene-illustration">
""".formatted(scene.getImageFileName(), scene.number()));
        }
        
        // Add scene audio if it exists
        Path sceneAudio = operaDir.resolve("scene_" + scene.number() + "_narration.mp3");
        if (Files.exists(sceneAudio)) {
            html.append("""
                <div class="scene-audio">
                    <span class="audio-label">🎙️ Scene Narration</span>
                    <audio controls style="width: 100%%;">
                        <source src="scene_%d_narration.mp3" type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                </div>
""".formatted(scene.number()));
        }
        
        // Add scene content with formatting
        String formattedContent = LibrettoWriter.formatSceneContent(scene.content());
        html.append("""
                <div class="scene-content">
                    %s
                </div>
            </div>
""".formatted(markdownToHtml(formattedContent)));
        
        return html.toString();
    }
    
    /**
     * Converts basic markdown to HTML. Handles common markdown patterns used in librettos.
     */
    private static String markdownToHtml(String markdown) {
        if (markdown == null) return "";
        
        String html = markdown;
        
        // Handle headers
        html = html.replaceAll("(?m)^### (.+)$", "<h3>$1</h3>");
        html = html.replaceAll("(?m)^## (.+)$", "<h2>$1</h2>");
        html = html.replaceAll("(?m)^# (.+)$", "<h1>$1</h1>");
        
        // Handle bold
        html = html.replaceAll("\\*\\*(.+?)\\*\\*", "<strong>$1</strong>");
        
        // Handle italic
        html = html.replaceAll("\\*(.+?)\\*", "<em>$1</em>");
        
        // Handle blockquotes
        html = html.replaceAll("(?m)^> (.+)$", "<blockquote>$1</blockquote>");
        
        // Handle horizontal rules
        html = html.replaceAll("(?m)^---$", "<hr>");
        
        // Handle line breaks
        html = html.replaceAll("<br>", "<br>");
        
        // Handle paragraphs (double newlines)
        html = html.replaceAll("(?m)^([^<\n].+)$", "<p>$1</p>");
        
        return html;
    }
    
    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
    
    private static String slugify(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "_");
    }
}
