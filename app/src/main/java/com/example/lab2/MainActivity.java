package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> parents;
    Map<String, List<String>> children;
    ExpandableListAdapter listAdapter;
    String topText;
    Context context;
    int temp;
    int partemp = -1;
    int chitemp = -1;
    final MainActivity dis = this;
    public boolean flagChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        final EditText edittext = (EditText) findViewById(R.id.editText2);
        expandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        topText = "/";
        fillData();

        listAdapter = new MyExpandableListAdapter(this, parents, children);

        expandableListView.setAdapter(listAdapter);


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                dis.flagChanged = true;

                if (partemp != -1 && chitemp != -1) {

                    int index2 = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(partemp, chitemp));
                    expandableListView.getChildAt(index2).setBackgroundColor(Color.WHITE);
                    partemp = -1;
                    chitemp = -1;

                }
                if(parent.isGroupExpanded(groupPosition)){
                        topText = "/";
                }
                else {
                    topText = ("/" + parents.get(groupPosition));
                }

                edittext.setText(topText);


                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                dis.flagChanged = true;
                if (partemp != -1 && chitemp != -1) {

                    int index2 = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(partemp, chitemp));
                    expandableListView.getChildAt(index2).setBackgroundColor(Color.WHITE);

                }
                int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                expandableListView.getChildAt(index).setBackgroundColor(Color.GRAY);


                partemp = groupPosition;
                chitemp = childPosition;

                topText = ("/" + parents.get(groupPosition) + "/" + children.get(parents.get(groupPosition)).get(childPosition));
                edittext.setText(topText);


                return false;
            }
        });


        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!dis.flagChanged) {
                    String abc = edittext.getText().toString();
                    StringTokenizer st = new StringTokenizer(abc, "/");
                    LinkedList<String> names = new LinkedList<>();
                    int counter = 0;

                    while (counter < 2 && st.hasMoreTokens()) {
                        names.add(st.nextToken());
                        counter++;
                    }

                    boolean con = true;

                    for (int i = 0; con && i < parents.size(); i++) {
                        if (names.size() > 0 && parents.get(i).startsWith(names.get(0)) || names.size() == 0 && abc.equals("/")) {
                            con = false;

                            edittext.setBackgroundColor(Color.WHITE);
                            expandableListView.expandGroup(i);
                            boolean com = true;
                            for (int j = 0; com && j < 3; j++) {
                                if (names.size() > 1 && children.get(parents.get(i)).get(j).startsWith(names.get(1)) || names.size() == 1 && abc.equals(parents.get(i) + "/")) {

                                    com = false;

                                    expandableListView.getChildAt(temp).setBackgroundColor(Color.WHITE);

                                    int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(i, j));
                                    expandableListView.getChildAt(index).setBackgroundColor(Color.GRAY);
                                    temp = index;
                                    partemp = i;
                                    chitemp = j;
                                }

                            }
                            if (com && names.size() > 1) {
                                edittext.setBackgroundColor(Color.RED);
                            }
                        } else {
                            edittext.setBackgroundColor(Color.RED);

                        }

                    }
                }else{
                    flagChanged = false;
                }
            }
        });


    }


    public void fillData() {

        parents = new ArrayList<>();
        children = new HashMap<>();

        parents.add("Brands");
        parents.add("Models");
        parents.add("Colors");

        List<String> Brands = new ArrayList<>();
        List<String> Models = new ArrayList<>();
        List<String> Colors = new ArrayList<>();

        Brands.add("Gucci");
        Brands.add("Prada");
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