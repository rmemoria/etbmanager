package org.msh.etbm.db.entities;

import org.msh.etbm.commons.entities.cmdlog.Operation;
import org.msh.etbm.commons.entities.cmdlog.PropertyLog;
import org.msh.etbm.db.CaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Base entity class where other classes inherit from to store case data
 * where a date is required as reference
 *
 * @author Ricardo Memoria
 */
@MappedSuperclass
public class CaseEvent extends CaseEntity {

    @Temporal(TemporalType.DATE)
    @Column(name = "EVENT_DATE")
    @PropertyLog(operations = {Operation.NEW, Operation.DELETE})
    private Date date;

    @Lob
    @PropertyLog(messageKey = "global.comments")
    private String comments;


    /**
     * Return month of treatment based on the start treatment date and the collected date
     *
     * @return
     */
    public Integer getMonthTreatment() {
        Date dt = getDate();
        if (dt == null) {
            return null;
        }

        if (getTbcase() == null) {
            return null;
        }

        return getTbcase().getMonthTreatment(dt);
    }

    /**
     * Return displayable month of treatment
     *
     * @return
     */
    public String getMonthDisplay() {
        return this.getMonthTreatment().toString();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comment) {
        this.comments = comment;
    }


}
