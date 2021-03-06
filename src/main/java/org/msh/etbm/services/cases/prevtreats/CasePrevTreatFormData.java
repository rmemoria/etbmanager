package org.msh.etbm.services.cases.prevtreats;

import org.msh.etbm.db.enums.PrevTBTreatmentOutcome;
import org.msh.etbm.services.cases.CaseEntityFormData;

import java.util.Optional;

/**
 * Created by Mauricio on 18/08/2016.
 */
public class CasePrevTreatFormData extends CaseEntityFormData {
    private Optional<Integer> month;
    private Optional<Integer> year;
    private Optional<Integer> outcomeMonth;
    private Optional<Integer> outcomeYear;
    private Optional<PrevTBTreatmentOutcome> outcome;

    private Optional<Boolean> am;
    private Optional<Boolean> cfz;
    private Optional<Boolean> cm;
    private Optional<Boolean> cs;
    private Optional<Boolean> e;
    private Optional<Boolean> eto;
    private Optional<Boolean> h;
    private Optional<Boolean> lfx;
    private Optional<Boolean> ofx;
    private Optional<Boolean> r;
    private Optional<Boolean> s;
    private Optional<Boolean> z;

    public Optional<Integer> getMonth() {
        return month;
    }

    public void setMonth(Optional<Integer> month) {
        this.month = month;
    }

    public Optional<Integer> getYear() {
        return year;
    }

    public void setYear(Optional<Integer> year) {
        this.year = year;
    }

    public Optional<Integer> getOutcomeMonth() {
        return outcomeMonth;
    }

    public void setOutcomeMonth(Optional<Integer> outcomeMonth) {
        this.outcomeMonth = outcomeMonth;
    }

    public Optional<Integer> getOutcomeYear() {
        return outcomeYear;
    }

    public void setOutcomeYear(Optional<Integer> outcomeYear) {
        this.outcomeYear = outcomeYear;
    }

    public Optional<PrevTBTreatmentOutcome> getOutcome() {
        return outcome;
    }

    public void setOutcome(Optional<PrevTBTreatmentOutcome> outcome) {
        this.outcome = outcome;
    }

    public Optional<Boolean> getAm() {
        return am;
    }

    public void setAm(Optional<Boolean> am) {
        this.am = am;
    }

    public Optional<Boolean> getCfz() {
        return cfz;
    }

    public void setCfz(Optional<Boolean> cfz) {
        this.cfz = cfz;
    }

    public Optional<Boolean> getCm() {
        return cm;
    }

    public void setCm(Optional<Boolean> cm) {
        this.cm = cm;
    }

    public Optional<Boolean> getCs() {
        return cs;
    }

    public void setCs(Optional<Boolean> cs) {
        this.cs = cs;
    }

    public Optional<Boolean> getE() {
        return e;
    }

    public void setE(Optional<Boolean> e) {
        this.e = e;
    }

    public Optional<Boolean> getEto() {
        return eto;
    }

    public void setEto(Optional<Boolean> eto) {
        this.eto = eto;
    }

    public Optional<Boolean> getH() {
        return h;
    }

    public void setH(Optional<Boolean> h) {
        this.h = h;
    }

    public Optional<Boolean> getLfx() {
        return lfx;
    }

    public void setLfx(Optional<Boolean> lfx) {
        this.lfx = lfx;
    }

    public Optional<Boolean> getOfx() {
        return ofx;
    }

    public void setOfx(Optional<Boolean> ofx) {
        this.ofx = ofx;
    }

    public Optional<Boolean> getR() {
        return r;
    }

    public void setR(Optional<Boolean> r) {
        this.r = r;
    }

    public Optional<Boolean> getS() {
        return s;
    }

    public void setS(Optional<Boolean> s) {
        this.s = s;
    }

    public Optional<Boolean> getZ() {
        return z;
    }

    public void setZ(Optional<Boolean> z) {
        this.z = z;
    }
}
