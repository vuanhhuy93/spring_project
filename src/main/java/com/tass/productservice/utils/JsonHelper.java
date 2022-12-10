package com.tass.productservice.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String toString(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T getObject(String data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(Map<String, Object> data, Class<T> clazz) {
        try {
            return mapper.convertValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObjectFromMap(Map<String, String> data, Class<T> clazz) {
        try {
            return mapper.convertValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(byte[] data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(InputStream inputStream, Class<T> clazz) {
        try {
            return mapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getMap(String jsonData) {
        try {
            return mapper.readValue(jsonData, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static Map<String, String> getStringMap(InputStream inputStream) {
        try {
            return mapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
//            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("thangnm 1");
        list.add("thangnm 2");
        list.add("thangnm 3");
        list.add("thangnm 4");
        list.add("thangnm 5");

        try {
            System.out.println(toString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
