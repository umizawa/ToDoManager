package com.example.naoya.todomanager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class CellAdapter extends BaseAdapter implements Filterable {
    private Context context;
    android.widget.Filter filter;



    private List<CellData> cellDataList;
    private List<CellData> mOriginalCellDataList;

    public CellAdapter(Context context, List<CellData> cellDataList) {
        this.context = context;
        this.cellDataList = cellDataList;
        this.mOriginalCellDataList = cellDataList;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public android.widget.Filter getFilter(){

        filter = new android.widget.Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cellDataList = (List<CellData>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<CellData> filteredCellDataList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    results.values = mOriginalCellDataList;
                    results.count = mOriginalCellDataList.size();
                } else {
                    for (int i = 0; i < mOriginalCellDataList.size(); i++) {
                        CellData cellData = mOriginalCellDataList.get(i);
                        Log.d("cellData",cellData.getCellTitle());
                        if (cellData.getCellTitle().contains(constraint.toString()))  {
                            filteredCellDataList.add(cellData);
                        }
                    }
                    results.count = filteredCellDataList.size();
                    System.out.println(results.count);

                    results.values = filteredCellDataList;
                    Log.d("VALUES", results.values.toString());
                }
                return results;
            }
        };
        return filter;
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