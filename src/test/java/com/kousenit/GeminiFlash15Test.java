package com.kousenit;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import com.kousenit.tags.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@EnabledIfEnvironmentVariable(named = "GOOGLEAI_API_KEY", matches = ".+")
class GeminiFlash15Test {

    @Test
    @IntegrationTest
    void testGeminiFlash15BasicQuery() {
        ChatModel model = AiModels.GEMINI_FLASH_1_5;
        
        ChatResponse response = model.chat(
                UserMessage.from("Write a brief one-sentence summary of what opera is."));
        
        assertThat(response).isNotNull();
        assertThat(response.aiMessage()).isNotNull();
        assertThat(response.aiMessage().text()).isNotEmpty();
        
        // Verify response contains opera-related content
        String responseText = response.aiMessage().text().toLowerCase();
        assertThat(responseText).containsAnyOf("music", "drama", "performance", 
                "theatrical", "stage", "singing", "art", "vocal");
        
        System.out.println("✅ Gemini Flash 1.5 Response: " + response.aiMessage().text());
        System.out.println("Token Usage: " + response.tokenUsage());
    }

    @Test
    @IntegrationTest
    void testGeminiFlash15OperaGeneration() {
        ChatModel model = AiModels.GEMINI_FLASH_1_5;
        
        String prompt = """
                Write a brief, dramatic opera scene (2-3 lines) about a robot 
                discovering emotions for the first time. Include character name 
                and stage direction.
                """;
        
        ChatResponse response = model.chat(UserMessage.from(prompt));
        
        assertThat(response).isNotNull();
        assertThat(response.aiMessage().text()).isNotEmpty();
        assertThat(response.aiMessage().text().length()).isGreaterThan(50);
        
        // Verify response contains expected opera scene elements
        String responseText = response.aiMessage().text();
        assertThat(responseText).matches("(?s).*\\[.*\\].*"); // Stage directions in brackets
        
        System.out.println("✅ Gemini Flash 1.5 Opera Scene:");
        System.out.println(response.aiMessage().text());
        System.out.println("\nToken Usage: " + response.tokenUsage());
    }
}
