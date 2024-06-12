package com.example.vitraya_assignment.repository;


import com.example.vitraya_assignment.models.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
}
