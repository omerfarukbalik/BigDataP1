package com.swe307.pro1;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "data")
public class DataRow {

    @Id
    private String id;

    @Field("Row")
    private String row;

    @Field("Col-1")
    private double col1;

    public String getId() { return id; }
    public String getRow() { return row; }
    public double getCol1() { return col1; }
}
