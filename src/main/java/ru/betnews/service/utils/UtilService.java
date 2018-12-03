package ru.betnews.service.utils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 14.11.13
 * Time: 22:01
 *
 */
public interface UtilService {
    String md5Java(String message);
    void sendEmail(String emailTo, String subject, String template, Map<String, Object> params);
}
