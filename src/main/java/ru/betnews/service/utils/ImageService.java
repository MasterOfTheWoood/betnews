package ru.betnews.service.utils;

import java.io.InputStream;

/**
 * Created by Evgeniy.Guzeev on 09.04.2018.
 */
public interface ImageService {
    /**
     * Получение картинки с удалённого сервера
     * @param url - url
     * @return - картинка
     */
    InputStream getImage(String url);

    /**
     * Сохранение картинки на удалёном серевере
     * @param image - картинки
     * @return - картинка
     */
    String saveImage(byte[] image);

    /**
     * Удаление картинки на удалёном серевере
     * @param url - url
     */
    void deleteImage(String url);

}
