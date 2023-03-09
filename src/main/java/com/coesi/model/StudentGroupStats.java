/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coesi.model;

/**
 *
 * @author Alberto
 */
public class StudentGroupStats {

    private AttendanceMatrix attendanceMatrix;
    private String[][] evaluation;

    public AttendanceMatrix getAttendanceMatrix() {
        return attendanceMatrix;
    }

    public void setAttendanceMatrix(AttendanceMatrix attendanceMatrix) {
        this.attendanceMatrix = attendanceMatrix;
    }

    public String[][] getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String[][] evaluation) {
        this.evaluation = evaluation;
    }

}
