package com.example.finalProject.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class PageOfUser<T>{
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int numberOfElements;
    private boolean last;
    private List<T> content;
}
