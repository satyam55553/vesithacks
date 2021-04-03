package com.example.vesithacks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    private RecyclerViewClickListener listener;
    private final List<EventData> listEvents;

    public myAdapter(List<EventData> listEvents, RecyclerViewClickListener listener) {
        this.listEvents = listEvents;
        this.listener = listener;
    }

    public myAdapter(List<EventData> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        EventData e = listEvents.get(position);
        holder.eventName.setText(e.getEventName());
        holder.organiserName.setText(e.getOrganiser());
        holder.regDate.setText(e.getReg());
        holder.eventDate.setText(e.getEventDate());
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    //ViewHolder Class
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView eventName, organiserName, regDate, eventDate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name_txt);
            organiserName = (TextView) itemView.findViewById(R.id.organiser_txt);
            regDate = (TextView) itemView.findViewById(R.id.reg_date_txt);
            eventDate = (TextView) itemView.findViewById(R.id.event_date_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

}

