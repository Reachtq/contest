package com.group.contestback.responseTypes;

import com.group.contestback.models.Scores;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScoresUser {
    UserPageResponse user;
    List<Scores> scores = new ArrayList<>();
}
