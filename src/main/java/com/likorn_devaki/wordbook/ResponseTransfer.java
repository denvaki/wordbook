package com.likorn_devaki.wordbook;

import lombok.NoArgsConstructor;

// wrapper for collecting responses
@NoArgsConstructor
class ResponseTransfer {

    private String response;

    ResponseTransfer(String response) {
        this.response = response;
    }

    @SuppressWarnings("WeakerAccess")
    public String getResponse() {
        return response;
    }
}
