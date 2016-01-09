package com.userdata;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericchaney on 1/6/16.
 */
public class ListFrag extends ListFragment implements AdapterView.OnItemLongClickListener {

    ListView lv;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> entriesForAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        entriesForAdapter = new ArrayList<>();



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("user", ParseUser.getCurrentUser());


        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> posts, ParseException e) {

                for (int i = 0; i < posts.size(); i++) {

                    entriesForAdapter.add(posts.get(i).getString("title"));

                }

                adapter = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_list_item_1,
                        entriesForAdapter);

//                setListAdapter(new ArrayAdapter<String>(getActivity(),
//                        android.R.layout.simple_list_item_1, entriesForAdapter));

                setListAdapter(adapter);

            }
        });



        lv = getListView();

        lv.setOnItemLongClickListener(this);

//        adapter = (ArrayAdapter<String>) lv.getAdapter();


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        ParseUser user = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> posts, ParseException e) {
                if (e == null) {

                    posts.get(position).deleteInBackground();

                } else {

                }
            }
        });

       adapter.notifyDataSetChanged();

        return false;

    }
}
