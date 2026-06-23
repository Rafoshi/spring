package com.example.healthchecker.domain.check;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckResult {

    private String url;

    private boolean success;

    private Integer statusCode;

    private long durationMillis;

    private String error;
}
