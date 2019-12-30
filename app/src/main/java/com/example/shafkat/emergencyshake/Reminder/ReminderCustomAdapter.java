package com.example.shafkat.emergencyshake.Reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.GroupContact.ShowAllGroup;
import com.example.shafkat.emergencyshake.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReminderCustomAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Reminder> objects1;

    String parsedDate1;

    public ReminderCustomAdapter(Context context, ArrayList<Reminder> products) {
        ctx = context;
        objects1 = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects1.size();
    }

    @Override
    public Object getItem(int position) {
        return objects1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.reminder_list, parent, false);
        }

        Reminder reminder = getFriends(position);

        String time = reminder.getEventDate();

        try {
            Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            parsedDate1 = formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.reminderEventName)).setText(reminder.eventName);
        ((TextView) view.findViewById(R.id.reminderEventContactName)).setText(reminder.eventContactName);
        ((TextView) view.findViewById(R.id.reminderEventDate)).setText(parsedDate1 + "");

        return view;
    }

    private Reminder getFriends(int position) {
        return ((Reminder) getItem(position));
    }


}
