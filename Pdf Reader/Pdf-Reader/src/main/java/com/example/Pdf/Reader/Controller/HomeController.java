package com.example.Ai.Planet.Assignment.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @PostMapping("/upload")
//    public ModelAndView handleFileUpload(MultipartFile pdfFile) {
//        // Add logic to process the uploaded file here
//        // For now, just log the file name to demonstrate the flow
//        System.out.println("Uploaded file: " + pdfFile.getOriginalFilename());
//
//        // Redirect to chat.html after file upload
//        return new ModelAndView("redirect:/chat");
//    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}