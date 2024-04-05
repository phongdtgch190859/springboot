package com.example.project.service.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.service.IFileService;

import io.jsonwebtoken.io.IOException;

@Service
public class FileService implements IFileService{

    @Override
    public String uploadImage( MultipartFile file) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadImage'");
    }

    @Override
    public InputStream getResource(String fileName) throws FileNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResource'");
    }
    
}
