package com.sanketh.generatepdfexcel.controller;

import com.sanketh.generatepdfexcel.service.FilesDataService;
import com.sanketh.generatepdfexcel.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private FilesDataService filesDataService;

    @GetMapping("/generatePdf")
    public ResponseEntity<InputStreamResource> generatePdf() {

        String fileName = "students";

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        ByteArrayInputStream pdf = pdfService.createPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName + "_" + currentDateTime + ".pdf");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(pdf));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> storePdf(@RequestParam("file") MultipartFile file) throws IOException {
        String base64String = filesDataService.storePdfInBase64String(file);
        return ResponseEntity.ok().body(base64String);
    }

    @GetMapping(value = "/retrieve/{fileName}")
    public ResponseEntity<byte[]> getPdfFromDB(@PathVariable String fileName) throws IOException {
        byte[] pdf = filesDataService.convertToPdf(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/pdf"))
                .body(pdf);
    }

}
