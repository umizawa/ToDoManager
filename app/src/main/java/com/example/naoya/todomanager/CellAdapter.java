package com.example.naoya.todomanager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

public class CellAdapter extends BaseAdapter {
    private Context context;
    private List<CellData> cellDataList;

    public CellAdapter(Context context, List<CellData> cellDataList) {
        this.context = context;
        this.cellDataList = cellDataList;
    }

    @Override
    public int getCount() { return cellDataList.size(); }

    @Override
    public CellData getItem(int position) { return cellDataList.get(position);}

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CellHolder cellHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.table_cell, null);
            cellHolder = genCellHolder(convertView);
            convertView.setTag(cellHolder);
            Log.d("getView", "null");
        } else {
            cellHolder = (CellHolder) convertView.getTag();
            Log.d("getView", "else");
        }
        setCellData(cellDataList.get(position), cellHolder);

        return convertView;
    }

    private CellHolder genCellHolder(View convertView) {
        CellHolder cellHolder = new CellHolder();
        cellHolder.image = (ImageView) convertView.findViewById(R.id.image);
        cellHolder.cellTitle = (TextView) convertView.findViewById(R.id.table_cell_title);
        cellHolder.dueDay = (TextView) convertView.findViewById(R.id.due_day);
        return cellHolder;
    }

    private void setCellData(CellData cellData, CellHolder cellHolder) {
        cellHolder.image.setImageResource(cellData.getCellImageResourceId());
        cellHolder.cellTitle.setText(cellData.getCellTitle());
        if(cellData.getImportance() >= 3) {
            cellHolder.cellTitle.setTextColor(Color.RED);
        } else {
            cellHolder.cellTitle.setTextColor(Color.BLACK);
        }
        cellHolder.dueDay.setText(convertDateToString(cellData.getCellDueDay()));
    }

    public String convertDateToString(java.util.Date date) {
        return DateFormat.getDateInstance().format(date);
    }

    private class CellHolder {
        ImageView image;
        TextView cellTitle, dueDay;
    }
}