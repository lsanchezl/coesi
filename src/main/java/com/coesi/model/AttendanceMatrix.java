package com.coesi.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alberto
 */
public class AttendanceMatrix {

    private final List<String> headers;
    private final List<List<String>> data;
    private final List<List<Long>> ids;

    public AttendanceMatrix() {
        headers = new ArrayList<>();
        data = new ArrayList<>();
        ids = new ArrayList<>();
    }

    public void addHeader(String header) {
        headers.add(header);
    }

    public void addData(List<String> data) {
        this.data.add(data);
    }

    public void addIds(List<Long> ids) {
        this.ids.add(ids);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getData() {
        return data;
    }

    public List<List<Long>> getIds() {
        return ids;
    }
}
