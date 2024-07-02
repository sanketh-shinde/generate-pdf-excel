package com.sanketh.generatepdfexcel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelService {

    ByteArrayInputStream createExcel() throws IOException;
}
