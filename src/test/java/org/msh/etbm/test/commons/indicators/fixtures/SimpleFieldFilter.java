package org.msh.etbm.test.commons.indicators.fixtures;

import org.msh.etbm.commons.filters.Filter;
import org.msh.etbm.commons.sqlquery.QueryDefs;
import org.msh.etbm.services.cases.filters.FilterContext;
import org.msh.etbm.services.cases.filters.FilterGroup;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Created by rmemoria on 10/9/16.
 */
public class SimpleFieldFilter implements Filter {

    private String id;
    private String label;
    private String fieldName;

    public SimpleFieldFilter(String id, String label, String fieldName) {
        this.id = id;
        this.label = label;
        this.fieldName = fieldName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public FilterGroup getGroup() {
        return FilterGroup.DATA;
    }

    @Override
    public void initialize(ApplicationContext context) {
    //
    }

    @Override
    public void prepareFilterQuery(QueryDefs def, Object value, Map<String, Object> params) {
        if (value == null) {
            return;
        }
        def.restrict(fieldName + " = ?", value.toString());
    }

    @Override
    public String getFilterType() {
        return "select";
    }

    @Override
    public Map<String, Object> getResources(FilterContext context, Map<String, Object> params) {
        return null;
    }
}
