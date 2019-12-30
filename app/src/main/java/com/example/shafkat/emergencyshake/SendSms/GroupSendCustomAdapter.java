package com.example.shafkat.emergencyshake.SendSms;

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

import java.util.ArrayList;

public class GroupSendCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<GroupSend> objects;

    public GroupSendCustomAdapter(Context context, ArrayList<GroupSend> products) {
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
            view = lInflater.inflate(R.layout.groupsendlist, parent, false);
        }

        GroupSend groupSend = getAllGroup(position);

        ((TextView) view.findViewById(R.id.groupSendName)).setText(groupSend.name);
        ((TextView) view.findViewById(R.id.groupSendNumber)).setText("Total Contact: "+groupSend.number+ "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.groupSend_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(groupSend.box);
        return view;

    }

    GroupSend getAllGroup(int position) {
        return ((GroupSend) getItem(position));
    }

    ArrayList<GroupSend> getBox() {
        ArrayList<GroupSend> box = new ArrayList<GroupSend>();
        for (GroupSend friends : objects) {
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
