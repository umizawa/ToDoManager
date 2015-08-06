package com.example.naoya.todomanager;

import java.lang.ref.SoftReference;
import java.util.Date;

/**
 * Created by Naoya on 2015/07/22.
 */
public class CellData {

    private int imageResourceId;
//    private int importance;
    private Date  dueDay;//,editDay;
    private String title;//,message;

//    public CellData(int imageResourceId, int importance, Date editDay, Date dueDay, String title, String message){
    public CellData(int imageResourceId, Date dueDay, String title){
        this.imageResourceId = imageResourceId;
        this.dueDay = dueDay;
        this.title = title;
//        this.importance = importance;
//        this.editDay = editDay;
//        this.message = message;
    }

    public int getImageResourceId() {return imageResourceId;}
    public Date getDueDay() {return dueDay;}
    public String getTitle() {return title;}
//    public int getImportance() {return importance;}
//    public Date getEditDay() {return editDay;}
//    public String getMessage() {return message;}

}