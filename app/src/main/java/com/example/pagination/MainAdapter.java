package com.example.pagination;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private ArrayList<MainData> dataArrayList;

    private Activity activity;


public MainAdapter(Activity activity,ArrayList<MainData>dataArrayList){
    this.activity=activity;
    this.dataArrayList=dataArrayList;
}


    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
MainData data= dataArrayList.get(position);
holder.t1.setText(data.getTitle());
holder.t2.setText(data.getBody());

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.t1);
             t2=itemView.findViewById(R.id.t2);

        }
    }
}
