package com.example.shafkat.emergencyshake.HomeActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.util.AttributeSet;
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
import android.widget.EditText;
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
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;
import com.example.shafkat.emergencyshake.sensor.SensorService;
import com.example.shafkat.emergencyshake.unlock.ShakeDetectionService;
import com.example.shafkat.emergencyshake.unlock.UnlockDeviceAdmin;
import com.karan.churi.PermissionManager.PermissionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PermissionManager permissionManager;

    Intent mServiceIntent;
    private SensorService mSensorService;

    public Context getCtx() {
        return ctx;
    }

    Context ctx;
    static final int RESULT_ENABLE = 1;
    private DevicePolicyManager deviceManger;
    private ComponentName compName;
    private CoordinatorLayout root;

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    private FloatingActionButton fab2;

    final int IMG_REQUEST = 999;
    //private Bitmap bitmap3;

    EditText homeNameEditText,homeNumberEditText;
    Button homeSaveButton,homeImageImportButton,homeEditButton;
    ImageView homeImageView,headerImageView,homeImageView1;
    TextView headerNameTextView,headerNumberTextView;

    MyDatabaseHelper myDatabaseHelper;
    Cursor cursor;

    public File actualImage;
    public File compressedImage;

    String name,number;
    long rowId1;
    RoundImage roundedImage;

//    public ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deviceManger = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, UnlockDeviceAdmin.class);

        ctx = this;

        setTitle(R.string.home);
        setContentView(R.layout.activity_home);

//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#81a3d0")));

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

        root = (CoordinatorLayout) findViewById(R.id.root1);

        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startService(new Intent(this, ShakeDetectionService.class));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(HomeActivity.this,SendSmsActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
//        navMenuView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.VERTICAL));
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        headerNameTextView = (TextView)header.findViewById(R.id.headerNameTextView);
        headerNumberTextView = (TextView)header.findViewById(R.id.headerNumberTextView);
        headerImageView = (ImageView)header.findViewById(R.id.headerImageView);


        myDatabaseHelper = new MyDatabaseHelper(this);

        homeNameEditText = (EditText)findViewById(R.id.homeNameEditText);
        homeNumberEditText = (EditText)findViewById(R.id.homeNumberEditText);
        homeImageView = (ImageView)findViewById(R.id.homeImageView);
        homeImageView1 = (ImageView)findViewById(R.id.homeImageView1);

        homeSaveButton = (Button)findViewById(R.id.homeSaveButton);
        homeImageImportButton = (Button)findViewById(R.id.homeImageImportButton);
        homeEditButton = (Button)findViewById(R.id.homeEditButton);

        cursor = myDatabaseHelper.showAllData3();

        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            homeImageView.setVisibility(View.GONE);
            homeEditButton.setEnabled(false);
            homeSaveButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {

                    if (actualImage == null) {
                        showError("Please choose an image!");
                    }
                    else
                    {
                        name = homeNameEditText.getText().toString();
                        number = homeNumberEditText.getText().toString();

                        if(name.isEmpty())
                        {
                            homeNameEditText.setError("Please Enter Name");
                            homeNameEditText.requestFocus();
                        }

                        else if(number.isEmpty())
                        {
                            homeNumberEditText.setError("Please Enter Number");
                            homeNumberEditText.requestFocus();
                        }

                        else if((number.length()!=11) || (!(number.startsWith("018")) && !(number.startsWith("016")) && !(number.startsWith("017")) && !(number.startsWith("015")) && !(number.startsWith("019"))) )
                        {
                            homeNumberEditText.setError("Invalid Number");
                            homeNumberEditText.requestFocus();
                        }

//                        else if(homeImageView1.getDrawable().getConstantState()== getResources().getDrawable(R.drawable.profilepic).getConstantState())
//                        {
//                            Toast.makeText(HomeActivity.this,"Please select your picture",Toast.LENGTH_SHORT).show();
//                        }

                        else
                        {
                            try{

                                new Compressor(HomeActivity.this)
                                        .compressToFileAsFlowable(actualImage)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<File>() {
                                            @Override
                                            public void accept(File file) {
                                                compressedImage = file;
                                                setCompressedImage();
                                                Log.d("name>>", name + "  " + number);
                                                rowId1 = myDatabaseHelper.insertData3(name,number,imageViewToByte(homeImageView)); //method is calling
//                            long rowId1 = myDatabaseHelper.insertData3(name,number,imageToString(bitmap3)); //method is calling

                                                if(rowId1 > -1)
                                                {
                                                    Toast.makeText(HomeActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                    Log.d("response>>", name + "  " + number);
                                                }

                                                homeSaveButton.setEnabled(false);
                                                homeImageImportButton.setEnabled(false);
                                                homeNameEditText.setEnabled(false);
                                                homeNumberEditText.setEnabled(false);
                                                homeImageView.setEnabled(false);
                                                homeImageView1.setEnabled(false);
                                                homeEditButton.setEnabled(true);

                                                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageViewToByte(homeImageView),0,imageViewToByte(homeImageView).length);

//                                                roundedImage = new RoundImage(bitmap1);
                                                headerNameTextView.setText(name);
                                                headerNumberTextView.setText(number);
                  //                            headerImageView.setImageBitmap(bitmap3);
                                                headerImageView.setImageBitmap(bitmap1);

//                                                headerImageView.setImageDrawable(roundedImage);
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) {
                                                throwable.printStackTrace();
                                                showError(throwable.getMessage());
                                            }
                                        });
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }

                }

            });

            homeImageImportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(
                            HomeActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            IMG_REQUEST
                    );
