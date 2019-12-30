package com.example.shafkat.emergencyshake.Blood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactProfileActivity;
import com.example.shafkat.emergencyshake.ContactAll.EventActivity;
import com.example.shafkat.emergencyshake.ContactAll.EventClass;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.FileUtil;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BloodProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener  {

    MyDatabaseHelper myDatabaseHelper1,myDatabaseHelper2;
    Cursor cursor1,cursor2,cursor3;

    TextView headerNameTextView,headerNumberTextView,bloodGroupTextView;
    ImageView headerImageView;

    ImageView profileImageView,profileImageView1;
    EditText profileNameEditText,number1EditText,number2EditText,number3EditText,profileEmailEditText;
    Spinner bloodSpinner;
    Button profileSaveButton,profileImageImportButton,profileEditButton,EventButton;
    String[] arrrrrname = {"Select","A+","B+","AB+","O+","A-","B-","AB-","O-","N/A"};

    public File actualImage;
    public File compressedImage;

    final int IMG_REQUEST = 999;

    String name,number1,number2,number3,email,bgroup,bloodgroup;
    long rowId1;

    public int id1;

    String name1,n1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.profile);
        setContentView(R.layout.activity_blood_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileImageView = (ImageView)findViewById(R.id.profileImageView);
        profileImageView1 = (ImageView)findViewById(R.id.profileImageView1);
        profileNameEditText = (EditText)findViewById(R.id.profileNameEditText);
        number1EditText = (EditText)findViewById(R.id.number1EditText);
        number2EditText = (EditText)findViewById(R.id.number2EditText);
        number3EditText = (EditText)findViewById(R.id.number3EditText);
        profileEmailEditText = (EditText)findViewById(R.id.profileEmailEditText);
        bloodSpinner = (Spinner)findViewById(R.id.bloodSpinnerId);
        bloodGroupTextView = (TextView)findViewById(R.id.bloodGroupTextView);

        //bloodGroupTextView.setVisibility(View.GONE);

        profileSaveButton = (Button)findViewById(R.id.profileSaveButton);
        profileImageImportButton = (Button)findViewById(R.id.profileImageImportButton);
        profileEditButton = (Button)findViewById(R.id.profileEditButton);
        EventButton = (Button)findViewById(R.id.EventButton);


//        Bundle bundle=getIntent().getExtras();
//        final String name=bundle.getString("name");
//        final String number=bundle.getString("number");

        EventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodProfileActivity.this,EventActivity.class);
