package com.example.Ai.Planet.Assignment.Service;

import com.example.Ai.Planet.Assignment.Entity.entityClass;
import com.example.Ai.Planet.Assignment.Repository.PdfDocumentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PdfDocumentService {
    @Autowired
    private PdfDocumentRepository pdfDocumentRepository;

    public entityClass savePdf(MultipartFile file) throws IOException {
        // Extract text from PDF
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String extractedText = stripper.getText(document);
        document.close();

        // Create entity and save to database
        entityClass pdfDocument = new entityClass();
        pdfDocument.setFileName(file.getOriginalFilename());
        pdfDocument.setExtractedText(extractedText);
        pdfDocument.setUploadTime(LocalDateTime.now());

        return pdfDocumentRepository.save(pdfDocument);
    }
    public String getLatestPdfText() {
        entityClass latestPdfDocument = pdfDocumentRepository.findTopByOrderByUploadTimeDesc();
        if (latestPdfDocument != null) {
            return latestPdfDocument.getExtractedText();
        }
        return null; // Return null if no PDF document is found
    }
}
