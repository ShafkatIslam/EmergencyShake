package com.example.shafkat.emergencyshake.SendSms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.Group.RenameGroup;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.GroupContact.ShowAllGroup;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.MessageActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;

import java.util.ArrayList;

public class GroupSendActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<GroupSend> groupSends = new ArrayList<GroupSend>();

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    private ListView groupSendListView;
    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1;
    Cursor cursor,cursor1,cursor2,cursor3;

    ImageView noData;

    Button groupSendSendButton;

    GroupSendCustomAdapter groupSendCustomAdapter;

    BroadcastReceiver smsSentReciver, smsSentDelivery;

    public long rowId;

    public int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.group_sms);
        setContentView(R.layout.activity_group_send);

        groupSendListView = (ListView)findViewById(R.id.groupSendListView);
        myDatabaseHelper = new MyDatabaseHelper(this);

        groupSendCustomAdapter = new GroupSendCustomAdapter(this,groupSends);
        //groupSendListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        groupSendListView.setAdapter(groupSendCustomAdapter);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        groupSendSendButton = (Button)findViewById(R.id.groupSendSendButton);

        groupSendSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = "Selected Product are :";
                String name ="";
                String number ="";
                ArrayList<String> nums = new ArrayList<String>();

                for (GroupSend p : groupSendCustomAdapter.getBox())
                {
                    if(!(p.box))
                    {
                        Toast.makeText(GroupSendActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                    }

                    else
                    {

                        if (p.box){
                            result += "\n" + p.name;
                            name = p.name;
                            {
//                                cellArray = number.toString().split("");
                                cursor3 = myDatabaseHelper.showAllData5(name);     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper


                                if(cursor3.moveToFirst())
                                {
                                    do {
                                        String number1="";
                                        //name = cursor2.getString(0);
                                        number1 = cursor3.getString(1);

                                        nums.add(number1);

//                                        Friends friends = new Friends(name,number);
//                                        friendsCustomAdapter.add(friends);
//                                        list.add(friends);
//
//                                        showAllGroups.add(new ShowAllGroup(name,number));


//                                    ShowAllGroup showAllGroup = new ShowAllGroup(name,number);
//                                    showAllGroupCustomAdapter.add()
                                    }
                                    //it will check the String from first to last in the cursor
                                    while(cursor3.moveToNext());  //to check till there is a row after a previous row
                                    {
                                        //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                                    }
                                }

                            }

                        }

                        Intent intent = new Intent(GroupSendActivity.this,SuccessfulSmsActivity.class);
                        startActivity(intent);
                    }
                }

                SmsManager smsManager = SmsManager.getDefault();

                IndividualClass individualClass = IndividualClass.getInstance();
                String msg = individualClass.getSms();

                PendingIntent piSend = PendingIntent.getBroadcast(GroupSendActivity.this, 0,
                        new Intent("SMS_SENT"), 0);
                PendingIntent piDelivered = PendingIntent.getBroadcast(GroupSendActivity.this, 0,
                        new Intent("SMS_DELIVERED"), 0);

                for (int a1 = 0; a1 < nums.size(); a1++) {
                    smsManager.sendTextMessage(nums.get(a1).toString(), null, msg,
                            piSend, piDelivered);
                }


            }
        });

        count = 0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        headerNameTextView = (TextView)header.findViewById(R.id.headerNameTextView);
        headerNumberTextView = (TextView)header.findViewById(R.id.headerNumberTextView);
        headerImageView = (ImageView)header.findViewById(R.id.headerImageView);

        myDatabaseHelper1 = new MyDatabaseHelper(this);

        cursor1 = myDatabaseHelper1.showAllData3();

        if(cursor1.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {

        }
        else
        {
            if(cursor1.moveToFirst())
            {
                do {
                    String name1,number1;
//                    String image;

                    byte[] image1;
                    name1 = cursor1.getString(0);
                    number1 = cursor1.getString(1);
//                    image = cursor.getString(2);

//                    image1 = image.getBytes();
                    image1 = cursor1.getBlob(2);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(image1,0,image1.length);
                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    //Bitmap bitmap = BitmapFactory.decodeFile(image);


                    headerNameTextView.setText(name1);
                    headerNumberTextView.setText(number1);
                    headerImageView.setImageBitmap(scaled);

//                    headerImageView.setImageDrawable(roundedImage);


                }
                //it will check the String from first to last in the cursor
                while(cursor1.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

        loadData();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(smsSentReciver);
        unregisterReceiver(smsSentDelivery);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        smsSentReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "sms has been sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic Fail",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No Service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio Off",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
            }

        };
        smsSentDelivery = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Sms Delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Sms not Delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        };
        registerReceiver(smsSentReciver, new IntentFilter("SMS_SENT"));
        registerReceiver(smsSentDelivery, new IntentFilter("SMS_DELIVERED"));

    }

    private void loadData() {

        cursor = myDatabaseHelper.showAllData4();     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper

        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Create Group Contacts");
            groupSendListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
            //return;
            groupSendSendButton.setEnabled(false);
        }

        else
        {
            noData.setVisibility(View.GONE);
            groupSendSendButton.setEnabled(true);

            if(cursor.moveToFirst())
            {
                do {
                    String name;
                    int number;
                    name = cursor.getString(0);


                    cursor2 = myDatabaseHelper.showAllData5(name);

                    if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                    {
                        //there is no data so we will diplay message
//                        showData("Error","No Data Found");
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
                        //return;
                        groupSends.add(new GroupSend(name,0,false));
                    }
                    else
                    {
                        if(cursor2.moveToFirst())
                        {
                            do{
                                count++;
                                number = count;
                            }
                            while(cursor2.moveToNext());  //to check till there is a row after a previous row
                            {
                                count=0;
                                //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                            }

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                            groupSends.add(new GroupSend(name,number,false));
                        }
                    }


                }
                //it will check the String from first to last in the cursor
                while(cursor.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }

            }
        }


    }

    private void showData(String title, String message)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            Intent intent = new Intent(GroupSendActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(GroupSendActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(GroupSendActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(GroupSendActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {

            Intent intent = new Intent(GroupSendActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(GroupSendActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(GroupSendActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(GroupSendActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(GroupSendActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(GroupSendActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
