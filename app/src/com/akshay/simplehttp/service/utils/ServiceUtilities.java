package com.akshay.simplehttp.service.utils;


import org.codehaus.jackson.map.ObjectMapper;


public class ServiceUtilities {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
    
    public interface NetworkAvailabilityCallback{
        public void isAvailable(boolean flag);
    }

}
