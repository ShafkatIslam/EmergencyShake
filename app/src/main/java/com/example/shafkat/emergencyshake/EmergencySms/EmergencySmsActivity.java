package com.example.shafkat.emergencyshake.EmergencySms;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.Group.RenameGroupActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.MessageActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

public class EmergencySmsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText sendEmergencySmsEditText;
    Button emergencySmsSave,emergencySmsEdit;
    String sms;

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1;

    Cursor cursor,cursor1;

    public long rowId,rowId1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.emergency_sms);
        setContentView(R.layout.activity_emergency_sms);

        sendEmergencySmsEditText = (EditText)findViewById(R.id.sendEmergencySmsEditText);
        emergencySmsSave = (Button)findViewById(R.id.emergencySmsSave);
        emergencySmsEdit = (Button)findViewById(R.id.emergencySmsEdit);

        emergencySmsEdit.setEnabled(false);

        myDatabaseHelper = new MyDatabaseHelper(this);

        cursor =myDatabaseHelper.showAllSms();

        emergencySmsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sms = sendEmergencySmsEditText.getText().toString();

                if(sms.isEmpty())
                {
                    sendEmergencySmsEditText.setError("Please Write Something");
                    sendEmergencySmsEditText.requestFocus();
                }

                else
                {

                    if(cursor.getCount()==0)
                    {
                        rowId = myDatabaseHelper.insertSms(sms);

                        emergencySmsSave.setEnabled(false);
                        sendEmergencySmsEditText.setEnabled(false);
                        emergencySmsEdit.setEnabled(true);

                        if(rowId>0)
                        {
                            Toast.makeText(EmergencySmsActivity.this, "Sms is saved successfully", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(EmergencySmsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        emergencySmsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emergencySmsSave.setEnabled(true);
                sendEmergencySmsEditText.setEnabled(true);
                emergencySmsEdit.setEnabled(false);

                rowId1 = myDatabaseHelper.deleteSms();

                cursor =myDatabaseHelper.showAllSms();

                if(cursor.getCount()==0)
                {
                    emergencySmsSave.setEnabled(true);
                    sendEmergencySmsEditText.setEnabled(true);
                    emergencySmsEdit.setEnabled(false);

                    emergencySmsSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sms = sendEmergencySmsEditText.getText().toString();

                            if(sms.isEmpty())
                            {
                                sendEmergencySmsEditText.setError("Please Write Something");
                                sendEmergencySmsEditText.requestFocus();
                            }

                            else
                            {
                                rowId = myDatabaseHelper.insertSms(sms);

                                emergencySmsSave.setEnabled(false);
                                sendEmergencySmsEditText.setEnabled(false);
                                emergencySmsEdit.setEnabled(true);

                                if(rowId>0)
                                {
                                    Toast.makeText(EmergencySmsActivity.this, "Sms is saved successfully", Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    Toast.makeText(EmergencySmsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

            }
        });

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

    private void loadData() {

        cursor =myDatabaseHelper.showAllSms();

        if(cursor.getCount()==0)
        {
            emergencySmsSave.setEnabled(true);
            sendEmergencySmsEditText.setEnabled(true);
            emergencySmsEdit.setEnabled(false);
        }

        else
        {
            emergencySmsSave.setEnabled(false);
            sendEmergencySmsEditText.setEnabled(false);
            emergencySmsEdit.setEnabled(true);

            if(cursor.moveToFirst())
            {
                do{

                    String sms;
                    sms = cursor.getString(1);

                    sendEmergencySmsEditText.setText(sms);

                }
                while(cursor.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

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
        getMenuInflater().inflate(R.menu.emergency_sms, menu);
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

            Intent intent = new Intent(EmergencySmsActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(EmergencySmsActivity.this,EmergencyListActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.emergenySms) {

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(EmergencySmsActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(EmergencySmsActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(EmergencySmsActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {
            Intent intent = new Intent(EmergencySmsActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(EmergencySmsActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(EmergencySmsActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(EmergencySmsActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
