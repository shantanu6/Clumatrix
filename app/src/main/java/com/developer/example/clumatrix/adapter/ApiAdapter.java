package com.developer.example.clumatrix.adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.example.clumatrix.R;
import com.developer.example.clumatrix.menu.ApiActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ApiAdapter extends RecyclerView.Adapter<ApiViewHolder> {
    Context context;
    ArrayList<ApiModel> apiModel;
    
    public ApiAdapter(Context context, ArrayList<ApiModel> apiModel) {
        this.context=context;
        this.apiModel=apiModel;
    }

    @NonNull
    @Override
    public ApiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ApiViewHolder(LayoutInflater.from(context).inflate(R.layout.api_model,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApiViewHolder holder, int position) {
        holder.txtRfID.setText(apiModel.get(position).getRf_id());
        holder.txtCreated.setText(apiModel.get(position).getCreated_at());
        holder.txtUpdated.setText(apiModel.get(position).getUpdated_at());
    }

    @Override
    public int getItemCount() {
        return apiModel.size();
    }
}

class ApiViewHolder extends RecyclerView.ViewHolder{
    TextView txtRfID,txtCreated,txtUpdated;

    public ApiViewHolder(@NonNull View itemView) {
        super(itemView);
        txtRfID=itemView.findViewById(R.id.txtRfID);
        txtCreated=itemView.findViewById(R.id.txtCreated);
        txtUpdated=itemView.findViewById(R.id.txtUpdated);
    }
}