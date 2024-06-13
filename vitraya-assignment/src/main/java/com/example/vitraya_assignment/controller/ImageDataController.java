package com.example.vitraya_assignment.controller;

import com.example.vitraya_assignment.models.ImageData;
import com.example.vitraya_assignment.service.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/images")
public class ImageDataController {

  @Autowired
  private ImageDataService imageDataService;

  private static final Logger LOGGER = Logger.getLogger(ImageDataController.class.getName());

  @PostMapping("/upload")
  public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      ImageData savedImageData = imageDataService.uploadImage(file);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedImageData);
    } catch (RuntimeException e) {
      LOGGER.severe("Failed to upload image: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<?> getAllImageData() {
    try {
      List<ImageData> imageDataList = imageDataService.getAllImageData();
      return ResponseEntity.ok(imageDataList);
    } catch (RuntimeException e) {
      LOGGER.severe("Failed to retrieve images: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getImageDataById(@PathVariable Long id) {
    try {
      ImageData imageData = imageDataService.getImageDataById(id);
      return ResponseEntity.ok(imageData);
    } catch (RuntimeException e) {
      LOGGER.severe("Image not found: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
