package com.example.naoya.todomanager;

import java.util.Date;

import io.realm.RealmObject;

public class ToDoData extends RealmObject{
    private String title;
    private String place;
    private String comment;
    private int imageResourceId;
    private int importance;
    private boolean repeatFlag;
    private Date editDay;
    private Date dueDay;
    private boolean finishFlag;

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getPlace(){return place;}
    public void setPlace(String place){this.place = place;}
    public String getComment(){return comment;}
    public void setComment(String comment){this.comment = comment;}
    public int getImageResourceId(){return imageResourceId;}
    public void setImageResourceId(int imageResourceId){this.imageResourceId = imageResourceId;}
    public int getImportance(){return importance;}
    public void setImportance(int importance){this.importance = importance;}
    public boolean getRepeatFlag(){return repeatFlag;}
    public void setRepeatFlag(boolean repeatFlag){this.repeatFlag = repeatFlag;}
    public Date getEditDay(){return editDay;}
    public void setEditDay(Date editDay){this.editDay = editDay;}
    public Date getDueDay(){return dueDay;}
    public void setDueDay(Date dueDay){this.dueDay = dueDay;}
    public boolean getFinishFlag(){return finishFlag;}
    public void setFinishFlag(boolean finishFlag){this.finishFlag = finishFlag;}
}

