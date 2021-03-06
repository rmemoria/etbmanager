package org.msh.etbm.commons.models.data.handlers;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.msh.etbm.commons.models.data.fields.DateField;
import org.msh.etbm.commons.models.impl.FieldContext;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by rmemoria on 2/7/16.
 */
public class DateFieldHandler extends SingleFieldHandler<DateField> {

    @Override
    protected Object convertValue(DateField field, FieldContext context, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return value;
        }

        if (value instanceof String) {
            try {
                return ISO8601DateFormat.getInstance().parse((String)value);
            } catch (ParseException e) {
                registerConversionError(context);
            }
        }

        registerConversionError(context);
        return value;
    }

    @Override
    protected void validateValue(DateField field, FieldContext context, Object value) {

    }

}
