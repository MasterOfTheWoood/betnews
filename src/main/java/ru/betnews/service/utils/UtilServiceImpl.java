package ru.betnews.service.utils;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 14.11.13
 * Time 22:02
 * Вспомогательные функции
 */
@Service
public class UtilServiceImpl implements UtilService{

    private static Logger log = Logger.getLogger(UtilServiceImpl.class.getName());

    public String md5Java(String message){
        String digest = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        }
        catch (UnsupportedEncodingException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        catch (NoSuchAlgorithmException ex){
            log.log(Level.SEVERE, null, ex);
        }
        return digest;
    }

    public void sendEmail(String emailTo, String subject, String template, Map<String, Object> params){
        String host = "localhost";
        String from = "no-replay@svarhummer.org";
        emailTo = "guzflouter@yandex.ru";//TODO delete on PROD
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);


        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            message.setSubject(subject);
            String messageBody = "";
            message.setText(messageBody);

            // Send message
            Transport.send(message);
            log.info("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
