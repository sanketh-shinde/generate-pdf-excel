package com.sanketh.generatepdfexcel.controller;

import com.sanketh.generatepdfexcel.service.ExcelService;
import com.sanketh.generatepdfexcel.service.FilesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FilesDataService filesDataService;

    @GetMapping("/generateExcel")
    public ResponseEntity<InputStreamResource> generateExcel() throws IOException {

        String fileName = "students";

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        ByteArrayInputStream excel = excelService.createExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "_" + currentDateTime  + ".xlsx")
               .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(new InputStreamResource(excel));
    }

    @GetMapping(value = "/retrieve/{fileName}", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getExcelFromDB(@PathVariable String fileName) throws IOException {
        byte[] excel = filesDataService.convertToExcel(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/vnd.ms-excel"))
                .body(excel);
    }
}
