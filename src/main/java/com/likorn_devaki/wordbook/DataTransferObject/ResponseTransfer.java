package com.likorn_devaki.wordbook.DataTransferObject;

import lombok.NoArgsConstructor;

// wrapper for collecting responses
@NoArgsConstructor
public class ResponseTransfer {

    private String response;

    public ResponseTransfer(String response) {
        this.response = response;
    }

    @SuppressWarnings("WeakerAccess")
    public String getResponse() {
        return response;
    }
}
