package com.userdata;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
public class ListFrag extends ListFragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    ListView lv;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> entriesForAdapter;
    Bundle extras;
    Sync alarm;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         alarm = new Sync();

        entriesForAdapter = new ArrayList<>();
        //registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));
         extras = getArguments();
//        entriesForAdapter = extras.getStringArrayList("syncedData");

//adapter = new ArrayAdapter<String>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("user", ParseUser.getCurrentUser());


        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> posts, ParseException e) {
                //if(entriesForAdapter == null) {
                for (int i = 0; i < posts.size(); i++) {

                    entriesForAdapter.add(posts.get(i).getString("title"));

                }
                // }else{
                //entriesForAdapter = extras.getStringArrayList("syncedData");
                //}


                if (getActivity() != null) {
                    // code goes here.  Edited to add the
                    // missing brackets, needed more than 6 chars!

                    adapter = new ArrayAdapter(getActivity(),
                            android.R.layout.simple_list_item_1,
                            entriesForAdapter);

                }
//                setListAdapter(new ArrayAdapter<String>(getActivity(),
//                        android.R.layout.simple_list_item_1, entriesForAdapter));

                entriesForAdapter = extras.getStringArrayList("syncedData");
                setListAdapter(adapter);

            }
        });


        lv = getListView();

        lv.setOnItemLongClickListener(this);
        lv.setOnItemClickListener(this);

//        adapter = (ArrayAdapter<String>) lv.getAdapter();


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        ParseUser user = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("user", user);
        query.whereEqualTo("title", parent.getItemAtPosition(position));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> posts, ParseException e) {
                if (e == null && getOnline()) {

                    posts.get(0).deleteInBackground();
                    //entriesForAdapter.remove(position);
                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getActivity(),
                            "Delete Failed: Check Internet Connection",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

      // adapter.notifyDataSetChanged();

        return false;

    }


protected boolean getOnline(){
   return  ((EnterData) getActivity()).isOnline();


}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       // alarm.CancelAlarm(getActivity());

        Intent intent = new Intent(getActivity(), EditProfile.class);
        String clickedItem = (String) parent.getItemAtPosition(position);
        intent.putExtra("clickedItem", clickedItem);

        startActivity(intent);

    }
}
