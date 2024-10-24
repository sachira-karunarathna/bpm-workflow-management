package com.bpm_workflow.bpm_workflow_management.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Component
public class Helpers {

    public static String convertXmlToJsonString(MultipartFile file) {
        return "";
    }

    public static boolean isValidXmlFile(MultipartFile file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(file.getInputStream());

            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/xml")) {
                throw new IllegalArgumentException("Invalid file type");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
