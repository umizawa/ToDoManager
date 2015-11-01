package com.example.naoya.todomanager.CellAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.naoya.todomanager.R;

import java.util.ArrayList;
import java.util.List;

public class TagCellAdapter extends BaseAdapter implements Filterable{
    private Context context;
    android.widget.Filter filter;

    private List<TagCellData> tagCellDataList;
    private List<TagCellData> mOriginalTagCellDataList;

    public TagCellAdapter(Context context, List<TagCellData> tagCellDataList) {
        this.context = context;
        this.tagCellDataList = tagCellDataList;
        this.mOriginalTagCellDataList = tagCellDataList;
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
                tagCellDataList = (List<TagCellData>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<TagCellData> filteredCellDataList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    results.values = mOriginalTagCellDataList;
                    results.count = mOriginalTagCellDataList.size();
                } else {
                    for (int i = 0; i < mOriginalTagCellDataList.size(); i++) {
                        TagCellData tagCellData = mOriginalTagCellDataList.get(i);
                        Log.d("cellData", tagCellData.getName());
                        if (tagCellData.getName().contains(constraint.toString()))  {
                            filteredCellDataList.add(tagCellData);
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