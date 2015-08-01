package com.example.naoya.todomanager;

import java.lang.ref.SoftReference;
import java.util.Date;

/**
 * Created by Naoya on 2015/07/22.
 */
public class CellData {

    private int imageResourceId;
    private int importance;
    private Date editDay, dueDay;
    private String title, message;

    public CellData(int imageResourceId, int importance, Date editDay, Date dueDay, String title, String message){
        this.imageResourceId = imageResourceId;
        this.importance = importance;
        this.editDay = editDay;
        this.dueDay = dueDay;
        this.title = title;
        this.message = message;
    }

    public int getImageResourceId() {return imageResourceId;}
    public int getImportance() {return importance;}
    public Date getEditDay() {return editDay;}
    public Date getDueDay() {return dueDay;}
    public String getTitle() {return title;}
    public String getMessage() {return message;}

}