//                    selectImage();
                }
            });
        }

        else
        {
            homeSaveButton.setEnabled(false);
            homeImageImportButton.setEnabled(false);
            homeNameEditText.setEnabled(false);
            homeNumberEditText.setEnabled(false);
            homeImageView.setEnabled(false);
            homeImageView1.setEnabled(false);

            if(cursor.moveToFirst())
            {
                do {
                    String name1,number1;
//                    String image;

                    byte[] image1;
                    name1 = cursor.getString(0);
                    number1 = cursor.getString(1);
//                    image = cursor.getString(2);

//                    image1 = image.getBytes();
                    image1 = cursor.getBlob(2);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(image1,0,image1.length);
                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    //Bitmap bitmap = BitmapFactory.decodeFile(image);

//                    roundedImage = new RoundImage(scaled);

                    homeNameEditText.setText(name1);
                    homeNumberEditText.setText(number1);
                    homeImageView.setImageBitmap(scaled);

//                    homeImageView.setImageDrawable(roundedImage);
                    homeImageView1.setVisibility(View.GONE);

                    headerNameTextView.setText(name1);
                    headerNumberTextView.setText(number1);
                    headerImageView.setImageBitmap(scaled);

//                    headerImageView.setImageDrawable(roundedImage);


                }
                //it will check the String from first to last in the cursor
                while(cursor.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

        homeEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeSaveButton.setEnabled(true);
                homeImageImportButton.setEnabled(true);
                homeNameEditText.setEnabled(true);
                homeNumberEditText.setEnabled(true);
                homeImageView.setEnabled(true);
                homeImageView1.setEnabled(true);
                homeEditButton.setEnabled(false);


                rowId1 = myDatabaseHelper.deleteData3();

                cursor = myDatabaseHelper.showAllData3();

                if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                {
                    homeImageView1.setEnabled(true);
                    homeEditButton.setEnabled(false);
                    homeSaveButton.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(View v) {

                            if (actualImage == null) {
                                showError("Please choose an image!");
                            }
                            else
                            {
                                name = homeNameEditText.getText().toString();
                                number = homeNumberEditText.getText().toString();

                                if(name.isEmpty())
                                {
                                    homeNameEditText.setError("Please Enter Name");
                                    homeNameEditText.requestFocus();
                                }

                                else if(number.isEmpty())
                                {
                                    homeNumberEditText.setError("Please Enter Number");
                                    homeNumberEditText.requestFocus();
                                }

                                else if((number.length()!=11) || (!(number.startsWith("018")) && !(number.startsWith("016")) && !(number.startsWith("017")) && !(number.startsWith("015")) && !(number.startsWith("019"))) )
                                {
                                    homeNumberEditText.setError("Invalid Number");
                                    homeNumberEditText.requestFocus();
                                }

//                        else if(homeImageView1.getDrawable().getConstantState()== getResources().getDrawable(R.drawable.profilepic).getConstantState())
//                        {
//                            Toast.makeText(HomeActivity.this,"Please select your picture",Toast.LENGTH_SHORT).show();
//                        }

                                else
                                {
                                    try{

                                        new Compressor(HomeActivity.this)
                                                .compressToFileAsFlowable(actualImage)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Consumer<File>() {
                                                    @Override
                                                    public void accept(File file) {
                                                        compressedImage = file;
                                                        homeImageView1.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                                                        setCompressedImage();
                                                        Log.d("name>>", name + "  " + number);
                                                        rowId1 = myDatabaseHelper.insertData3(name,number,imageViewToByte(homeImageView)); //method is calling
//                            long rowId1 = myDatabaseHelper.insertData3(name,number,imageToString(bitmap3)); //method is calling

                                                        if(rowId1 > -1)
                                                        {
                                                            Toast.makeText(HomeActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                            Log.d("response>>", name + "  " + number);
                                                        }

                                                        homeSaveButton.setEnabled(false);
                                                        homeImageImportButton.setEnabled(false);
                                                        homeNameEditText.setEnabled(false);
                                                        homeNumberEditText.setEnabled(false);
                                                        homeImageView.setEnabled(false);
                                                        homeImageView1.setEnabled(false);
                                                        homeEditButton.setEnabled(true);

                                                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageViewToByte(homeImageView),0,imageViewToByte(homeImageView).length);
//                                                        roundedImage = new RoundImage(bitmap1);

                                                        headerNameTextView.setText(name);
                                                        headerNumberTextView.setText(number);
                                                        //                            headerImageView.setImageBitmap(bitmap3);
                                                        headerImageView.setImageBitmap(bitmap1);
//                                                        headerImageView.setImageDrawable(roundedImage);
                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) {
                                                        throwable.printStackTrace();
                                                        showError(throwable.getMessage());
                                                    }
                                                });
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                            }

                        }

                    });

                    homeImageImportButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeImageView.setVisibility(View.GONE);
                            homeImageView1.setVisibility(View.VISIBLE);
                            ActivityCompat.requestPermissions(
                                    HomeActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    IMG_REQUEST
                            );
//                    selectImage();
                        }
                    });
                }
            }
        });



        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deviceManger.isAdminActive(compName)) {
                    deviceManger.lockNow();
                } else {
                    Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setCompressedImage() {

        homeImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
        headerImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));

