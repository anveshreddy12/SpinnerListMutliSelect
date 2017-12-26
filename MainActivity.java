package com.example.andriod.spinnerlistmutliselect;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Dialog dialog;
    TextView textView;
    int mGroupPosition;
    String mGroupText;
    ExpandableListView expandableListView;
    ExpListViewAdapterWithCheckbox expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.SelectBox);
    }

    private void initializeListData(){

        listDataHeader = new ArrayList<>();
        listDataHeader.add("Projects");
        listDataHeader.add("All Residential");
        listDataHeader.add("All Commercial");

        List<String> projects = new ArrayList<>();
        projects.add("Residential Projects");
        projects.add("Commercial Projects");

        List<String> allResidentials = new ArrayList<>();
        allResidentials.add("Residential Apartment");
        allResidentials.add("Independent/Builder Floor");
        allResidentials.add("Independent House/Villa");
        allResidentials.add("Residential Land");
        allResidentials.add("Studio Apartment");
        allResidentials.add("Farm House");
        allResidentials.add("Serviced Apartments");
        allResidentials.add("Other");

        List<String> allCommercials = new ArrayList<>();
        allCommercials.add("Commercial Shops");
        allCommercials.add("Commercial Showrooms");
        allCommercials.add("Commercial Office/Space");
        allCommercials.add("Commercial Land/Inst. Land");
        allCommercials.add("Industrial Lands/Plots");
        allCommercials.add("Agricultural/Farm Land");
        allCommercials.add("Industrial Lands/Plots");
        allCommercials.add("Hotel/Resorts");
        allCommercials.add("Guest-House/Banquet-Halls");
        allCommercials.add("Time Share");
        allCommercials.add("Space in Retail Mall");
        allCommercials.add("Office in Business Park");
        allCommercials.add("Office in IT Park");
        allCommercials.add("Ware House");
        allCommercials.add("Cold Storage");
        allCommercials.add("Factory");
        allCommercials.add("Manufacturing");
        allCommercials.add("Business center");
        allCommercials.add("Other");

        listDataChild = new HashMap<>();
        listDataChild.put(listDataHeader.get(0), projects);
        listDataChild.put(listDataHeader.get(1), allResidentials);
        listDataChild.put(listDataHeader.get(2), allCommercials);
    }

    public void onDisplay(View view){

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        expandableListView = dialog.findViewById(R.id.exList);

        initializeListData();
        expandableListAdapter = new ExpListViewAdapterWithCheckbox(this,listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
        dialog.show();

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return  expandableListView.isGroupExpanded(groupPosition) ? expandableListView.collapseGroup(groupPosition) : expandableListView.expandGroup(groupPosition);
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition)
                                + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Button button = dialog.findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count =0;

                for (mGroupPosition = 0; mGroupPosition < expandableListAdapter.getGroupCount(); mGroupPosition++) {
                    mGroupText = expandableListAdapter.getGroupSelectedText();
                }

                for(mGroupPosition =0; mGroupPosition < expandableListAdapter.getGroupCount(); mGroupPosition++) {
                    count = count +  expandableListAdapter.getNumberOfCheckedItemsInGroup(mGroupPosition);
                }

                if (mGroupText != null) textView.setText(mGroupText);
                else  textView.setText(""+count+" selected");

                dialog.dismiss();
            }
        });
    }
}
