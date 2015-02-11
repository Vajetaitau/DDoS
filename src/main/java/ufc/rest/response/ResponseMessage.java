package ufc.rest.response;

import ufc.enums.MessageCode;

public class ResponseMessage {

    private MessageCode messageCode;
    private Type type;

    public enum Type {
        success, error
    }

    public MessageCode getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setError() {
        this.type = Type.error;
    }

    public void setSuccess() {
        this.type = Type.success;
    }
}
