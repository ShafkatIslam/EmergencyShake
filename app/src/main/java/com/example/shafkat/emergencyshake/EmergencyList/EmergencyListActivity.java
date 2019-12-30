package com.example.shafkat.emergencyshake.EmergencyList;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactModel;
import com.example.shafkat.emergencyshake.ContactAll.CustomAdapter;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;

public class EmergencyListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Emergency> emergencies = new ArrayList<Emergency>();

    private ListView emergencyListView;
    MyDatabaseHelper myDatabaseHelper;
    MyDatabaseHelper myDatabaseHelper1;
    MyDatabaseHelper myDatabaseHelper2;
    Cursor cursor;
    Cursor cursor1;
    Cursor cursor2;
    EmergencyCustomAdapter emergencyCustomAdapter;
    Button emergencySendButton,emergencyUpdateButton,emergencyShowButton;
    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;
    ImageView noData;
    public long rowId1;
    public ArrayList<String> list2 = new ArrayList<String>();;
    public ArrayList<String> list3 = new ArrayList<String>();;
    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.emergency_contacts);
        setContentView(R.layout.activity_emergency_list);

        emergencyListView = (ListView) findViewById(R.id.emergencylistView);
        myDatabaseHelper = new MyDatabaseHelper(this);
        emergencySendButton = (Button)findViewById(R.id.emergencySendButton);
        emergencyUpdateButton = (Button)findViewById(R.id.emergencyUpdateButton);
        emergencyShowButton = (Button)findViewById(R.id.emergencyShowButton);

        emergencyCustomAdapter = new EmergencyCustomAdapter(this,emergencies);
        emergencyListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        emergencyListView.setAdapter(emergencyCustomAdapter);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);

        View header = navigationView1.getHeaderView(0);
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

        SharedPreferences saveFavPrefs = getSharedPreferences("com.example.shafkat.emergencyshake.EmergencyList", MODE_PRIVATE);
        emergencySendButton.setEnabled(saveFavPrefs.getBoolean("isChecked", true));

        if(emergencySendButton.isEnabled()==true)
        {
            emergencyUpdateButton.setEnabled(false);
        }
        else
        {
            emergencyUpdateButton.setEnabled(true);
        }

        emergencyUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emergencyCustomAdapter.getBox().isEmpty())
                {
                    Toast.makeText(EmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
                else
                {
                    rowId1 = myDatabaseHelper.deleteData2();

                    String result = "Selected Product are :";
                    String name ="";
                    String number ="";
                    for (Emergency p : emergencyCustomAdapter.getBox()) {

                        if(!(p.box))
                        {
                            Toast.makeText(EmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if (p.box){
                                result += "\n" + p.name;
                                name = p.name;
                                number = p.number;
                                {
                                    rowId1 = myDatabaseHelper.insertData2(name,number); //method is calling

                                }

                            }
                            emergencySendButton.setEnabled(false);
                        }

                    }

                    if (rowId1 > 0) {
                        Toast.makeText(EmergencyListActivity.this, "Information is updated", Toast.LENGTH_SHORT).show();
                        Log.d("response>>", name + "  " + number);
                    }

                    else
                    {
                        Toast.makeText(EmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                    }
                }



                //Toast.makeText(FriendsActivity.this, result, Toast.LENGTH_LONG).show();

//                String selected = "";
//                int cntChoice = friendslistView.getCount();
//                sparseBooleanArray = friendslistView.getCheckedItemPositions();
//                for(int i = 0; i < cntChoice; i++)
//                {
//                    if(sparseBooleanArray.get(i))
//                    {
//                        selected += friendslistView.getItemAtPosition(i).toString() + "\n";
//                        System.out.println("Checking list while adding:" + friendslistView.getItemAtPosition(i).toString());
//                        SaveSelections();
//                    }
//                }
//                Toast.makeText(FriendsActivity.this, selected, Toast.LENGTH_LONG).show();

            }
        });


        emergencyShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmergencyListActivity.this,ShowEmergencyListActivity.class);
                startActivity(intent);
            }
        });

        emergencySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emergencySendButton.isPressed())
                {

                    SharedPreferences.Editor editor = getSharedPreferences("com.example.shafkat.emergencyshake.EmergencyList", MODE_PRIVATE).edit();
                    editor.putBoolean("isChecked", false);
                    editor.apply();
                    //showResult(v);
                    //CheckBox cbBuy = (CheckBox)findViewById(R.id.friends_select_check_box);
                    String result = "Selected Product are :";
                    String name ="";
                    String number ="";
                    for (Emergency p : emergencyCustomAdapter.getBox()) {

                        if(!(p.box))
                        {
                            Toast.makeText(EmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if (p.box){
                                result += "\n" + p.name;
                                name = p.name;
                                number = p.number;
                                {
                                    rowId1 = myDatabaseHelper.insertData2(name,number); //method is calling

                                }

                            }
                            emergencySendButton.setEnabled(false);
                            emergencyUpdateButton.setEnabled(true);
                        }

                    }

                    if (rowId1 > 0) {
                        Toast.makeText(EmergencyListActivity.this, "Information is successfully inserted", Toast.LENGTH_SHORT).show();
                        Log.d("response>>", name + "  " + number);

                        Intent intent = new Intent(EmergencyListActivity.this,EmergencyListActivity.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Toast.makeText(EmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(FriendsActivity.this, result, Toast.LENGTH_LONG).show();
                }

                else{
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.shafkatislam.shake_unlock.Friends", MODE_PRIVATE).edit();
                    editor.putBoolean("isChecked", true);
                    editor.apply();
                }

                //cbBuy.setChecked(Boolean.parseBoolean(result));
            }
        });

        loadData();
        //showResult(null);



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
    }

    private void loadData() {

        cursor = myDatabaseHelper.showAllData();     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper


        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Import Contact List");
            emergencyListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            emergencyUpdateButton.setEnabled(false);
            emergencySendButton.setEnabled(false);
            emergencyShowButton.setEnabled(false);
            //return;
        }
        else
        {
            noData.setVisibility(View.GONE);
            if(cursor.moveToFirst())
            {
                do {
                    String name,number;
                    name = cursor.getString(0);
                    number = cursor.getString(1);

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                    emergencies.add(new Emergency(name,number,false));

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

        Intent intent = new Intent(EmergencyListActivity.this,HomeActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.emergency_list, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        emergencies.clear();

        myDatabaseHelper2 = new MyDatabaseHelper(this);

        Emergency emergency = null;

        cursor2 = myDatabaseHelper2.retrieve1(searchTerm);

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

                emergencies.add(new Emergency(name,number));
            }

            emergencyListView.setAdapter(emergencyCustomAdapter);

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

            Intent intent = new Intent(EmergencyListActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(EmergencyListActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(EmergencyListActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(EmergencyListActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(EmergencyListActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(EmergencyListActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(EmergencyListActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(EmergencyListActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(EmergencyListActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
