package com.example.vitraya_assignment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;

@Entity
public class ImageData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String imageData;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String extractedText;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String boldWords;

  // Constructors, getters, and setters
  public ImageData() {
  }

  public ImageData(String imageData, String extractedText, String boldWords) {
    this.imageData = imageData;
    this.extractedText = extractedText;
    this.boldWords = boldWords;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImageData() {
    return imageData;
  }

  public void setImageData(String imageData) {
    this.imageData = imageData;
  }

  public String getExtractedText() {
    return extractedText;
  }

  public void setExtractedText(String extractedText) {
    this.extractedText = extractedText;
  }

  public String getBoldWords() {
    return boldWords;
  }

  public void setBoldWords(String boldWords) {
    this.boldWords = boldWords;
  }
}
