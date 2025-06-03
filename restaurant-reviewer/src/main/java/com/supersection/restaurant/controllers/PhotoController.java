package com.supersection.restaurant.controllers;

import com.supersection.restaurant.domain.dtos.PhotoDTO;
import com.supersection.restaurant.domain.entities.Photo;
import com.supersection.restaurant.mappers.PhotoMapper;
import com.supersection.restaurant.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping
    public PhotoDTO validatePhoto(@RequestParam("file")MultipartFile file) {
        Photo savedPhoto = photoService.uploadPhoto(file);
        return photoMapper.toDto(savedPhoto);
    }

}
