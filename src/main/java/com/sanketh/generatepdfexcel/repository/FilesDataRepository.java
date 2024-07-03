package com.sanketh.generatepdfexcel.repository;

import com.sanketh.generatepdfexcel.entity.FilesData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesDataRepository extends JpaRepository<FilesData, Integer> {
    FilesData findByName(String fileName);
}
