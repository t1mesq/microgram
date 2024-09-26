package com.attractor.microgram.service;

import org.springframework.http.ResponseEntity;

public interface FileService {
    ResponseEntity<?> downloadImages(String fileName);
    ResponseEntity<?> downloadJs(String fileName);
}

