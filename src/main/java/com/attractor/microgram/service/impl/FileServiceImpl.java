package com.attractor.microgram.service.impl;

import com.attractor.microgram.service.FileService;
import com.attractor.microgram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class FileServiceImpl implements FileService {
    private final FileUtil fileUtil;
    private final String dirImages="images";
    private final String dirJs="js";

    @Override
    public ResponseEntity<?> downloadImages(String fileName) {
        return fileUtil.getOutputStaticFile(fileName, dirImages, MediaType.IMAGE_JPEG);
    }

    @Override
    public ResponseEntity<?> downloadJs(String fileName) {
        MediaType m=new MediaType("application", "javascript");
        return fileUtil.getOutputStaticFile(fileName, dirJs, m);
    }
}
