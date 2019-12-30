package com.example.shafkat.emergencyshake.Reminder;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.ContactAll.EventActivity;
import com.example.shafkat.emergencyshake.ContactAll.EventDate;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.Emergency;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ListView ReminderlistView;
    ReminderCustomAdapter reminderCustomAdapter;
    ArrayList<Reminder> reminders = new ArrayList<Reminder>();

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    private MyDatabaseHelper db,myDatabaseHelper1,myDatabaseHelper2;
    Cursor cursor,cursor1,cursor2;

    ImageView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.reminder_list);
        setContentView(R.layout.activity_reminder);

        ReminderlistView = (ListView)findViewById(R.id.ReminderlistView);
        reminderCustomAdapter = new ReminderCustomAdapter(this,reminders);
        ReminderlistView.setAdapter(reminderCustomAdapter);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        db = new MyDatabaseHelper(this);

        loadData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReminderActivity.this,SendSmsActivity.class);
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

    }

    private void loadData() {

        cursor = db.reminderEvent();

        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Set Reminder");
            ReminderlistView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
            //return;
        }

        else
        {
            noData.setVisibility(View.GONE);
            if(cursor.moveToFirst())
            {
                do {
                    String note,time,name;
                    note = cursor.getString(1);
                    time = cursor.getString(2);
                    name = cursor.getString(4);

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                    reminders.add(new Reminder(note,time,name));
//                    allGroups.add(new AllGroup(name,number,false));

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

        MenuInflater menuInflater = getMenuInflater();   //"MenuInflater" is a service which coverts xml file into java file
        menuInflater.inflate(R.menu.menu_layout,menu);    //"Inflate" method turns the xml file into java file and pass the menu class object

        MenuItem menuItem = menu.findItem(R.id.searchViewId);        //Find MenuItem which is a searchView. Because searchView is in the menuItem
        SearchView searchView = (SearchView) menuItem.getActionView();   //The menuItem which we get that is kept in the searchView variable  and in the xml file we set the searchView as a menuItem using actionViewClass .So we have to get the ActionView by the menuItem

        searchView.setQueryHint("Search an Event Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getPlanets(newText);
                return false;
            }
        });

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reminder, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        reminders.clear();

        myDatabaseHelper2 = new MyDatabaseHelper(this);

        Reminder reminder = null;

        cursor2 = myDatabaseHelper2.retrieve3(searchTerm);

        if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error",searchTerm+" is not Found.");
            //bloodListViewId.setVisibility(View.GONE);
            //noData.setVisibility(View.VISIBLE);
            //return;
            loadData();

        }

        else
        {
            while (cursor2.moveToNext())
            {
                String name,date,contactName;
                name = cursor2.getString(1);
                date = cursor2.getString(2);
                contactName = cursor2.getString(4);

//                bloodSearch1 = new BloodSearch();
//
//                bloodSearch1.setName(name);
//                bloodSearch1.setNumber(number);
//                bloodSearch1.setBlood(blood);
//
//                bloodSearches.add(bloodSearch1);

                reminders.add(new Reminder(name,date,contactName));
            }

            ReminderlistView.setAdapter(reminderCustomAdapter);

        }
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

            Intent intent = new Intent(ReminderActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(ReminderActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(ReminderActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(ReminderActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {

            Intent intent = new Intent(ReminderActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(ReminderActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(ReminderActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {



        }else if (id == R.id.blood) {

            Intent intent = new Intent(ReminderActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(ReminderActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
