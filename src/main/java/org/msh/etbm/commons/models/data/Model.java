package org.msh.etbm.commons.models.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.msh.etbm.commons.models.data.fields.Field;

import java.util.List;

/**
 * Created by rmemoria on 1/7/16.
 */
public class Model {

    /**
     * The model unique identifier ID
     */
    private String name;

    /**
     * The table to store the model record
     */
    private String table;

    /**
     * The list of fields of a model
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Field> fields;

    /**
     * The list of validators of the model
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Validator> validators;


    /**
     * Search for a field by its name
     * @param name
     * @return
     */
    public Field findFieldByName(String name) {
        if (fields != null) {
            for (Field field: fields) {
                if (name.equals(field.getName())) {
                    return field;
                }
            }
        }

        return null;
    }

    /**
     * Resolve the table name. If table property is not defined, use the model name
     * @return
     */
    public String resolveTableName() {
        return table != null ? table : name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
