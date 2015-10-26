package com.example.naoya.todomanager;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.exceptions.RealmMigrationNeededException;

public class ToDoAdaptor {
    private Realm realm;
    static final String ID_COLUMN_NAME = "index";


    private ToDoAdaptor(){}

    private static class SingletonHolder {
        private static final ToDoAdaptor instance = new ToDoAdaptor();
    }

    public static ToDoAdaptor getInstance() {
        return SingletonHolder.instance;
    }

    public void getRealmInstance(Context context, String fileName){
        try {
            realm = Realm.getInstance(context, fileName);
        } catch (RealmMigrationNeededException r) {
            Realm.deleteRealmFile(context, fileName);
            realm = Realm.getInstance(context, fileName);
        }
    }

    public void remove(int index) {
        realm.beginTransaction();
        realm.where(ToDoData.class).equalTo(ID_COLUMN_NAME, index).findFirst().removeFromRealm();//定数をどうにか
        realm.commitTransaction();
    }

    public int getResultSize(){
        return realm.where(ToDoData.class).findAll().size();
    }

    public ToDoData getToDoData(int index) {
        return realm.where(ToDoData.class).equalTo(ID_COLUMN_NAME, index).findFirst();
    }

    public void setToDoData(String title, String place, String comment, int imageResourceId,
                            int importance, Date dueDate, Date reminderDate,
                            boolean repeatFrag, boolean finishFrag) {

        ToDoData toDoData = new ToDoData();

        toDoData.setIndex((int)realm.where(ToDoData.class).maximumInt(ID_COLUMN_NAME) + 1);
        toDoData.setTitle(title);
        toDoData.setPlace(place);
        toDoData.setComment(comment);
        toDoData.setImageResourceId(imageResourceId);
        toDoData.setImportance(importance);
        toDoData.setRepeatFlag(repeatFrag);
        toDoData.setEditedDate(Calendar.getInstance().getTime());
        toDoData.setDueDate(dueDate);
        toDoData.setReminderDate(reminderDate);
        toDoData.setFinishFlag(finishFrag);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(toDoData);
        realm.commitTransaction();
    }

    public void setToDoData(int index, String title, String place, String comment, int imageResourceId,
                                 int importance, Date dueDate, Date reminderDate,
                                 boolean repeatFrag, boolean finishFrag){

        ToDoData toDoData = new ToDoData();

        toDoData.setIndex(index);
        toDoData.setTitle(title);
        toDoData.setPlace(place);
        toDoData.setComment(comment);
        toDoData.setImageResourceId(imageResourceId);
        toDoData.setImportance(importance);
        toDoData.setRepeatFlag(repeatFrag);
        toDoData.setEditedDate(Calendar.getInstance().getTime());
        toDoData.setDueDate(dueDate);
        toDoData.setReminderDate(reminderDate);
        toDoData.setFinishFlag(finishFrag);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(toDoData);
        realm.commitTransaction();
    }
    public void setRealmToCellDataList(List<CellData> list){

        for (ToDoData toDoData : realm.where(ToDoData.class).findAll()) {
            CellData cellData = new CellData(toDoData.getIndex(),toDoData.getImageResourceId(),
                    toDoData.getDueDate(), toDoData.getTitle());
            list.add(cellData);
            Log.d("myApp", "RW index = " + toDoData.getIndex());
        }
    }
}