package com.example.fetch_rewards_task;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

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

                            ArrayList<Integer> uniqueListID = new ArrayList<>();

                            for(int i=0;i<3;i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                System.out.println("Id : "+ jsonObject.getString("id"));
                                System.out.println("listID : "+ jsonObject.getString("listId"));
                                System.out.println("Name : "+ jsonObject.getString("name"));
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
}
