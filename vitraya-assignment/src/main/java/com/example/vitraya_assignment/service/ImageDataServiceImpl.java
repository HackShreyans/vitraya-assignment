package com.example.vitraya_assignment.service;

import com.example.vitraya_assignment.models.ImageData;
import com.example.vitraya_assignment.repository.ImageDataRepository;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

@Service
public class ImageDataServiceImpl implements ImageDataService {

  @Autowired
  private ImageDataRepository imageDataRepository;

  @Value("${tesseract.data.path}")
  private String tesseractDataPath;

  private static final Logger LOGGER = Logger.getLogger(ImageDataServiceImpl.class.getName());

  @Override
  public ImageData uploadImage(MultipartFile file) {
    try {
      byte[] imageData = file.getBytes();
      String base64Image = Base64.getEncoder().encodeToString(imageData);

      // Perform OCR and extract bold words
      String extractedText = performOCR(file);
      List<String> boldWords = extractBoldWords(extractedText);

      ImageData imageDataEntity = new ImageData();
      imageDataEntity.setImageData(base64Image);
      imageDataEntity.setExtractedText(extractedText);
      imageDataEntity.setBoldWords(String.join(", ", boldWords));

      return imageDataRepository.save(imageDataEntity);
    } catch (IOException e) {
      LOGGER.severe("Failed to upload image: " + e.getMessage());
      throw new RuntimeException("Failed to upload image: " + e.getMessage(), e);
    }
  }

  @Override
  public ImageData saveImageData(ImageData imageData) {
    return imageDataRepository.save(imageData);
  }

  @Override
  public List<ImageData> getAllImageData() {
    return imageDataRepository.findAll();
  }

  @Override
  public ImageData getImageDataById(Long id) {
    return imageDataRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found with id " + id));
  }

  private String performOCR(MultipartFile file) {
    try {
      ITesseract tesseract = new Tesseract();
      tesseract.setDatapath(tesseractDataPath);

      BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
      return tesseract.doOCR(bufferedImage);
    } catch (IOException e) {
      LOGGER.severe("Failed to read image input stream: " + e.getMessage());
      throw new RuntimeException("Failed to read image input stream: " + e.getMessage(), e);
    } catch (TesseractException e) {
      LOGGER.severe("Failed to perform OCR: " + e.getMessage());
      throw new RuntimeException("Failed to perform OCR: " + e.getMessage(), e);
    }
  }

  private List<String> extractBoldWords(String text) {
    List<String> boldWords = new ArrayList<>();
    Matcher matcher = Pattern.compile("\\*\\*(.*?)\\*\\*").matcher(text);
    while (matcher.find()) {
      boldWords.add(matcher.group(1));
    }
    return boldWords;
  }
}