//        roundedImage = new RoundImage(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
//        homeImageView.setImageDrawable(roundedImage);
//        headerImageView.setImageDrawable(roundedImage);
    }

    private void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    private String imageToString(Bitmap bitmap3) {
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//        byte[] imgBytes = byteArrayOutputStream.toByteArray();
//        return android.util.Base64.encodeToString(imgBytes, android.util.Base64.DEFAULT);
//    }
//
//    private void selectImage() {
//
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,IMG_REQUEST);
//    }

    private byte[] imageViewToByte(ImageView image) {

        byte[] byteArray = null;
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;

//        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
//        Canvas canvas = new Canvas(newBitmap);
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(bitmap, 0, 0, null);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        newBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
//        byteArray = outputStream.toByteArray();
//        return byteArray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == IMG_REQUEST)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
            else
            {
                Toast.makeText(HomeActivity.this,"You don't have permission to access file location",Toast.LENGTH_SHORT).show();
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            {
                if (resultCode == Activity.RESULT_OK) {
                    Snackbar.make(root, "Admin enabled", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                }

                else {

                    Snackbar.make(root, "Admin enable FAILED!", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                }
            }

            case IMG_REQUEST:
            {
                if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null)
                {
                    //Uri path = data.getData();

                    try {
                        actualImage = FileUtil.from(this, data.getData());
                        //homeImageView.setVisibility(View.GONE);
//                        homeImageView1.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));

//                        String s;
//                        s = String.format("Size : %s", getReadableFileSize(actualImage.length()));
//                        Log.d("name>>", s);

                        long size = actualImage.length();
                        Log.d("name>>", String.valueOf(size));
//                        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
//                        double x;
//                        x = size/Math.pow(1024, digitGroups);

                        if(size<5000000)
                        {
                            homeImageView1.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                            headerImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this,"Image size is too large",Toast.LENGTH_SHORT).show();
                        }

//                        roundedImage = new RoundImage(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
//                        homeImageView1.setImageDrawable(roundedImage);


                        //homeImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
//                        InputStream inputStream = getContentResolver().openInputStream(path);
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        homeImageView.setImageBitmap(bitmap);

//                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
//                        homeImageView.setImageBitmap(bitmap3);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
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

        alertdialogBuilder = new AlertDialog.Builder(this);    //creating object of alertDialogBuilder

        //setting the properties of alertDialogBuilder:

        //for setting title
        alertdialogBuilder.setTitle("Emergency Shape");

        //for setting message
        alertdialogBuilder.setMessage("Do you want to exit?");

        //for setting icon
        alertdialogBuilder.setIcon(R.drawable.exit);

        //for setting cancelable
        alertdialogBuilder.setCancelable(false);

        //for setting Button
        alertdialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //exit
//                finish();;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                finish();;
//                System.exit(0);
//                onDestroy();
            }
        });
        alertdialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Not exit
                // Toast.makeText(MainActivity.this,"You have clicked on no button",Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertdialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Not exit
                //Toast.makeText(MainActivity.this,"You have clicked on cancel button",Toast.LENGTH_SHORT).show();
            }
        });

        //showing alertDialog by creating alertDialog in object and creating alertDialogBuilder in this object
        AlertDialog alertDialog = alertdialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

//            Intent intent = new Intent(MainActivity.this,MainActivity.class);
//            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(HomeActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(HomeActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(HomeActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {

            Intent intent = new Intent(HomeActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(HomeActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(HomeActivity.this,SendSmsActivity.class);
            startActivity(intent);


        }else if (id == R.id.reminder) {

            Intent intent = new Intent(HomeActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(HomeActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}
