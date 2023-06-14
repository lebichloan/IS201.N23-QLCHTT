package model;

import java.io.FileOutputStream;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFGenerator {
    public static void main(String[] args) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("path/to/output.pdf"));

            document.open();
            document.add(new Paragraph("Hello, World!"));

            document.close();

            System.out.println("File PDF đã được tạo thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}