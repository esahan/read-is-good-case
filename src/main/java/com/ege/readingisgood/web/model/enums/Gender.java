package com.ege.readingisgood.web.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("M"), FEMALE("F");
    private String key;
}
