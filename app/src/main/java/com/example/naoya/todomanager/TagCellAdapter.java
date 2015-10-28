package com.example.naoya.todomanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TagCellAdapter extends BaseAdapter {
    private Context context;
    private List<TagCellData> tagCellDataList;

    public TagCellAdapter(Context context, List<TagCellData> tagCellDataList) {
        this.context = context;
        this.tagCellDataList = tagCellDataList;
    }

    @Override
    public int getCount() { return tagCellDataList.size(); }

    @Override
    public TagCellData getItem(int position) { return tagCellDataList.get(position);}

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CellHolder cellHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.table_tag_cell, null);
            cellHolder = genCellHolder(convertView);
            convertView.setTag(cellHolder);
            Log.d("getView", "null");
        } else {
            cellHolder = (CellHolder) convertView.getTag();
            Log.d("getView", "else");
        }
        setCellData(tagCellDataList.get(position), cellHolder);

        return convertView;
    }

    private CellHolder genCellHolder(View convertView) {
        CellHolder cellHolder = new CellHolder();
        cellHolder.tag = (TextView) convertView.findViewById(R.id.table_tag_cell_title);
        return cellHolder;
    }

    private void setCellData(TagCellData tagCellData, CellHolder cellHolder) {
        Log.d("name", tagCellData.getName());
        cellHolder.tag.setText(tagCellData.getName());
    }

    private class CellHolder {
        TextView tag;
    }
}