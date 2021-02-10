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

        recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("data");

        try {
            ArrayList<IDEntity> arrayList = new ArrayList<>();
            JSONArray array = new JSONArray(jsonArray);
           // System.out.println(array.toString(2));
            //System.out.println(array);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                IDEntity idEntity = new IDEntity(jsonObject.getString("id"),jsonObject.getString("name"));
                arrayList.add(idEntity);
            }
            //send array list to recycler view


           ViewIDAdapter viewIDAdapter = new ViewIDAdapter(this,arrayList);

            recyclerView.setLayoutManager(new LinearLayoutManager(ViewIDActivity.this, LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(viewIDAdapter);




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}