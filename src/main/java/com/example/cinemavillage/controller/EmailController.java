package com.example.cinemavillage.controller;

import com.example.cinemavillage.PdfGenerator;
import com.example.cinemavillage.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendEmail() {
        try {
            String filePath = "src/main/resources/static/confirmation.pdf";
            PdfGenerator.generatePdf(filePath);

            emailService.sendMailWithAttachment(
                    "to@example.com",
                    "Hello!",
                    "Please find the attachment.",
                    filePath
            );
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
