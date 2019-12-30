package com.example.shafkat.emergencyshake.Blood;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactProfileActivity;
import com.example.shafkat.emergencyshake.ContactAll.EventActivity;
import com.example.shafkat.emergencyshake.ContactAll.EventClass;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergency;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergencyCustomAdapter;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;

public class BloodSearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<BloodSearch> bloodSearches = new ArrayList<BloodSearch>();

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    private ListView bloodListViewId;
    private SearchView bloodSearchViewId;
    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1,myDatabaseHelper2;
    Cursor cursor,cursor1,cursor2;

    BloodSearch bloodSearch;

    ImageView noData;
    BloodSearchCustomAdapter bloodSearchCustomAdapter;
    Button okButton;
    public long rowId2;

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.blood);
        setContentView(R.layout.activity_blood_search);

        bloodListViewId = (ListView)findViewById(R.id.bloodListViewId);
        myDatabaseHelper = new MyDatabaseHelper(this);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);
        
        bloodSearchViewId = (SearchView)findViewById(R.id.bloodSearchViewId);

        bloodSearchCustomAdapter = new BloodSearchCustomAdapter(this,bloodSearches);
        bloodListViewId.setAdapter(bloodSearchCustomAdapter);

        bloodListViewId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bloodSearch = (BloodSearch)bloodSearches.get(position);

                Intent intent = new Intent(BloodSearchActivity.this,BloodProfileActivity.class);

//                intent.putExtra("name",contactModel.getName());
//                intent.putExtra("number",contactModel.getNumber());

                EventClass eventClass = EventClass.getInstance();
                eventClass.setId(position);
                eventClass.setNumber(bloodSearch.getNumber());
                eventClass.setName(bloodSearch.getName());

                startActivity(intent);
            }
        });

        bloodSearchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        

        showData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BloodSearchActivity.this,SendSmsActivity.class);
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

        cursor2 = myDatabaseHelper2.showAllData3();

        if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {

        }
        else
        {
            if(cursor2.moveToFirst())
            {
                do {
                    String name1,number1;
//                    String image;

                    byte[] image1;
                    name1 = cursor2.getString(0);
                    number1 = cursor2.getString(1);
//                    image = cursor.getString(2);

//                    image1 = image.getBytes();
                    image1 = cursor2.getBlob(2);

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
                while(cursor2.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }
    }

    private void getPlanets(String searchTerm) {

        bloodSearches.clear();

        myDatabaseHelper1 = new MyDatabaseHelper(this);

        BloodSearch bloodSearch1 = null;

        cursor1 = myDatabaseHelper1.retrieve(searchTerm);

        if(cursor1.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No "+searchTerm+" Blood Found.");
            //bloodListViewId.setVisibility(View.GONE);
            //noData.setVisibility(View.VISIBLE);
            //return;
            showData();
        }

        else
        {
            while (cursor1.moveToNext())
            {
                String name,number,blood;
                name = cursor1.getString(1);
                number = cursor1.getString(2);
                blood = cursor1.getString(6);

//                bloodSearch1 = new BloodSearch();
//
//                bloodSearch1.setName(name);
//                bloodSearch1.setNumber(number);
//                bloodSearch1.setBlood(blood);
//
//                bloodSearches.add(bloodSearch1);

                bloodSearches.add(new BloodSearch(name,number,blood));
            }

            bloodListViewId.setAdapter(bloodSearchCustomAdapter);

        }

    }

    private void showData() {

        cursor = myDatabaseHelper.showAllDataBlood();     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper


        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Import Contacts Information");
            bloodListViewId.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            //return;
        }
        else
        {
            noData.setVisibility(View.GONE);
            if(cursor.moveToFirst())
            {
                do {
                    String name,number,blood;
                    name = cursor.getString(1);
                    number = cursor.getString(2);
                    blood = cursor.getString(6);

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                    bloodSearches.add(new BloodSearch(name,number,blood));

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
        getMenuInflater().inflate(R.menu.blood_search, menu);
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

            Intent intent = new Intent(BloodSearchActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(BloodSearchActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(BloodSearchActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(BloodSearchActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(BloodSearchActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(BloodSearchActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(BloodSearchActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(BloodSearchActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {



        }else if (id == R.id.settings) {
            Intent intent = new Intent(BloodSearchActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
