package com.example.project.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

public interface IFileService {
    String uploadImage(MultipartFile file) throws IOException;
	
	InputStream getResource( String fileName) throws FileNotFoundException;
    
} 
