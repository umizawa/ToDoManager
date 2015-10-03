package com.example.naoya.todomanager;

import java.util.Date;

public class CellData {

    private int index;
    private int imageResourceId;
    private Date  dueDay;
    private String cellTitle;

    public CellData(int index, int imageResourceId, Date dueDay, String cellTitle){
        this.index = index;
        this.imageResourceId = imageResourceId;
        this.dueDay = dueDay;
        this.cellTitle = cellTitle;
    }

    public int getIndex(){return index;}
    public int getCellImageResourceId() {return imageResourceId;}
    public Date getCellDueDay() {return dueDay;}
    public String getCellTitle() {return cellTitle;}

}