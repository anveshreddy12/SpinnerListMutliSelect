package com.example.andriod.spinnerlistmutliselect;

/**
 * Created by dbhat on 15-03-2016.
 */

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpListViewAdapterWithCheckbox extends BaseExpandableListAdapter {

    private Context mContext;
    private HashMap<String, List<String>> mListDataChild;
    private ArrayList<String> mListDataGroup;
    private int selectedIndex = -1;
    private HashMap<Integer,boolean[]> mChildCheckStates, mGroupCheckStates;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;
    private String groupText;
    private String childText;


    public ExpListViewAdapterWithCheckbox(Context context, ArrayList<String> listDataGroup, HashMap<String, List<String>> listDataChild){

        mContext = context;
        mListDataGroup = listDataGroup;
        mListDataChild = listDataChild;
        mChildCheckStates = new HashMap<>();
        mGroupCheckStates = new HashMap<>();
    }

    public int getNumberOfCheckedItemsInGroup(int mGroupPosition)
    {
        boolean getChildChecked[] = mChildCheckStates.get(mGroupPosition);

        int count = 0;
        if(getChildChecked != null) {
            for (int j = 0; j < getChildChecked.length; ++j) {
                if (getChildChecked[j]) count++;
            }
        }
        return  count;
    }

    public String getGroupSelectedText(){
        String mGroupText = null;
        if (groupText != null) mGroupText = groupText;
        return mGroupText;
    }

    public int getNumberOfGroupCheckedItems(int mGroupPosition)
    {
        boolean getGroupChecked[] = mGroupCheckStates.get(mGroupPosition);
        int count = 0;
        if(getGroupChecked != null) {
            for (int j = 0; j < getGroupChecked.length; ++j) {
                if (getGroupChecked[j]) count++;
            }
        }
        return  count;
    }

    @Override public int getGroupCount() {
        return mListDataGroup.size();
    }

    @Override public String getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        groupViewHolder = new GroupViewHolder();
        final int mGroupPosition = groupPosition;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.list_group, null);

                groupViewHolder.mGroupText = convertView.findViewById(R.id.lblListHeader);

                groupViewHolder.mGroupCheckBox = convertView.findViewById(R.id.lblListCheckBox);

                convertView.setTag(R.layout.list_group, groupViewHolder);
            }
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag(R.layout.list_group);
        }

        groupViewHolder.mGroupText.setText(getGroup(groupPosition));

        groupViewHolder.mGroupCheckBox.setOnCheckedChangeListener(null);

        if (mGroupCheckStates.containsKey(mGroupPosition)) {

            boolean getChecked[] = mGroupCheckStates.get(mGroupPosition);

            if (mGroupPosition == selectedIndex)
            groupViewHolder.mGroupCheckBox.setChecked(getChecked[mGroupPosition]);
            else groupViewHolder.mGroupCheckBox.setChecked(false);
        } else {

            boolean getChecked[] = new boolean[getGroupCount()];

            mGroupCheckStates.put(mGroupPosition, getChecked);

            groupViewHolder.mGroupCheckBox.setChecked(false);
        }

        groupViewHolder.mGroupCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    boolean getChecked[] = mGroupCheckStates.get(mGroupPosition);
                        getChecked[mGroupPosition] = isChecked;
                        mGroupCheckStates.put(mGroupPosition, getChecked);
                        groupText = mListDataGroup.get(mGroupPosition);
                        selectedIndex = mGroupPosition;
                } else {

                    boolean getChecked[] = mGroupCheckStates.get(mGroupPosition);
                        getChecked[mGroupPosition] = isChecked;
                        mGroupCheckStates.put(mGroupPosition, getChecked);
                        selectedIndex= -1;
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).size();
    }

    @Override public String getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).get(childPosition);
    }

    @Override public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        childText = getChild(mGroupPosition, mChildPosition);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                childViewHolder = new ChildViewHolder();

                childViewHolder.mChildText = convertView.findViewById(R.id.lblListItem);

                childViewHolder.mChildCheckBox = convertView.findViewById(R.id.lstcheckBox);

                convertView.setTag(R.layout.list_item, childViewHolder);
            }

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.list_item);
        }

        childViewHolder.mChildText.setText(childText);

        childViewHolder.mChildCheckBox.setOnCheckedChangeListener(null);

        if (mChildCheckStates.containsKey(mGroupPosition)) {
            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
            childViewHolder.mChildCheckBox.setChecked(getChecked[mChildPosition]);

        } else {
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
            mChildCheckStates.put(mGroupPosition, getChecked);
            childViewHolder.mChildCheckBox.setChecked(false);
        }

        childViewHolder.mChildCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                } else {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                }
            }
        });

        return convertView;
    }

    @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {
        TextView mGroupText;
        CheckBox mGroupCheckBox;
    }

    public final class ChildViewHolder {
        TextView mChildText;
        CheckBox mChildCheckBox;
    }
}


