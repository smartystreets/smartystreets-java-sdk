package com.smartystreets.api.us_enrichment.result_types;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class EnrichmentToStringer {
    @Override
    public String toString() {
        Class<?> clazz = getClass();
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName()).append(":\n");

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if (value != null) {
                        if (value.getClass().isArray()) {
                            value = arrayToString(value);
                        }
                        sb.append(field.getName()).append(": ").append(value).append("\n");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }

        return sb.toString();
    }

    private String arrayToString(Object array) {
        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            sb.append(element);
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
