package com.example.naoya.todomanager;

import android.content.ComponentName;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.ContentHandler;
import java.util.List;

/**
 * Created by Naoya on 2015/07/30.
 */
public class CellAdapter extends BaseAdapter { //ggr
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
        } else {
            cellHolder = (CellHolder) convertView.getTag();
        }
        setCellData(cellDataList.get(position), cellHolder);

        return convertView;
    }

    private CellHolder genCellHolder(View convertView) {
        CellHolder cellHolder = new CellHolder();
        cellHolder.image = (ImageView) convertView.findViewById(R.id.image);
        cellHolder.title = (TextView) convertView.findViewById(R.id.title);
        cellHolder.dueDay = (TextView) convertView.findViewById(R.id.due_day);
        cellHolder.editetDay = (TextView) convertView.findViewById(R.id.edited_day);
        return cellHolder;
    }

    private void setCellData(CellData cellData, CellHolder cellHolder) { //ggr
        cellHolder.image.setImageResource(cellData.getImageResourceId());
        cellHolder.title.setText(cellData.getTitle());
        cellHolder.dueDay.setText(cellData.getMessage());
    }
    private class CellHolder {
        ImageView image;
        TextView title, dueDay, editetDay, importance;
    }
}