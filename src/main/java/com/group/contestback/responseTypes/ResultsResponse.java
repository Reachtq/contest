package com.group.contestback.responseTypes;

import lombok.Data;
import java.util.List;

@Data
public class ResultsResponse {
    private Integer timeout;
    List<List<String>> openResult;
}
