package com.example.technologies.domain.exceptions;

import com.example.technologies.domain.enums.Message;

public class DomainException extends RuntimeException {
    private final Message messageEnum;

    public DomainException(Message messageEnum) {
        super(messageEnum.getMessage());
        this.messageEnum = messageEnum;
    }

    public String getCode() {
        return messageEnum.getCode();
    }

    public String getParam() {
        return messageEnum.getParam();
    }
}
