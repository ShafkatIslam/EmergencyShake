package com.example.shafkat.emergencyshake;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.GroupSendActivity;
import com.example.shafkat.emergencyshake.SendSms.IndividualClass;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1;
    Cursor cursor,cursor1,cursor2;

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    ImageView noData,sendData;

    TextView emergency1;

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.delivery);
        setContentView(R.layout.activity_message);

//        emergency1 = (TextView)findViewById(R.id.emergency1);

        noData = (ImageView)findViewById(R.id.noData);
        sendData = (ImageView)findViewById(R.id.sendData);
        noData.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

//        alertdialogBuilder = new AlertDialog.Builder(this);    //creating object of alertDialogBuilder
//
//        //setting the properties of alertDialogBuilder:
//
//        //for setting title
//        alertdialogBuilder.setTitle("Emergency Shape");
//
//        //for setting message
//        alertdialogBuilder.setMessage("Do you want to exit?");
//
//        //for setting icon
//        alertdialogBuilder.setIcon(R.drawable.exit);
//
//        //for setting cancelable
//        alertdialogBuilder.setCancelable(false);
//
//        //for setting Button
//        alertdialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                //exit
////                finish();
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();;
//                System.exit(0);
//                onDestroy();
//            }
//        });
//        alertdialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                //Not exit
//                // Toast.makeText(MainActivity.this,"You have clicked on no button",Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        });
//        alertdialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                //Not exit
//                //Toast.makeText(MainActivity.this,"You have clicked on cancel button",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //showing alertDialog by creating alertDialog in object and creating alertDialogBuilder in this object
//        AlertDialog alertDialog = alertdialogBuilder.create();
//        alertDialog.show();

        Intent intent = new Intent(MessageActivity.this,HomeActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message, menu);
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

            Intent intent = new Intent(MessageActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(MessageActivity.this,EmergencyListActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.emergenySms) {
            Intent intent = new Intent(MessageActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(MessageActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(MessageActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(MessageActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {
            Intent intent = new Intent(MessageActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(MessageActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(MessageActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(MessageActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        myDatabaseHelper = new MyDatabaseHelper(this);
        ArrayList<String> nums = new ArrayList<String>();
        String sms="";

        cursor = myDatabaseHelper.showAllSms();
        cursor2 = myDatabaseHelper.showAllData2();

        if(cursor.getCount() == 0)
        {

        }

        else
        {
            if(cursor.moveToFirst())
            {
                do{

                    sms = cursor.getString(1);
                }

                while(cursor2.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

        if(cursor2.getCount() == 0)
        {

        }

        else
        {
            if(cursor2.moveToFirst())
            {
                do{
                    String number1="";
                    number1 = cursor2.getString(1);
                    nums.add(number1);
                }

                while(cursor2.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

//        String phone_number = "123"; // some phone number here
//        String text = "SMS text here";
//
//        SmsManager smsMgr = SmsManager.getDefault();
//        smsMgr.sendTextMessage(phone_number, null, text, null, null);

        SmsManager smsManager = SmsManager.getDefault();

//        IndividualClass individualClass = IndividualClass.getInstance();
//        String msg = individualClass.getSms();

        PendingIntent piSend = PendingIntent.getBroadcast(MessageActivity.this, 0,
                new Intent("SMS_SENT"), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(MessageActivity.this, 0,
                new Intent("SMS_DELIVERED"), 0);

        if(nums.isEmpty())
        {
            Toast.makeText(MessageActivity.this,"You don't have any emergency contacts",Toast.LENGTH_SHORT).show();
//            emergency1.setText("You don't have any emergency contacts");
            showData("Error","You don't have any emergency contacts.\nPlease Import Emergency Contacts");
            sendData.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        else if(sms.isEmpty())
        {
            Toast.makeText(MessageActivity.this,"You don't set any emergency sms",Toast.LENGTH_SHORT).show();
//            emergency1.setText("You don't set emergency sms");
            showData("Error","You don't set any emergency sms.\nPlease set Emergency Sms");
            sendData.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        else
        {
            for (int a1 = 0; a1 < nums.size(); a1++) {
                smsManager.sendTextMessage(nums.get(a1).toString(), null, sms,
                        piSend, piDelivered);
            }
            noData.setVisibility(View.GONE);
            sendData.setVisibility(View.VISIBLE);
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
}
