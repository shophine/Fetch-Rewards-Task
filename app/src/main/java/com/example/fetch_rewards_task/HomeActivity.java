package com.example.fetch_rewards_task;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Integer> listIDs = new ArrayList<>();
    ArrayList<JSONArray> groupedJSONOnID = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        getData();
        initAdapter();
    }

    private void initAdapter() {
        ArrayList<String > exampleList = new ArrayList<>();

    }


    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://fetch-hiring.s3.amazonaws.com/hiring.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));

                        try{
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                createListIDs(jsonObject.getString("listId"));
//                                System.out.println("Id : "+ jsonObject.getString("id"));
//                                System.out.println("listID : "+ jsonObject.getString("listId"));
//                                System.out.println("Name : "+ jsonObject.getString("name"));
                            }
                            Collections.sort(listIDs);
                            for (int listid:listIDs) {
                                JSONArray jsonArray1 = new JSONArray();
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String idInList = jsonObject.getString("listId");
                                    String nameInList = jsonObject.getString("name");
                                    if(listid == Integer.parseInt(idInList)){
//                                        System.out.println("Hey"+nameInList);
                                        if(nameInList != null || !nameInList.trim().isEmpty()) {
                                            jsonArray1.put(jsonObject);
                                        }
                                    }
                                }
                                groupedJSONOnID.add(jsonArray1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,"Fetch Failed",Toast.LENGTH_SHORT);
            }
        });

        queue.add(stringRequest);

    }

    private void createListIDs(String listId) {
        int i = Integer.parseInt(listId);
        if(! listIDs.contains(i)){
            listIDs.add(i);
        }

    }
}
