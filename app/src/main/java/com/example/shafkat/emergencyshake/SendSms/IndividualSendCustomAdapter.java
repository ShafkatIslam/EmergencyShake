package com.example.shafkat.emergencyshake.SendSms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.Group.AllGroup;
import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;

public class IndividualSendCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<IndividualSend> objects;

    public IndividualSendCustomAdapter(Context context, ArrayList<IndividualSend> products) {
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
            view = lInflater.inflate(R.layout.individualsenditems, parent, false);
        }

        IndividualSend individualSend = getAllGroup(position);

        ((TextView) view.findViewById(R.id.individualSendName)).setText(individualSend.name);
        ((TextView) view.findViewById(R.id.individualSendNumber)).setText(individualSend.number + "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.individualSend_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(individualSend.box);
        return view;

    }

    IndividualSend getAllGroup(int position) {
        return ((IndividualSend) getItem(position));
    }

    ArrayList<IndividualSend> getBox() {
        ArrayList<IndividualSend> box = new ArrayList<IndividualSend>();
        for (IndividualSend friends : objects) {
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
