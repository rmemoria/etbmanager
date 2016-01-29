package org.msh.etbm.commons.objutils;

/**
 * Exception thrown by the {@link ObjectUtils} class to indicate an error when trying to access an object
 * Created by rmemoria on 28/1/16.
 */
public class ObjectAccessException extends RuntimeException {

    private transient Object object;
    private final String property;

    /**
     * Constructor to get information about the error
     * @param obj the object involved in the exception
     * @param property the property involved in the exception
     * @param msg the message generated by the error
     * @param e the exception
     */
    ObjectAccessException(Object obj, String property, String msg, Throwable e) {
        super(msg, e);
        this.object = obj;
        this.property = property;
    }

    /**
     * Default constructor
     * @param msg the message
     * @param t the original exception thrown
     */
    ObjectAccessException(String msg, Throwable t) {
        super(msg, t);
        this.property = null;
    }

    public Object getObject() {
        return object;
    }

    public String getProperty() {
        return property;
    }
}
