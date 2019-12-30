package com.example.shafkat.emergencyshake.ContactAll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>  {

    private Context context;
    private List<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            event = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.event.setText(event.getEvent());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
//        holder.timestamp.setText(formatDate(event.getTimestamp()));

        holder.timestamp.setText(event.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
