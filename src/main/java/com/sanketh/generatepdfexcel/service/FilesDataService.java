package com.sanketh.generatepdfexcel.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesDataService {

    String storePdfInBase64String(MultipartFile file) throws IOException;

    byte[] convertToPdf(String fileName) throws IOException;

    byte[] convertToExcel(String fileName) throws IOException;
}
