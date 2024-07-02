package com.sanketh.generatepdfexcel.service;

import com.sanketh.generatepdfexcel.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private StudentService studentService;

    @Override
    public ByteArrayInputStream createExcel() throws IOException {

        String[] header = {"Id", "Name", "Address"};
        List<Student> students = studentService.allStudents();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet studentsSheet = workbook.createSheet("Students");

            Row headerRow = studentsSheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
            }

            int rowIndex = 1;
            for (Student student : students) {
                Row dataRow = studentsSheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(student.getId());
                dataRow.createCell(1).setCellValue(student.getName());
                dataRow.createCell(2).setCellValue(student.getAddress());
            }

            workbook.write(out);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
