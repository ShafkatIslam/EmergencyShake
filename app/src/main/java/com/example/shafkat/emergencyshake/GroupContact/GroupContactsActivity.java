package com.example.shafkat.emergencyshake.GroupContact;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergency;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergencyCustomAdapter;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupContactsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<ShowAllGroup> showAllGroups = new ArrayList<ShowAllGroup>();

    List<ShowAllGroup> list;
    CustomAdapter customAdapter;

    private ListView allGrouplistView1;
    Spinner spinner1;
    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1,myDatabaseHelper2;
    Cursor cursor1,cursor2,cursor4;
    public Button showAllGroupButton;
    ShowAllGroupCustomAdapter showAllGroupCustomAdapter;
    public long rowId2;
    public String groupname;

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    ImageView noData;

    public List<String> showgroupNames = new ArrayList<String>();

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.home);
        setContentView(R.layout.activity_group_contacts);

        allGrouplistView1= (ListView)findViewById(R.id.allGrouplistView1);
        myDatabaseHelper = new MyDatabaseHelper(this);

//        showAllGroupCustomAdapter = new ShowAllGroupCustomAdapter(this,showAllGroups);
//        allGrouplistView1.setAdapter(showAllGroupCustomAdapter);

        customAdapter = new CustomAdapter(GroupContactsActivity.this,R.layout.showallgroupitems);
        allGrouplistView1.setAdapter(customAdapter);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        spinner1 = (Spinner) findViewById(R.id.spinnerId1);

        showAllGroupButton = (Button)findViewById(R.id.showAllGroupButton);

        list = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupContactsActivity.this,SendSmsActivity.class);
                startActivity(intent);
            }
        });

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

        myDatabaseHelper2 = new MyDatabaseHelper(this);

        cursor4 = myDatabaseHelper2.showAllData3();

        if(cursor4.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {

        }
        else
        {
            if(cursor4.moveToFirst())
            {
                do {
                    String name1,number1;
//                    String image;

                    byte[] image1;
                    name1 = cursor4.getString(0);
                    number1 = cursor4.getString(1);
//                    image = cursor.getString(2);

//                    image1 = image.getBytes();
                    image1 = cursor4.getBlob(2);

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
                while(cursor4.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

        myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper1 = new MyDatabaseHelper(this);

        cursor1 = myDatabaseHelper.showAllData4();

        if(cursor1.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {

            showData("Error","No Data Found.\nPlease Create group");
            allGrouplistView1.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            Toast.makeText(GroupContactsActivity.this,"You don't have any group",Toast.LENGTH_SHORT).show();
        }
        else
        {
            noData.setVisibility(View.GONE);
            if(cursor1.moveToFirst())
            {

                do {

                    String name1;
                    name1= cursor1.getString(0);
                    showgroupNames.add(name1);
                }
                //it will check the String from first to last in the cursor
                while(cursor1.moveToNext()); //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata

                }
                Log.d("name>>", String.valueOf(showgroupNames));
            }
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(GroupContactsActivity.this,R.layout.spinnerview,R.id.textViewId,showgroupNames); //creating object of ArrayAdapter....thre are 4 parameters in ArrayAdapter
        spinner1.setAdapter(adapter1);   //setting the adapter in spinner


        showAllGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customAdapter.clear();
                customAdapter.notifyDataSetChanged();

                if(cursor1.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                {

                    Toast.makeText(GroupContactsActivity.this,"You don't have any group",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    groupname = spinner1.getSelectedItem().toString();

                    if(groupname.isEmpty())
                    {
                        Toast.makeText(GroupContactsActivity.this,"No Group Data Available",Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        cursor2 = myDatabaseHelper1.showAllData5(groupname);     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper

                        if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                        {
                            //there is no data so we will diplay message
                            showData("Error","No Data Found");
                            //return;
                        }

                        else
                        {
//                            while(cursor2.moveToNext())
//                            {
//                                String name,number;
//                                name = cursor2.getString(0);
//                                number = cursor2.getString(1);
//
////                    Friends friends = new Friends(name,number);
////                    friendsCustomAdapter.add(friends);
////                    list.add(friends);
//
//                                //showAllGroups.add(new ShowAllGroup(name,number));
//
//                                ShowAllGroup showAllGroup = new ShowAllGroup(name,number);
//                                customAdapter.add(showAllGroup);
//                                list.add(showAllGroup);
//                            }

                            if(cursor2.moveToFirst())
                                {
                                    do {
                                        String name,number;
                                        name = cursor2.getString(0);
                                        number = cursor2.getString(1);

//                                        Friends friends = new Friends(name,number);
//                                        friendsCustomAdapter.add(friends);
//                                        list.add(friends);
//
//                                        showAllGroups.add(new ShowAllGroup(name,number));

                                        ShowAllGroup showAllGroup = new ShowAllGroup(name,number);
                                        customAdapter.add(showAllGroup);
                                        list.add(showAllGroup);

//                                    ShowAllGroup showAllGroup = new ShowAllGroup(name,number);
//                                    showAllGroupCustomAdapter.add()
                                    }
                                    //it will check the String from first to last in the cursor
                                    while(cursor2.moveToNext());  //to check till there is a row after a previous row
                                    {
                                        //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                                    }
                                }



                        }

                    }

                }

            }

        });

        //allGrouplistView1.setAdapter(null);


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
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        Intent intent = new Intent(GroupContactsActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_contacts, menu);
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

            Intent intent = new Intent(GroupContactsActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(GroupContactsActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(GroupContactsActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(GroupContactsActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(GroupContactsActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(GroupContactsActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(GroupContactsActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(GroupContactsActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(GroupContactsActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
