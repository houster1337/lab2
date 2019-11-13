package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> parents;
    Map<String, List<String>> children;
    ExpandableListAdapter listAdapter;
    String topText;
    EditText tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (EditText) findViewById(R.id.editText2);
        expandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);

        fillData();

        listAdapter = new MyExpandableListAdapter(this, parents, children);

        expandableListView.setAdapter(listAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                topText = ("/" + parents.get(groupPosition));
                tv.setText(topText);

                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                topText = ("/" + parents.get(groupPosition) + "/" + children.get(parents.get(groupPosition)).get(childPosition));
                tv.setText(topText);

                return false;
            }
        });

    }

    public void fillData(){

        parents = new ArrayList<>();
        children = new HashMap<>();

        parents.add("Brands");
        parents.add("Models");
        parents.add("Colors");

        List<String> Brands = new ArrayList<>();
        List<String> Models = new ArrayList<>();
        List<String> Colors = new ArrayList<>();

        Brands.add("Gucci");
        Brands.add("Tommy Hilfiger");

        Models.add("Round");
        Models.add("Squared");
        Models.add("Cat");

        Colors.add("Red");
        Colors.add("Black");
        Colors.add("Blue");

        children.put(parents.get(0), Brands);
        children.put(parents.get(1), Models);
        children.put(parents.get(2), Colors);

    }
}