package com.example.user_register.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

    /**
     * Convert an Object to json string format with its properties.
     * This mainly use for tests to perform requests.
     *
     * @param obj The Object to transform to a json string.
     * @return a String composed of a json object with all the object properties.
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