//                intent.putExtra("number",number);
//                EventClass eventClass = EventClass.getInstance();
//                eventClass.setNumber(number);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, arrrrrname);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(arrayAdapter);
        bloodSpinner.setOnItemSelectedListener(this);

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

        EventClass eventClass = EventClass.getInstance();
        id1 = eventClass.getId();

        Log.d("id>>", String.valueOf(id1));
        name1 = eventClass.getName();
        n1 = eventClass.getNumber();

        n1 = n1.replaceAll("[()\\-\\s]", "").trim();

        if(n1.startsWith("+88"))
        {
            n1 = n1.substring(3);
        }
        myDatabaseHelper2 = new MyDatabaseHelper(this);

        cursor2 = myDatabaseHelper2.showAllData6(n1);

        if(cursor2.getCount() == 0)
        {
            profileNameEditText.setText(name1);
            number1EditText.setText(n1);
            profileImageView.setVisibility(View.GONE);
            profileEditButton.setEnabled(false);

            profileSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actualImage == null) {
                        showError("Please choose an image!");
                    }
                    else
                    {
                        name = profileNameEditText.getText().toString();
                        number1 = number1EditText.getText().toString();
                        number2 = number2EditText.getText().toString();
                        number3 = number3EditText.getText().toString();
                        email = profileEmailEditText.getText().toString();
//                        bgroup = bloodSpinner.getSelectedItem().toString();
                        bloodgroup = bloodGroupTextView.getText().toString();

                        if(name.isEmpty())
                        {
                            profileNameEditText.setError("Please Enter Name");
                            profileNameEditText.requestFocus();
                        }
                        else if(number1.isEmpty())
                        {
                            number1EditText.setError("Please Enter Number");
                            number1EditText.requestFocus();
                        }
//                        else if(number2.isEmpty())
//                        {
//                            number2EditText.setError("Please Enter Number");
//                            number2EditText.requestFocus();
//                        }
//                        else if(number3.isEmpty())
//                        {
//                            number3EditText.setError("Please Enter Number");
//                            number3EditText.requestFocus();
//                        }
                        else if(email.isEmpty())
                        {
                            profileEmailEditText.setError("Please Enter Number");
                            profileEmailEditText.requestFocus();
                        }
                        else if(bloodgroup.isEmpty())
                        {
                            Toast.makeText(BloodProfileActivity.this,"Choose Blood Group",Toast.LENGTH_SHORT).show();
                        }
                        else if((number1.length()!=11) || (!(number1.startsWith("018")) && !(number1.startsWith("016")) && !(number1.startsWith("017")) && !(number1.startsWith("015")) && !(number1.startsWith("019"))) )
                        {
                            number1EditText.setError("Invalid Number");
                            number1EditText.requestFocus();
                        }
                        else if(!(number2.isEmpty()))
                        {
                            if((number2.length()!=11) || (!(number2.startsWith("018")) && !(number2.startsWith("016")) && !(number2.startsWith("017")) && !(number2.startsWith("015")) && !(number2.startsWith("019"))))
                            {
                                number2EditText.setError("Invalid Number");
                                number2EditText.requestFocus();
                            }
                        }

                        else if(!(number3.isEmpty()))
                        {
                            if((number3.length()!=11) || (!(number3.startsWith("018")) && !(number3.startsWith("016")) && !(number3.startsWith("017")) && !(number3.startsWith("015")) && !(number3.startsWith("019"))))
                            {
                                number3EditText.setError("Invalid Number");
                                number3EditText.requestFocus();
                            }
                        }

                        else if(bloodgroup.equalsIgnoreCase("select"))
                        {
                            Toast.makeText(BloodProfileActivity.this,"Please select a blood group",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            try{

                                new Compressor(BloodProfileActivity.this)
                                        .compressToFileAsFlowable(actualImage)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<File>() {
                                            @Override
                                            public void accept(File file) {
                                                compressedImage = file;
                                                setCompressedImage();
                                                rowId1 = myDatabaseHelper2.insertData6(id1,name,number1,number2,number3,email,bloodgroup,imageViewToByte(profileImageView)); //method is calling
//                            long rowId1 = myDatabaseHelper.insertData3(name,number,imageToString(bitmap3)); //method is calling

                                                if(rowId1 > -1)
                                                {
                                                    Toast.makeText(BloodProfileActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                }

                                                profileSaveButton.setEnabled(false);
                                                profileImageImportButton.setEnabled(false);
                                                EventButton.setEnabled(false);
                                                profileNameEditText.setEnabled(false);
                                                number1EditText.setEnabled(false);
                                                number2EditText.setEnabled(false);
                                                number3EditText.setEnabled(false);
                                                profileEmailEditText.setEnabled(false);
                                                bloodSpinner.setEnabled(false);
                                                profileImageView.setEnabled(false);
                                                profileImageView1.setEnabled(false);
                                                profileEditButton.setEnabled(true);
                                                bloodGroupTextView.setText(bloodgroup);
                                                bloodGroupTextView.setEnabled(false);
                                                bloodGroupTextView.setVisibility(View.VISIBLE);
                                                bloodSpinner.setVisibility(View.GONE);

                                                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageViewToByte(profileImageView),0,imageViewToByte(profileImageView).length);

////                                                roundedImage = new RoundImage(bitmap1);
//                                                headerNameTextView.setText(name);
//                                                headerNumberTextView.setText(number);
//                                                //                            headerImageView.setImageBitmap(bitmap3);
//                                                headerImageView.setImageBitmap(bitmap1);

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

            profileImageImportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(
                            BloodProfileActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            IMG_REQUEST
                    );
//                    selectImage();
                }
            });
        }

//        profileSaveButton.setEnabled(false);
//        profileImageImportButton.setEnabled(false);
//        EventButton.setEnabled(false);
//        profileNameEditText.setEnabled(false);
//        number1EditText.setEnabled(false);
//        number2EditText.setEnabled(false);
//        number3EditText.setEnabled(false);
//        profileEmailEditText.setEnabled(false);
//        bloodSpinner.setEnabled(false);
//        profileImageView.setEnabled(false);
//        profileImageView1.setEnabled(false);
//
//        String names,numberA,numberB,numberC,emails,bgroups;
////                    String image;
//
//        byte[] image1;
//        names = cursor2.getString(1);
//        numberA = cursor2.getString(2);
//        numberB = cursor2.getString(3);
//        numberC = cursor2.getString(4);
//        emails = cursor2.getString(5);
//        bgroups = cursor2.getString(6);
////                    image = cursor.getString(2);
//
////                    image1 = image.getBytes();
//        image1 = cursor2.getBlob(7);
//
//        Bitmap bitmap = BitmapFactory.decodeByteArray(image1,0,image1.length);
//        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//        //Bitmap bitmap = BitmapFactory.decodeFile(image);
//
////                    roundedImage = new RoundImage(scaled);
//
////                    if(id==id1)
//        profileNameEditText.setText(names);
//        number1EditText.setText(numberA);
//        number2EditText.setText(numberB);
//        number3EditText.setText(numberC);
//        profileEmailEditText.setText(emails);
//        profileImageView.setImageBitmap(scaled);
//
////                    homeImageView.setImageDrawable(roundedImage);
//        profileImageView1.setVisibility(View.GONE);
        else
        {
            profileSaveButton.setEnabled(false);
            profileImageImportButton.setEnabled(false);
            EventButton.setEnabled(false);
            profileNameEditText.setEnabled(false);
            number1EditText.setEnabled(false);
            number2EditText.setEnabled(false);
            number3EditText.setEnabled(false);
            profileEmailEditText.setEnabled(false);
            bloodSpinner.setEnabled(false);
            profileImageView.setEnabled(false);
            profileImageView1.setEnabled(false);
            bloodGroupTextView.setVisibility(View.VISIBLE);
            bloodSpinner.setVisibility(View.GONE);
            bloodGroupTextView.setEnabled(false);
//                    headerImageView.setImageDrawable(roundedImage);
            if(cursor2.moveToFirst())
            {
                do {
                    String names,numberA,numberB,numberC,emails,bgroups;
//                    String image;
                    int id;

                    byte[] image1;
                    id = cursor2.getInt(0);
                    names = cursor2.getString(1);
                    numberA = cursor2.getString(2);
                    numberB = cursor2.getString(3);
                    numberC = cursor2.getString(4);
                    emails = cursor2.getString(5);
                    bgroups = cursor2.getString(6);
//                    image = cursor.getString(2);

//                    image1 = image.getBytes();
                    image1 = cursor2.getBlob(7);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(image1,0,image1.length);
                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    //Bitmap bitmap = BitmapFactory.decodeFile(image);

//                    roundedImage = new RoundImage(scaled);

                    profileNameEditText.setText(names);
                    number1EditText.setText(numberA);
                    number2EditText.setText(numberB);
                    number3EditText.setText(numberC);
                    profileEmailEditText.setText(emails);
                    profileImageView.setImageBitmap(scaled);
                    bloodGroupTextView.setText(bgroups);

//                    homeImageView.setImageDrawable(roundedImage);
                    profileImageView1.setVisibility(View.GONE);

//                    headerImageView.setImageDrawable(roundedImage);
//
//
//
//
                }
//                //it will check the String from first to last in the cursor
                while(cursor2.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }
            }
        }

        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profileSaveButton.setEnabled(true);
                profileEditButton.setEnabled(false);
                profileImageImportButton.setEnabled(true);
                EventButton.setEnabled(true);
                profileNameEditText.setEnabled(true);
                number1EditText.setEnabled(true);
                number2EditText.setEnabled(true);
                number3EditText.setEnabled(true);
                profileEmailEditText.setEnabled(true);
                bloodSpinner.setEnabled(true);
                bloodGroupTextView.setEnabled(true);
                profileImageView.setEnabled(true);
                profileImageView1.setEnabled(true);
//                bloodGroupTextView.setVisibility(View.GONE);
                bloodSpinner.setVisibility(View.GONE);
                bloodGroupTextView.setVisibility(View.VISIBLE);
                profileImageView.setVisibility(View.VISIBLE);

                String numbers = number1EditText.getText().toString();

                cursor3 = myDatabaseHelper2.showAllData6(numbers);

                if(cursor3.moveToFirst())
                {
                    do {
                        String names,numberA,numberB,numberC,emails,bgroups;
//                    String image;
                        int id;

                        byte[] image1;
                        id = cursor3.getInt(0);
                        names = cursor3.getString(1);
                        numberA = cursor3.getString(2);
                        numberB = cursor3.getString(3);
                        numberC = cursor3.getString(4);
                        emails = cursor3.getString(5);
                        bgroups = cursor3.getString(6);
//                    image = cursor.getString(2);
//                    image1 = image.getBytes();
                        image1 = cursor3.getBlob(7);

                        Log.d("names>>",names);

                        Bitmap bitmap = BitmapFactory.decodeByteArray(image1,0,image1.length);
                        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                        //Bitmap bitmap = BitmapFactory.decodeFile(image);

//                    roundedImage = new RoundImage(scaled);

                        profileNameEditText.setText(names);
                        number1EditText.setText(numberA);
                        number2EditText.setText(numberB);
                        number3EditText.setText(numberC);
                        profileEmailEditText.setText(emails);
                        profileImageView.setImageBitmap(scaled);
                        bloodGroupTextView.setText(bgroups);

//                    homeImageView.setImageDrawable(roundedImage);
                        profileImageView1.setVisibility(View.GONE);

//                    headerImageView.setImageDrawable(roundedImage);
//
//
//
//
                    }
//                //it will check the String from first to last in the cursor
                    while(cursor3.moveToNext());  //to check till there is a row after a previous row
                    {
                        //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                    }
                }
                bloodGroupTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bloodSpinner.setVisibility(View.VISIBLE);
                    }
                });



                profileSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (actualImage == null) {
                            showError("Please choose an image!");
                        }
                        else
                        {
                            name = profileNameEditText.getText().toString();
                            number1 = number1EditText.getText().toString();
                            number2 = number2EditText.getText().toString();
                            number3 = number3EditText.getText().toString();
                            email = profileEmailEditText.getText().toString();
//                            bgroup = bloodSpinner.getSelectedItem().toString();
                            bloodgroup = bloodGroupTextView.getText().toString();


                            if(name.isEmpty())
                            {
                                profileNameEditText.setError("Please Enter Name");
                                profileNameEditText.requestFocus();
                            }
                            else if(number1.isEmpty())
                            {
                                number1EditText.setError("Please Enter Number");
                                number1EditText.requestFocus();
                            }
//                        else if(number2.isEmpty())
//                        {
//                            number2EditText.setError("Please Enter Number");
//                            number2EditText.requestFocus();
//                        }
//                        else if(number3.isEmpty())
//                        {
//                            number3EditText.setError("Please Enter Number");
//                            number3EditText.requestFocus();
//                        }
                            else if(email.isEmpty())
                            {
                                profileEmailEditText.setError("Please Enter Number");
                                profileEmailEditText.requestFocus();
                            }
                            else if(bloodgroup.isEmpty())
                            {
                                Toast.makeText(BloodProfileActivity.this,"Choose Blood Group",Toast.LENGTH_SHORT).show();
                            }
                            else if((number1.length()!=11) || (!(number1.startsWith("018")) && !(number1.startsWith("016")) && !(number1.startsWith("017")) && !(number1.startsWith("015")) && !(number1.startsWith("019"))) )
                            {
                                number1EditText.setError("Invalid Number");
                                number1EditText.requestFocus();
                            }
                            else if(!(number2.isEmpty()))
                            {
                                if((number2.length()!=11) || (!(number2.startsWith("018")) && !(number2.startsWith("016")) && !(number2.startsWith("017")) && !(number2.startsWith("015")) && !(number2.startsWith("019"))))
                                {
                                    number2EditText.setError("Invalid Number");
                                    number2EditText.requestFocus();
                                }
                            }

                            else if(!(number3.isEmpty()))
                            {
                                if((number3.length()!=11) || (!(number3.startsWith("018")) && !(number3.startsWith("016")) && !(number3.startsWith("017")) && !(number3.startsWith("015")) && !(number3.startsWith("019"))))
                                {
                                    number3EditText.setError("Invalid Number");
                                    number3EditText.requestFocus();
                                }
                            }

                            else if(bloodgroup.equalsIgnoreCase("select"))
                            {
                                Toast.makeText(BloodProfileActivity.this,"Please select a blood group",Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                try{

                                    new Compressor(BloodProfileActivity.this)
                                            .compressToFileAsFlowable(actualImage)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<File>() {
                                                @Override
                                                public void accept(File file) {
                                                    compressedImage = file;
                                                    setCompressedImage();
                                                    myDatabaseHelper2.updateContactData(id1,name,number1,number2,number3,email,bloodgroup,imageViewToByte(profileImageView)); //method is calling
//                            long rowId1 = myDatabaseHelper.insertData3(name,number,imageToString(bitmap3)); //method is calling

//                                                    if(rowId1 > -1)
                                                    {
                                                        Toast.makeText(BloodProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                    }

                                                    profileSaveButton.setEnabled(false);
                                                    profileImageImportButton.setEnabled(false);
                                                    EventButton.setEnabled(false);
                                                    profileNameEditText.setEnabled(false);
                                                    number1EditText.setEnabled(false);
                                                    number2EditText.setEnabled(false);
                                                    number3EditText.setEnabled(false);
                                                    profileEmailEditText.setEnabled(false);
                                                    bloodSpinner.setEnabled(false);
                                                    profileImageView.setEnabled(false);
                                                    profileImageView1.setEnabled(false);
                                                    profileEditButton.setEnabled(true);
                                                    bloodGroupTextView.setText(bloodgroup);
                                                    bloodGroupTextView.setEnabled(false);
                                                    bloodGroupTextView.setVisibility(View.VISIBLE);
                                                    bloodSpinner.setVisibility(View.GONE);
                                                    profileImageView1.setVisibility(View.GONE);
                                                    profileImageView.setVisibility(View.VISIBLE);

                                                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageViewToByte(profileImageView),0,imageViewToByte(profileImageView).length);

////                                                roundedImage = new RoundImage(bitmap1);
//                                                headerNameTextView.setText(name);
//                                                headerNumberTextView.setText(number);
//                                                //                            headerImageView.setImageBitmap(bitmap3);
//                                                headerImageView.setImageBitmap(bitmap1);

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

                profileImageImportButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        profileImageView.setVisibility(View.GONE);
                        profileImageView1.setVisibility(View.VISIBLE);
                        ActivityCompat.requestPermissions(
                                BloodProfileActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                IMG_REQUEST
                        );
//                    selectImage();
                    }
                });


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
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
                            profileImageView1.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                        }
                        else
                        {
                            Toast.makeText(BloodProfileActivity.this,"Image size is too large",Toast.LENGTH_SHORT).show();
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

    private byte[] imageViewToByte(ImageView profileImageView) {

        byte[] byteArray = null;
        Bitmap bitmap = ((BitmapDrawable)profileImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
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
                Toast.makeText(BloodProfileActivity.this,"You don't have permission to access file location",Toast.LENGTH_SHORT).show();
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setCompressedImage() {

        profileImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
    }

    private void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.blood_profile, menu);
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

            Intent intent = new Intent(BloodProfileActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(BloodProfileActivity.this,EmergencyListActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(BloodProfileActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(BloodProfileActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(BloodProfileActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(BloodProfileActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(BloodProfileActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(BloodProfileActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(BloodProfileActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(BloodProfileActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        bgroup = bloodSpinner.getSelectedItem().toString();
        bloodGroupTextView.setText(bgroup);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
