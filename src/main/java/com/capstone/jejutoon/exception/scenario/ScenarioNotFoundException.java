package com.capstone.jejutoon.exception.scenario;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class ScenarioNotFoundException extends BaseException {
    public ScenarioNotFoundException(Long folktaleDetailId) {
        super(
                "Scenario not found with folktaleDetail id: " + folktaleDetailId,
                "SCENARIO_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
