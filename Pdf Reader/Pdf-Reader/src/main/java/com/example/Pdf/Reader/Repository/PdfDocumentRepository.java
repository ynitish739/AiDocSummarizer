package com.example.Ai.Planet.Assignment.Repository;

//import com.example.Ai.Planet.Assignment.Entity.entityClass;
import com.example.Ai.Planet.Assignment.Entity.entityClass;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PdfDocumentRepository extends JpaRepository<entityClass, Long> {
    entityClass findTopByOrderByUploadTimeDesc();
}
