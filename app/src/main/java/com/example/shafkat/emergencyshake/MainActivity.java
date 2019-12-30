package com.example.shafkat.emergencyshake;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;
import com.example.shafkat.emergencyshake.helper.PreferenceHelper;
import com.example.shafkat.emergencyshake.sensor.SensorService;
import com.example.shafkat.emergencyshake.unlock.ShakeDetectionService;
import com.example.shafkat.emergencyshake.unlock.UnlockDeviceAdmin;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    PermissionManager permissionManager;

    Intent mServiceIntent;
    private SensorService mSensorService;

    public Context getCtx() {
        return ctx;
    }

    MyDatabaseHelper myDatabaseHelper1;
    Cursor cursor1;

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    static final int RESULT_ENABLE = 1;
    private DevicePolicyManager deviceManger;
    private ActivityManager activityManager;
    private ComponentName compName;
    private SwitchCompat switchCompat;
    private FloatingActionButton fab1;
    private SeekBar proress;
    private CoordinatorLayout root;
    private TextView sensitivity;

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    Context ctx;

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deviceManger = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, UnlockDeviceAdmin.class);

        ctx = this;

        setTitle(R.string.settings);
        setContentView(R.layout.activity_main);


        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        switchCompat = (SwitchCompat) findViewById(R.id.tc_switch);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        proress = (SeekBar) findViewById(R.id.seekBar);
        root = (CoordinatorLayout) findViewById(R.id.root);
        sensitivity = (TextView) findViewById(R.id.sensitiy_value);

        proress.setProgress(PreferenceHelper.getPrefernceHelperInstace().getInteger(getApplicationContext(), "SENSITIVITY", 13));

        sensitivity.setText(String.valueOf(proress.getProgress()));


        switchCompat.setChecked(deviceManger.isAdminActive(compName));
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    Intent intent = new Intent(DevicePolicyManager
                            .ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                            compName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "Additional text explaining why this needs to be added.");
                    startActivityForResult(intent, RESULT_ENABLE);

                } else {

                    deviceManger.removeActiveAdmin(compName);

                }
            }
        });

        proress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                sensitivity.setText("" + i);
                PreferenceHelper.getPrefernceHelperInstace().setInteger(getApplicationContext(), "SENSITIVITY", 13);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startService(new Intent(this, ShakeDetectionService.class));


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deviceManger.isAdminActive(compName)) {
                    deviceManger.lockNow();
                } else {
                    Snackbar.make(view, "Enable Device Admin first", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                }
            }
        });



        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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


        toggle = new ActionBarDrawerToggle(
                this, drawer,null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);

        ArrayList<String> granted =permissionManager.getStatus().get(0).granted;
        ArrayList<String> denied =permissionManager.getStatus().get(0).denied;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {

                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Snackbar.make(root, "Admin enabled", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                } else {

                    Snackbar.make(root, "Admin enable FAILED!", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
////                finish();;
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

        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(toggle.onOptionsItemSelected(item))
        {
            return  true;
        }

        else if (id == R.id.action_settings) {
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

            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(MainActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(MainActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(MainActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(MainActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(MainActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(MainActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(MainActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(MainActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
