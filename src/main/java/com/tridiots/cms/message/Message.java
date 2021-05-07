package com.tridiots.cms.message;

/**
 * @author Tridiots
 *
 * Container for a flag with its corresponding message to determine the exact reason for
 * the said flag.
 */
public class Message {
    private String message;
    private boolean flag;

    public Message(String message, boolean flag) {
        this.message = message;
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
