package com.example.shafkat.emergencyshake.ContactAll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.Group.RenameGroup;
import com.example.shafkat.emergencyshake.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventDateCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<EventDate> objects;
    String parsedDate1;

    public EventDateCustomAdapter(Context context, ArrayList<EventDate> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.event_list_items, parent, false);
        }

        EventDate eventDate = getAllGroup(position);

        String time = eventDate.getEventDate();

        try {
            Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            parsedDate1 = formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.EventName)).setText(eventDate.getEventName());
        ((TextView) view.findViewById(R.id.EventDate)).setText(parsedDate1+ "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.event_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(eventDate.box);
        return view;

    }

    EventDate getAllGroup(int position) {
        return ((EventDate) getItem(position));
    }

    ArrayList<EventDate> getBox() {
        ArrayList<EventDate> box = new ArrayList<EventDate>();
        for (EventDate friends : objects) {
            if (friends.box)
                box.add(friends);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getAllGroup((Integer) buttonView.getTag()).box = isChecked;

        }
    };
}
