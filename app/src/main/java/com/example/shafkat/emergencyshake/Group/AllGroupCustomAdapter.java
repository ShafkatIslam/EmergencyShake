package com.example.shafkat.emergencyshake.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.EmergencyList.Emergency;
import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;

public class AllGroupCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<AllGroup> objects;

    public AllGroupCustomAdapter(Context context, ArrayList<AllGroup> products) {
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
            view = lInflater.inflate(R.layout.allgroupitems, parent, false);
        }

        AllGroup allGroup = getAllGroup(position);

        ((TextView) view.findViewById(R.id.AllGroupName)).setText(allGroup.name);
        ((TextView) view.findViewById(R.id.AllGroupNumber)).setText(allGroup.number + "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.allgroup_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(allGroup.box);
        return view;

    }

    AllGroup getAllGroup(int position) {
        return ((AllGroup) getItem(position));
    }

    ArrayList<AllGroup> getBox() {
        ArrayList<AllGroup> box = new ArrayList<AllGroup>();
        for (AllGroup friends : objects) {
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
