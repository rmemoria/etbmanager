package org.msh.etbm.commons.models.data.fields;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by rmemoria on 7/7/16.
 */
public abstract class SingleField extends Field {

    /**
     * Name of the field in the database table
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dbFieldName;


    public SingleField() {
        super();
    }

    public SingleField(String name) {
        super(name);
    }

    public SingleField(String name, String dbFieldName) {
        super(name);
        this.dbFieldName = dbFieldName;
    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }
}
