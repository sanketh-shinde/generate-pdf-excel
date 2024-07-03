package com.sanketh.generatepdfexcel.service;

import com.sanketh.generatepdfexcel.entity.FilesData;
import com.sanketh.generatepdfexcel.repository.FilesDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class FilesDataServiceImpl implements FilesDataService {

    @Autowired
    private FilesDataRepository filesDataRepository;

    @Override
    public String storePdfInBase64String(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return "File can not be empty";
        }

        byte[] fileBytes = file.getBytes();
        String base64String = pdfToBase64String(fileBytes);

        FilesData filesData = FilesData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .data(base64String)
                .build();

        FilesData savedFilesData = filesDataRepository.save(filesData);

        if (savedFilesData.getId() != null) {
            return "Upload Successful";
        }
        return "Failed to upload";
    }

    public String pdfToBase64String(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    @Override
    public byte[] convertToPdf(String fileName) throws IOException {
        FilesData file = filesDataRepository.findByName(fileName);
        String data = file.getData();
        return Base64.getDecoder().decode(data);
    }

    @Override
    public byte[] convertToExcel(String fileName) throws IOException {
        FilesData file = filesDataRepository.findByName(fileName);
        String data = file.getData();
        return Base64.getDecoder().decode(data);
    }
}
