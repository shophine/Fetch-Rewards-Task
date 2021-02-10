package com.example.fetch_rewards_task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;

public class IDRecyclerViewAdapter extends RecyclerView.Adapter<IDRecyclerViewAdapter.ViewHolder>{
    ArrayList<String> listIDs;
    Context context;
    OnIDRecyclerViewClickListener onIDRecyclerViewClickListener;

    public IDRecyclerViewAdapter(Context context,ArrayList<String> listIDs,OnIDRecyclerViewClickListener onIDRecyclerViewClickListener){
        this.context = context;
        this.listIDs = listIDs;
        this.onIDRecyclerViewClickListener = onIDRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //setting the layout view
        View view = layoutInflater.inflate(R.layout.id_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set the values from the data set to the view
         holder.textView.setText( listIDs.get(position));
         holder.button.setOnClickListener(new View.OnClickListener() {
             //when viewID button is clicked
            @Override
            public void onClick(View v) {

                onIDRecyclerViewClickListener.OnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listIDs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //initializing the elements of the view to the view holder
        TextView textView;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             textView = itemView.findViewById(R.id.list_id_name);
             button = itemView.findViewById(R.id.viewIDs);

        }
    }
    public interface OnIDRecyclerViewClickListener{
        void OnClick(int pos);
    }
}
