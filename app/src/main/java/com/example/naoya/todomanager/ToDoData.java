package com.example.naoya.todomanager;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ToDoData extends RealmObject {
    @PrimaryKey
    private int id;

    private String title;
    private String place;
    private String comment;
    private int imageResourceId;
    private int importance;
    private boolean repeatFlag;
    private Date editedDate;
    private Date dueDate;
    private Date reminderDate;
    private boolean finishFlag;
    private String tag;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getPlace() {return place;}
    public void setPlace(String place) {this.place = place;}
    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}
    public int getImageResourceId() {return imageResourceId;}
    public void setImageResourceId(int imageResourceId) {this.imageResourceId = imageResourceId;}
    public int getImportance() {return importance;}
    public void setImportance(int importance) {this.importance = importance;}
    public boolean getRepeatFlag() {return repeatFlag;}
    public void setRepeatFlag(boolean repeatFlag) {this.repeatFlag = repeatFlag;}
    public Date getEditedDate() {return editedDate;}
    public void setEditedDate(Date editedDate) {this.editedDate = editedDate;}
    public Date getDueDate() {return dueDate;}
    public void setDueDate(Date dueDate) {this.dueDate = dueDate;}
    public Date getReminderDate() {return reminderDate;}
    public void setReminderDate(Date reminderDate) {this.reminderDate = reminderDate;}
    public boolean getFinishFlag() {return finishFlag;}
    public void setFinishFlag(boolean finishFlag) {this.finishFlag = finishFlag;}
    public String getTag() {return tag;}
    public void setTag(String tag) {this.tag = tag;}
}

