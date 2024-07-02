package com.sanketh.generatepdfexcel.service;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sanketh.generatepdfexcel.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private StudentService studentService;

    public ByteArrayInputStream createPdf() {
        String title = "Students List";

        List<Student> students = studentService.allStudents();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (Document document = new Document()) {
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 30, Color.DARK_GRAY);

            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            document.add(titlePara);

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);

            Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE);
            Stream.of("Id", "Name", "Address").forEach(cellHeader -> {
                PdfPCell header = new PdfPCell(new Phrase(cellHeader, headFont));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(header);
            });

            for (Student student : students) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(student.getId())));
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(student.getName()));
                nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(nameCell);

                PdfPCell addressCell = new PdfPCell(new Phrase(student.getAddress()));
                addressCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(addressCell);
            }

            document.add(table);
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
