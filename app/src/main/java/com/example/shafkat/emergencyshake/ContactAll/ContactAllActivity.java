package com.example.shafkat.emergencyshake.ContactAll;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearch;
import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.Database.Contact;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;

public class ContactAllActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;
    Button save,update;
    public long rowId;
    public ArrayList<String> list = new ArrayList<String>();;
    public ArrayList<String> list1 = new ArrayList<String>();;

    ContactModel contactModel;

    public String name,phoneNumber;

    public boolean result;
    public boolean answer;

    public int count;
    public int count1;

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1,myDatabaseHelper2;
    Contact contact;
    Cursor cursor1,cursor2;

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.contact_list);
        setContentView(R.layout.activity_contact_all);
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


        listView = (ListView) findViewById(R.id.listView);
        save= (Button)findViewById(R.id.saveButton);
        update= (Button)findViewById(R.id.updateButton);


        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase(); //to get the implements of MyDatabaseHelper we have to use the getWriteableDatabase() method and it will return an object of SQLiteDatabase
        SharedPreferences saveFavPrefs = getSharedPreferences("com.example.shafkat.emergencyshake.ContactAll", MODE_PRIVATE);
        save.setEnabled(saveFavPrefs.getBoolean("isChecked", true));

        if(save.isEnabled()==true)
        {
            update.setEnabled(false);
        }
        else
        {
            update.setEnabled(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(save.isPressed())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.shafkat.emergencyshake.ContactAll", MODE_PRIVATE).edit();
                    editor.putBoolean("isChecked", false);
                    editor.apply();

                    //                result = myDatabaseHelper.userNameAlreadyExits(MyDatabaseHelper.TABLE_NAME,MyDatabaseHelper.PHONE,username);
                    int i;
                    for (i = 0; i < listView.getAdapter().getCount(); i++) {
                        rowId = myDatabaseHelper.insertData(list.get(i), list1.get(i)); //method is calling

                    }

                    Log.d("response>>", String.valueOf(rowId));
                    count1= Integer.parseInt(String.valueOf(rowId));

//                                            save.setVisibility(View.GONE);
                    save.setEnabled(false);
                    update.setEnabled(true);
//                                            if(save.isEnabled()==false)
//                                            {
//                                                answer = true;
//                                                SharedPreferences sharedPreferences = getSharedPreferences("gameScore", Context.MODE_PRIVATE);  //here getSharedPreferences have two parameters,one is Database name and another is access mode(public,private etc)
//                                                SharedPreferences.Editor editor = sharedPreferences.edit(); //to write the data in SharedPreference we have to use edit method
//                                                editor.putBoolean("lastScore",answer);
//                                                editor.commit();
//                                            }


                    if (rowId > -1) {
                        Toast.makeText(ContactAllActivity.this, "Information is successfully inserted", Toast.LENGTH_SHORT).show();
                        Log.d("response>>", name + "  " + phoneNumber);
                    }
                }


                else{
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.shafkatislam.shake_unlock.Contact", MODE_PRIVATE).edit();
                    editor.putBoolean("isChecked", true);
                    editor.apply();
                }


//                if(result)
//                {
//                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
//                }
//                else if(!result)
//                {
//                    int i;
//                    for(i=0;i<listView.getAdapter().getCount();i++)
//                    {
//                        rowId = myDatabaseHelper.insertData(list.get(i),list1.get(i)); //method is calling
//
//                    }
//                    if(rowId > -1)
//                    {
//                        Toast.makeText(ContactAllActivity.this, "Information is successfully inserted", Toast.LENGTH_SHORT).show();
//                        Log.d("response>>",name+"  "+phoneNumber);
//                    }
//                }

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j;
                for (j = 0; j < listView.getAdapter().getCount(); j++) {
                    result = myDatabaseHelper.userNameAlreadyExits(MyDatabaseHelper.TABLE_NAME, MyDatabaseHelper.PHONE, list1.get(j));

                    if (result == true) {
                        continue;
                    }
                    else if (result == false)

                    {
                        rowId = myDatabaseHelper.insertData(list.get(j), list1.get(j)); //method is calling
                        continue;

                    }
                }
                if(rowId > -1)
                {
                    Toast.makeText(ContactAllActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    Log.d("response>>",name+"  "+phoneNumber);
                }
            }
        });

        contactModelArrayList = new ArrayList<>();

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            ContactModel contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModelArrayList.add(contactModel);
            Log.d("name>>",name+"  "+phoneNumber);


            list.add(name);
            list1.add(phoneNumber);


