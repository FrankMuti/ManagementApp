package com.example.blacksky.datastructures;

import android.content.Context;
import android.support.design.button.MaterialButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.example.blacksky.R;

public class ListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String, List<String>> childTitles;
    List<String> headerTitles;

    public ListAdapter(Context context, List<String> headerTitles, HashMap<String, List<String>> childTitles){
        this.context = context;
        this.childTitles = childTitles;
        this.headerTitles = headerTitles;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerTitles.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this.headerTitles.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_header, null);
        }

      //  MaterialButton materialButton = convertView.findViewById(R.id.confirmClientBtn);
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.headerTitle);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childTitles.get(this.headerTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childTitles.get(this.headerTitles.get(groupPosition)).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_item, null);
        }

        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.childItem);
        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
