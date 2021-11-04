package com.ege.readingisgood.web.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("M"), FEMALE("F");

    private String key;

    public static Optional<Gender> getByKey(String key){
        return Arrays.stream(Gender.values()).filter(x->x.getKey().equals(key)).findAny();
    }
}
