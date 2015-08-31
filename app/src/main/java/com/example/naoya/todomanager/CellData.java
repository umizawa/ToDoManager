package com.example.naoya.todomanager;

import java.util.Date;

public class CellData {

    private int imageResourceId;
    private Date  dueDay;
    private String cellTitle;

    public CellData(int imageResourceId, Date dueDay, String cellTitle){
        this.imageResourceId = imageResourceId;
        this.dueDay = dueDay;
        this.cellTitle = cellTitle;
    }

    public int getCellImageResourceId() {return imageResourceId;}
    public Date getCellDueDay() {return dueDay;}
    public String getCellTitle() {return cellTitle;}

}