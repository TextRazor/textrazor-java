package com.textrazor;

import java.io.IOException;

public class NetworkException extends IOException {
    
	private static final long serialVersionUID = -821976320048704953L;

	public NetworkException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}