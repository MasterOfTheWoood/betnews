package ru.betnews.service.utils;

import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by Evgeniy.Guzeev on 09.04.2018.
 */
@Service
public class ImageServiceImpl implements ImageService{
    @Override
    public InputStream getImage(String url) {
        return null;
    }

    @Override
    public String saveImage(byte[] image) {
        return null;
    }

    @Override
    public void deleteImage(String url) {

    }
}
