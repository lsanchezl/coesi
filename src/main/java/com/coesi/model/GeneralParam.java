/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coesi.model;

import java.time.LocalDate;

/**
 *
 * @author Alberto
 */
public class GeneralParam {

    private Long id;
    private Object value;
    private LocalDate valueLocalDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public Long getLong() {
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public Double getDouble() {
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0D;
        }
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public LocalDate getValueLocalDate() {
        return valueLocalDate;
    }

    public void setValueLocalDate(LocalDate valueLocalDate) {
        this.valueLocalDate = valueLocalDate;
    }

}
