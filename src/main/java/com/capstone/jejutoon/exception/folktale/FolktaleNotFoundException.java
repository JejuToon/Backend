package com.capstone.jejutoon.exception.folktale;

import com.capstone.jejutoon.exception.common.BaseException;
import org.springframework.http.HttpStatus;

public class FolktaleNotFoundException extends BaseException {
    public FolktaleNotFoundException(Long folktaleId) {
        super("Folktale not found with id: " + folktaleId, "FOLKTALE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
