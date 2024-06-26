package com.travelgo.backend.domain.attractionImage.service;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attractionImage.entity.AttractionImage;
import com.travelgo.backend.domain.attractionImage.repository.AttractionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionImageService {

    private final AttractionImageRepository attractionImageRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void save(MultipartFile image, Attraction attraction) throws IOException {
        String fileUrl = s3UploadService.upload(image, "images");
        AttractionImage attractionImage = new AttractionImage(fileUrl, attraction);
        attractionImageRepository.save(attractionImage);
    }

    @Transactional
    public void save(String imageUrl, Attraction attraction) {
        AttractionImage attractionImage = new AttractionImage(imageUrl, attraction);
        attractionImageRepository.save(attractionImage);
    }

    @Transactional
    public void delete(Long attractionImageId) {
        attractionImageRepository.deleteById(attractionImageId);
    }

    @Transactional
    public void deleteAllById(Long attractionId) {
        attractionImageRepository.deleteAllByAttraction_AttractionId(attractionId);
    }

    @Transactional
    public void deleteAll(){
        attractionImageRepository.deleteAll();
    }

    public List<AttractionImage> getImages(Long attractionId) {
        return attractionImageRepository.findAllByAttraction_AttractionId(attractionId);
    }

}
