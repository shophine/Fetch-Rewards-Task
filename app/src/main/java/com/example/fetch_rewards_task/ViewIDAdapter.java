package com.example.fetch_rewards_task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewIDAdapter extends RecyclerView.Adapter<ViewIDAdapter.ViewHolder> {

    Context context;
    ArrayList<IDEntity> idEntities;

    public ViewIDAdapter(Context context,ArrayList<IDEntity> idEntities) {
        this.context = context;
        this.idEntities = idEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_list_ids,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(idEntities.get(position).id);
        holder.name.setText(idEntities.get(position).name);
    }

    @Override
    public int getItemCount() {
        return idEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.data_id);
            name = itemView.findViewById(R.id.data_name);
        }
    }
}