//            contact.setName(name);
//            contact.setNumber(phoneNumber);
//
//            long rowId = myDatabaseHelper.insertData(contact); //method is calling
//
//            if(rowId > -1)
//            {
//                Toast.makeText(ContactAllActivity.this,"Data is successfully inserted",Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(ContactAllActivity.this,"Unsuccessfull attempted",Toast.LENGTH_SHORT).show();
//            }
        }
        phones.close();
        Log.d("names>>", String.valueOf(list));
        Log.d("number>>", String.valueOf(list1));

        customAdapter = new CustomAdapter(this,contactModelArrayList);
        listView.setAdapter(customAdapter);

        count = listView.getCount();
        Log.d("count>>", String.valueOf(count));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                contactModel = (ContactModel)contactModelArrayList.get(position);

                Intent intent = new Intent(ContactAllActivity.this,ContactProfileActivity.class);

//                intent.putExtra("name",contactModel.getName());
//                intent.putExtra("number",contactModel.getNumber());

                EventClass eventClass = EventClass.getInstance();
                eventClass.setId(position);
                eventClass.setNumber(contactModel.getNumber());
                eventClass.setName(contactModel.getName());

                startActivity(intent);
            }
        });



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
//
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

        Intent intent = new Intent(ContactAllActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater menuInflater = getMenuInflater();   //"MenuInflater" is a service which coverts xml file into java file
        menuInflater.inflate(R.menu.menu_layout,menu);    //"Inflate" method turns the xml file into java file and pass the menu class object

        MenuItem menuItem = menu.findItem(R.id.searchViewId);        //Find MenuItem which is a searchView. Because searchView is in the menuItem
        SearchView searchView = (SearchView) menuItem.getActionView();   //The menuItem which we get that is kept in the searchView variable  and in the xml file we set the searchView as a menuItem using actionViewClass .So we have to get the ActionView by the menuItem

        searchView.setQueryHint("Search a Name");

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
        
        getMenuInflater().inflate(R.menu.contact_all, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        contactModelArrayList.clear();

        myDatabaseHelper2 = new MyDatabaseHelper(this);

        ContactModel contactModel = null;

        cursor2 = myDatabaseHelper2.retrieve1(searchTerm);

        if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No "+searchTerm+" Blood Found.");
            //bloodListViewId.setVisibility(View.GONE);
            //noData.setVisibility(View.VISIBLE);
            //return;
//            showData();

            contactModelArrayList = new ArrayList<>();

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
            while (phones.moveToNext())
            {
                name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                ContactModel contactModel1 = new ContactModel();
                contactModel1.setName(name);
                contactModel1.setNumber(phoneNumber);
                contactModelArrayList.add(contactModel1);
                Log.d("name>>",name+"  "+phoneNumber);


                list.add(name);
                list1.add(phoneNumber);


//            contact.setName(name);
//            contact.setNumber(phoneNumber);
//
//            long rowId = myDatabaseHelper.insertData(contact); //method is calling
//
//            if(rowId > -1)
//            {
//                Toast.makeText(ContactAllActivity.this,"Data is successfully inserted",Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(ContactAllActivity.this,"Unsuccessfull attempted",Toast.LENGTH_SHORT).show();
//            }
            }
            phones.close();
            Log.d("names>>", String.valueOf(list));
            Log.d("number>>", String.valueOf(list1));

            customAdapter = new CustomAdapter(this,contactModelArrayList);
            listView.setAdapter(customAdapter);

            count = listView.getCount();
            Log.d("count>>", String.valueOf(count));

        }

        else
        {
            while (cursor2.moveToNext())
            {
                String name,number;
                name = cursor2.getString(0);
                number = cursor2.getString(1);

//                bloodSearch1 = new BloodSearch();
//
//                bloodSearch1.setName(name);
//                bloodSearch1.setNumber(number);
//                bloodSearch1.setBlood(blood);
//
//                bloodSearches.add(bloodSearch1);

                contactModelArrayList.add(new ContactModel(name,number));
            }

            listView.setAdapter(customAdapter);

        }
    }

    private void showData(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
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

            Intent intent = new Intent(ContactAllActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(ContactAllActivity.this,EmergencyListActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(ContactAllActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

//            Intent intent = new Intent(ContactAllActivity.this,ContactAllActivity.class);
//            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(ContactAllActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(ContactAllActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(ContactAllActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(ContactAllActivity.this,ReminderActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.blood) {

            Intent intent = new Intent(ContactAllActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.blood) {

            Intent intent = new Intent(ContactAllActivity.this,ReminderActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.settings) {
            Intent intent = new Intent(ContactAllActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
