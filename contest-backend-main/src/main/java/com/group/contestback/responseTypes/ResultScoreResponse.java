package com.group.contestback.responseTypes;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultScoreResponse {
    private Integer timeout;
    private List<Result> results = new ArrayList<>();
}
