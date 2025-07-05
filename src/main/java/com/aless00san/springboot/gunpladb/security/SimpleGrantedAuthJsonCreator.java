package com.aless00san.springboot.gunpladb.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthJsonCreator(@JsonProperty("authority") String role) {
    }
}
