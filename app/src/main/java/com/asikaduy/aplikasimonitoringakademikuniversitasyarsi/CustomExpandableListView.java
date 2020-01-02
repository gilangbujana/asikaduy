package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListView extends BaseExpandableListAdapter {


    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<HashMap<String, String>>> expandableListDetail;

    public CustomExpandableListView(Context context, List<String>
            expandableListTitle, HashMap<String, List<HashMap<String, String>>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final HashMap<String, String> expandedList = (HashMap<String, String>) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.desain_listnilaidetail, null);
        }

        TextView kode_k = (TextView) convertView
                .findViewById(R.id.kode_k);
        TextView txtNamalkp = (TextView) convertView
                .findViewById( R.id.txtNamalkp);
        TextView txtDeskripsilkp = (TextView) convertView
                .findViewById(R.id.txtDeskripsilkp);

        kode_k.setText(expandedList.get("id_nilaidetail"));
        txtNamalkp.setText(expandedList.get("npm"));
        txtDeskripsilkp.setText(expandedList.get("id_nilai"));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.desain_listnilaidetailparent, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.txtNamalkp);
        listTitleTextView.setText(listTitle);
        return convertView;
    }
}
