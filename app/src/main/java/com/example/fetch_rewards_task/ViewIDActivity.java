package com.example.fetch_rewards_task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewIDActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        //initializing the recycler view
        recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        //getting the value from the intent
        String jsonArray = intent.getStringExtra("data");

        try {
            ArrayList<IDEntity> arrayList = new ArrayList<>();
            //object-mapping
            JSONArray array = new JSONArray(jsonArray);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                IDEntity idEntity = new IDEntity(jsonObject.getString("id"),jsonObject.getString("name"));
                arrayList.add(idEntity);
            }
            //sending the data list to recycler view for displaying
           ViewIDAdapter viewIDAdapter = new ViewIDAdapter(this,arrayList);

            //setting the recycler view and the adapter
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewIDActivity.this, LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(viewIDAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}