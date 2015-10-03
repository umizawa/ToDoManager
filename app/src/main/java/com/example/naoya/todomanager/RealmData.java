package com.example.naoya.todomanager;

import android.renderscript.Element;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmData {
    private Realm realm;
    private RealmQuery<ToDoData> query;
    private RealmResults<ToDoData> result;

    public RealmData(Realm realm){
        this.realm = realm;
        this.query = this.realm.where(ToDoData.class);
    }

    public void query(){
    }
    public void remove(int id){
        realm.beginTransaction();
        result.remove(id);
        realm.commitTransaction();
    }
    public int getResultSize(){
        return result.size();
    }
    public ToDoData getToDoData(int index){
        return query.findAll().get(index);
    }

}
