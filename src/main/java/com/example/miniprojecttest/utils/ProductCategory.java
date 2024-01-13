package com.example.miniprojecttest.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    AGRICULTURE(1, "농산물"),
    SEAFOOD(2, "수산물"),
    FRUIT(3, "과일")
    ;

    private final Integer idx;
    private final String type;
}
