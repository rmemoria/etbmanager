package org.msh.etbm.commons.entities;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * Exception generated inside an entity to indicate that an error occured and operation
 * cannot continue
 * <p>
 * Created by rmemoria on 28/10/15.
 */
public class EntityValidationException extends RuntimeException {

    private transient final BindingResult bindingResult;

    /**
     * Constructor when there is just one single validation error message
     *
     * @param field
     * @param message
     * @param code
     */
    public EntityValidationException(Object entity, String field, String message, String code) {
        super(message);

        this.bindingResult = new BeanPropertyBindingResult(entity, entity.getClass().getSimpleName());
        if (message != null) {
            this.bindingResult.reject(field, message);
        } else {
            this.bindingResult.rejectValue(field, code);
        }
    }

    /**
     * Constructor when validation messages are already in the binding result object
     *
     * @param bindingResult instance of BindingResult object containing validation messages
     */
    public EntityValidationException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    @Override
    public String getMessage() {
        if (bindingResult == null) {
            return super.getMessage();
        }

        return bindingResult.toString();
    }
}
