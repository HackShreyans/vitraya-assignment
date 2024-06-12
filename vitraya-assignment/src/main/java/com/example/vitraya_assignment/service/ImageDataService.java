package com.example.vitraya_assignment.service;

import com.example.vitraya_assignment.models.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageDataService {
  ImageData uploadImage(MultipartFile file);
  ImageData saveImageData(ImageData imageData);
  List<ImageData> getAllImageData();
  ImageData getImageDataById(Long id);
}
