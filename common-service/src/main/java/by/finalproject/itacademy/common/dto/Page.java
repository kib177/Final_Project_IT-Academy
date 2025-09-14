package by.finalproject.itacademy.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
        private int number;
        private int size;
        private int totalPages;
        private long totalElements;
        private boolean first;
        private int numberOfElements;
        private boolean last;
        private List<T> content;
    }

