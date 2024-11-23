package com.example.Ai.Planet.Assignment.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    @Value("${groq.api.key}")
    private String apiKey;

    public String getGroqResponse(String pdfText, String userQuery) {
        try {
            // Construct the prompt
            String prompt = "Given the following document text: \n" + pdfText + "\nAnswer the question: " + userQuery;

            // Create the request body
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestBody = objectMapper.createObjectNode();

            // Set the model to a valid one from the Groq-supported list
            requestBody.put("model", "llama3-8b-8192"); // Replace with the model of your choice

            // Define the 'messages' array structure
            ArrayNode messagesArray = requestBody.putArray("messages");
            ObjectNode systemMessage = messagesArray.addObject();
            systemMessage.put("role", "system").put("content", "You are a helpful assistant.");

            ObjectNode userMessage = messagesArray.addObject();
            userMessage.put("role", "user").put("content", prompt);

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Create the HTTP entity
            HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);

            // Send the POST request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, httpEntity, String.class);

            // Parse the API response
            JsonNode responseBody = objectMapper.readTree(response.getBody());

            // Extract the generated text from the response (adjust if needed based on the Groq API response structure)
            return responseBody.path("choices").get(0).path("message").path("content").asText();

        } catch (HttpClientErrorException e) {
            // Handle HTTP errors
            return "Error calling the Groq API: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }
}
