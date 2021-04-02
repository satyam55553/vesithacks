package com.example.vesithacks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.myViewHolder> {
    private AdminAdapter.RecyclerViewClickListener listener;

    private final String[] data;

    public AdminAdapter(String[] data, AdminAdapter.RecyclerViewClickListener listener) {
        this.data=data;
        this.listener = listener;
    }

    public AdminAdapter(String[] data) {
        this.data = data;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_admin_item, parent, false);
        return new AdminAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String name = data[position];
        holder.datatxt.setText(name);
    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    //ViewHolder Class
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView datatxt;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            datatxt = (TextView) itemView.findViewById(R.id.admin_item_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

}
