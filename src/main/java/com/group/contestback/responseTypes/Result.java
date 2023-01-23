package com.group.contestback.responseTypes;

import lombok.Data;

@Data
public class Result {
    private String name;
    private Boolean success;

    public Result(String s, boolean b) {
        this.name = s;
        this.success = b;
    }
}