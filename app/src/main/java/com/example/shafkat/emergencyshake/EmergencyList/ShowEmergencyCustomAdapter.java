package com.example.shafkat.emergencyshake.EmergencyList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;

public class ShowEmergencyCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ShowEmergency> objects1;

    public ShowEmergencyCustomAdapter(Context context, ArrayList<ShowEmergency> products) {
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
            view = lInflater.inflate(R.layout.showemergencyitems, parent, false);
        }

        ShowEmergency showEmergency = getFriends(position);

        ((TextView) view.findViewById(R.id.showEmergencyName)).setText(showEmergency.name);
        ((TextView) view.findViewById(R.id.showEmergencyNumber)).setText(showEmergency.number + "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.emergencyshow_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(showEmergency.box);
        return view;

    }

    private ShowEmergency getFriends(int position) {
        return ((ShowEmergency) getItem(position));
    }

    ArrayList<ShowEmergency> getBox() {
        ArrayList<ShowEmergency> box = new ArrayList<ShowEmergency>();
        for (ShowEmergency friends : objects1) {
            if (friends.box)
                box.add(friends);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getFriends((Integer) buttonView.getTag()).box = isChecked;

        }
    };
}
