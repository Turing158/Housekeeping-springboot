package com.housekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonOperation {
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> T toObject(String json,Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json,clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
