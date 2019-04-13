package com.likorn_devaki.wordbook;

import lombok.NoArgsConstructor;

// wrapper for collecting responses
@NoArgsConstructor
public class ResponseTransfer {

    private String response;

    ResponseTransfer(String response) {
        this.response = response;
    }

    String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
