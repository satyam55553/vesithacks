package com.example.vesithacks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    private RecyclerViewClickListener listener;

    private String[] eventnamedata, organisernamedata, regdatedata, eventdatedata;

    public myAdapter(String[] eventnamedata,
                     String[] organisernamedata,
                     String[] regdatedata,
                     String[] eventdatedata,
                     RecyclerViewClickListener listener) {
        this.eventnamedata = eventnamedata;
        this.organisernamedata = organisernamedata;
        this.regdatedata = regdatedata;
        this.eventdatedata = eventdatedata;
        this.listener = listener;
    }

    public myAdapter(String[] data) {
        this.eventnamedata = data;
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
        String name = eventnamedata[position];
        holder.eventName.setText(name);
        holder.organiserName.setText(organisernamedata[position]);
        holder.regDate.setText(regdatedata[position]);
        holder.eventDate.setText(eventdatedata[position]);
    }

    @Override
    public int getItemCount() {
        return eventnamedata.length;
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

