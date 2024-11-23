package com.example.Ai.Planet.Assignment.Controller;

import com.example.Ai.Planet.Assignment.Service.ChatService;
import com.example.Ai.Planet.Assignment.Service.PdfDocumentService;
import com.example.Ai.Planet.Assignment.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@org.springframework.web.bind.annotation.RestController
public class PdfDocumentController {

    private final PdfDocumentService pdfDocumentService;
    private final ChatService chatService;

    // Constructor-based dependency injection
    public PdfDocumentController(PdfDocumentService pdfDocumentService, ChatService chatService) {
        this.pdfDocumentService = pdfDocumentService;
        this.chatService = chatService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded PDF and extract text
            pdfDocumentService.savePdf(file);

            // Return success response
            return ResponseEntity.ok("File uploaded and text extracted successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing the PDF file.");
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestParam("query") String query) {
        try {
            // Retrieve the latest PDF text
            String pdfText = pdfDocumentService.getLatestPdfText();

            if (pdfText == null) {
                return ResponseEntity.status(400).body("No PDF uploaded.");
            }

            // Prefill the assistant message for structured responses
            String prompt = """
            Based on the uploaded PDF, respond in the following format:
            - **Summary**: Provide a brief overview.
            - **Key Points**: Highlight the key points as bullet points.
            - **Additional Notes**: Mention any relevant details or observations.

            Here is the query: "%s"
        """.formatted(query);

            // Send the prefilled message and extracted PDF text to Groq API
            String response = chatService.getGroqResponse(pdfText, prompt);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing the query.");
        }
    }
}
