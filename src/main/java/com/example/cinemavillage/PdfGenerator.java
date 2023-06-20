package com.example.cinemavillage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class PdfGenerator {
    public static void generatePdf(String filePath, List<String> elements, String imageUrl) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("POTWIERDZENIE REZERWACJI");
        contentStream.newLine();

        contentStream.setFont(PDType1Font.HELVETICA, 14);

        int fixedOffsetY = 650;

        for (String element : elements) {
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText(element);
            contentStream.newLine();
        }

        contentStream.endText();

        try (InputStream in = new URL(imageUrl).openStream()) {
            BufferedImage bImage = ImageIO.read(in);
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bImage);
            float scale = 0.5f;
            contentStream.drawImage(pdImage, 250, (fixedOffsetY - 180) - pdImage.getHeight() * scale, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
        } catch (Exception e) {
            System.err.println("Error loading image from URL: " + e.getMessage());
        }

        contentStream.close();

        document.save(filePath);
        document.close();
    }
}
