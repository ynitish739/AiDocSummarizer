package com.example.Ai.Planet.Assignment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class entityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName; // Name of the uploaded PDF

    @Column(nullable = false, columnDefinition = "TEXT")
    private String extractedText; // Extracted text from the PDF

    @Column(nullable = false)
    private LocalDateTime uploadTime; // Timestamp for when the PDF was uploaded

}
