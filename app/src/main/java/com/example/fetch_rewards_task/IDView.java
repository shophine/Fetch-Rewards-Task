package com.example.fetch_rewards_task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class IDView extends AppCompatActivity {
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
            JSONArray array = new JSONArray(jsonArray);
            System.out.println(array.toString(2));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}