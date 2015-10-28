package com.example.naoya.todomanager;

public class TagCellData {
    private int id;
    private String name;

    public TagCellData(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
