package com.example.fetch_rewards_task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements IDRecyclerViewAdapter.OnIDRecyclerViewClickListener {
    private RecyclerView recyclerView;
    public  ArrayList<String> listIDs = new ArrayList<>();
    public  ArrayList<JSONArray> groupedJSONOnID = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        //initializing the recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        String url ="https://fetch-hiring.s3.amazonaws.com/hiring.json";
        //passing the url to the helper function to fetch the data
        getData(url);

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
                            List<JSONObject> jsonValues = new ArrayList<JSONObject>();

                            //function that create the list of IDs
                            createListIDs(jsonArray);

                            //converting JSON Array to List for sorting
                            for(int i=0;i<jsonArray.length();i++){
                                jsonValues.add(jsonArray.getJSONObject(i));
                            }
                            //sorting the listID
                            Collections.sort(listIDs);

                            //sorting the data by name
                            sortDataByName(jsonValues);

                            //store the sorted value from list to json array
                            JSONArray sortedJson = new JSONArray();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sortedJson.put(jsonValues.get(i));
                            }

                            //function that groups data by it's list ID
                            groupDataByListID(sortedJson);

                            //sending the data list to recycler view for displaying
                            IDRecyclerViewAdapter idRecyclerViewAdapter = new IDRecyclerViewAdapter(HomeActivity.this,listIDs,HomeActivity.this);

                            //setting the recycler view and the adapter
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL,false));
                            recyclerView.setAdapter(idRecyclerViewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,"Data Fetch Failed",Toast.LENGTH_SHORT);
            }
        });

        queue.add(stringRequest);
    }

    //function that sorts JSON object by it's key
    private void sortDataByName(List<JSONObject> jsonValues) {
        Collections.sort( jsonValues, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "name";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = a.getString(KEY_NAME);
                    valB = b.getString(KEY_NAME);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                return valA.compareTo(valB);
            }
        });

    }

    //function that groups data by it's list ID
    private void groupDataByListID(JSONArray sortedJson) throws JSONException {
        //traversing every listID
        for (String listid:listIDs) {

            //creating a json array for every listID
            JSONArray listIDJsonArray = new JSONArray();
            //traversing the json data
            for(int i=0;i<sortedJson.length();i++){
                //getting JSON object from the array
                JSONObject jsonObject = sortedJson.getJSONObject(i);
                String nameInList = null;
                //if the name is not null, set the name
                if(! jsonObject.isNull("name")){
                    nameInList = jsonObject.getString("name");
                }
                String idInList = jsonObject.getString("listId");

                //check if the listID in the json data and list of listIDs array are same
                if(listid.equals(idInList)){
                    //check if the name field is not null or is not empty
                    if(nameInList != null){
                        if(!nameInList.trim().isEmpty()){
                            //it's a valid data, so add it to the array
                            listIDJsonArray.put(jsonObject);
                        }
                    }
                }
            }
            //add the listIDJsonArray to the groupedList
            groupedJSONOnID.add(listIDJsonArray);
        }
    }

    //function to create a list of 'listId's
    private void createListIDs(JSONArray jsonArray) throws JSONException {
        for(int i=0;i<jsonArray.length();i++) {
            //capturing every element as JSONObject
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            //getting the listID from the JSON Object
            String listId = jsonObject.getString("listId");
            //check of it's already exist in the list
            if(! listIDs.contains(listId)){
                //add to the list if it doesn't exist
                listIDs.add(listId);
            }
            //ignore if the id is already there in the list
        }
    }

    //when the view ID button is clicked
    @Override
    public void OnClick(int pos) {
        JSONArray jsonArray = groupedJSONOnID.get(pos);

        Intent intent = new Intent(this, ViewIDActivity.class);
        //pass the data to the new activity for displaying
        intent.putExtra("data",jsonArray.toString());
        startActivity(intent);


    }
}
