package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

public class BeanCreatingException extends BeansException {


    public BeanCreatingException(String message) {
        super(message);
    }

    public BeanCreatingException(String message, Throwable cause) {
        super(message, cause);
    }


}
