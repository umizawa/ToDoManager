package com.example.naoya.todomanager;

import java.util.Date;

public class CellData {

    private int id;
    private int imageResourceId;
    private Date  dueDay;
    private String cellTitle;
    private int importance;

    public CellData(int id, int imageResourceId, Date dueDay, String cellTitle, int importance){
        this.id = id;
        this.imageResourceId = imageResourceId;
        this.dueDay = dueDay;
        this.cellTitle = cellTitle;
        this.importance = importance;
    }

    public int getId(){return id;}
    public int getCellImageResourceId() {return imageResourceId;}
    public Date getCellDueDay() {return dueDay;}
    public String getCellTitle() {return cellTitle;}
    public int getImportance() {return importance;}

}