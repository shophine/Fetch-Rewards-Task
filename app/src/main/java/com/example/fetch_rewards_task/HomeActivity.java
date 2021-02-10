package com.example.fetch_rewards_task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeActivity extends AppCompatActivity implements IDRecyclerViewAdapter.OnIDRecyclerViewClickListener {
    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;

    public  ArrayList<String> listIDs = new ArrayList<>(); //1,2,3,4
    public  ArrayList<JSONArray> groupedJSONOnID = new ArrayList<>(); //4
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        String url ="https://fetch-hiring.s3.amazonaws.com/hiring.json";
        //passing the url to the helper function to fetch the data
//        System.out.println("Before Function");
//        System.out.println(listIDs.size());
        getData(url);





//
//        System.out.println("After Function");
//        System.out.println(listIDs.size());



//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL  , false);
//        mAdapter = new ItemAdapter(fruitsItemList,this,1);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);

    }

    //function to get response data from the given URL
    private void getData(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            //capturing the response in json array
                            JSONArray jsonArray = new JSONArray(response);

                            //traversing the json array
                            for(int i=0;i<jsonArray.length();i++){

                                //capturing every element as JSONObject
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                //creating the list of 'listID's
                                createListIDs(jsonObject.getString("listId"));
                            }
                            //1,2,4,3
                            //sorting the listID
                            Collections.sort(listIDs);
                            //1,2,3,4

                            //traversing every listID
                            for (String listid:listIDs) {

                                //creating a json array for every listID
                                JSONArray listIDJsonArray = new JSONArray();
                                //traversing the json data
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String idInList = jsonObject.getString("listId");
                                    String nameInList = jsonObject.getString("name");
                                    //check if the listID in the json data and list of listIDs array are same
                                    if(listid.equals(idInList)){
                                        //check if the name field is not null or is not empty
                                        if(nameInList != null || !nameInList.trim().isEmpty()) {
                                            //it's a valid data, so add it to the array
                                            listIDJsonArray.put(jsonObject);
                                        }
                                    }
                                }
                                //add the listIDJsonArray to the groupedList
                                groupedJSONOnID.add(listIDJsonArray);
                                //a = [{1},{1},{1}],[{2},{2},{2}],[{1},{1},{1}],[{1},{1},{1}]
                                //groupedJSONOnID = [a]
                                //groupedJSONOnID.get(0) = id = 1
                                //groupedJSONOnID.get(1) = id = 2
                                //groupedJSONOnID.get(2) = id = 3
                                //groupedJSONOnID.get(3) = id = 4
                                

                            }
                            System.out.println("Inside function");
                            System.out.println(listIDs.size());

//                            ArrayList<String>temp = new ArrayList<>();
//                            temp.add("1");
//                            temp.add("2");
//                            temp.add("3");
//                            temp.add("4");


                            IDRecyclerViewAdapter idRecyclerViewAdapter = new IDRecyclerViewAdapter(HomeActivity.this,listIDs,HomeActivity.this);

                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL,false));
                            recyclerView.setAdapter(idRecyclerViewAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,"Fetch Failed",Toast.LENGTH_SHORT);
            }
        });

        queue.add(stringRequest);



    }

    //function to create a list of 'listId's
    private void createListIDs(String listId) {

        //check of it's already exist in the list
        if(! listIDs.contains(listId)){
            //add to the list if it doesn't exist
            listIDs.add(listId);
        }
        //ignore if the id is already there in the list

    }

    @Override
    public void OnClick(int pos) {
        JSONArray jsonArray = groupedJSONOnID.get(pos);

        Intent intent = new Intent(this,IDView.class);
        intent.putExtra("data",jsonArray.toString());
        startActivity(intent);


    }
}
