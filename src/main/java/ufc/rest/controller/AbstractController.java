package ufc.rest.controller;

import ufc.rest.response.ResponseMessage;
import ufc.enums.MessageCode;

public class AbstractController {

    protected ResponseMessage onFailure(MessageCode messageCode) {
        ResponseMessage response = new ResponseMessage();
        response.setError();
        response.setMessageCode(messageCode);
        return response;
    }

    protected <T extends ResponseMessage> T onSuccess(Class<T> klass, MessageCode messageCode) {
        T response = null;
        try {
            response = klass.newInstance();
            response.setSuccess();
            response.setMessageCode(messageCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
