package com.example.shafkat.emergencyshake.Group;

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

public class RenameGroupCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<RenameGroup> objects;

    public RenameGroupCustomAdapter(Context context, ArrayList<RenameGroup> products) {
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
            view = lInflater.inflate(R.layout.rename_list, parent, false);
        }

        RenameGroup renameGroup = getAllGroup(position);

        ((TextView) view.findViewById(R.id.RenameName)).setText(renameGroup.name);
        ((TextView) view.findViewById(R.id.RenameNumber)).setText("Total Contact: "+renameGroup.number+ "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.reaname_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(renameGroup.box);
        return view;

    }

    RenameGroup getAllGroup(int position) {
        return ((RenameGroup) getItem(position));
    }

    ArrayList<RenameGroup> getBox() {
        ArrayList<RenameGroup> box = new ArrayList<RenameGroup>();
        for (RenameGroup friends : objects) {
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
