package com.group.contestback.responseTypes;

import com.group.contestback.models.Attempts;
import com.group.contestback.models.Tasks;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttemptsTask {
    Attempts attempt;
    Tasks task;
}
