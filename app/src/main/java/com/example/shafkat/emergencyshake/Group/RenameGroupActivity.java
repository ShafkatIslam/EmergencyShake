package com.example.shafkat.emergencyshake.Group;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.Emergency;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergency;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenameGroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<RenameGroup> renameGroups = new ArrayList<RenameGroup>();
    public List<String> allGroupNames = new ArrayList<String>();

    ListView RenameGrouplistView;

    ImageView noData;

    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1,myDatabaseHelper2,myDatabaseHelper3,myDatabaseHelper4;
    Cursor cursor2,cursor3,cursor4,cursor5,cursor6;
    RenameGroupCustomAdapter renameGroupCustomAdapter;
    public long rowId3,rowId4;
    public int count;
    Button RenameDeleteButton;

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    private AlertDialog.Builder alertdialogBuilder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.rename_group);
        setContentView(R.layout.activity_rename_group);

        RenameGrouplistView = (ListView)findViewById(R.id.RenameGrouplistView);
        myDatabaseHelper = new MyDatabaseHelper(this);
        renameGroupCustomAdapter = new RenameGroupCustomAdapter(this,renameGroups);
        RenameGrouplistView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        RenameGrouplistView.setAdapter(renameGroupCustomAdapter);

        RenameDeleteButton = (Button)findViewById(R.id.RenameDeleteButton);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        count = 0;


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

        cursor4 = myDatabaseHelper1.showAllData3();

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

        loadData();

        myDatabaseHelper2 = new MyDatabaseHelper(this);
        cursor5 = myDatabaseHelper2.showAllData4();

        RenameDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(renameGroupCustomAdapter.getBox().isEmpty())
                {
                    Toast.makeText(RenameGroupActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(cursor5.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                    {

                        Toast.makeText(RenameGroupActivity.this,"You don't have any group",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String result = "Selected Product are :";
                        String name ="";
                        String number ="";
                        for (RenameGroup p : renameGroupCustomAdapter.getBox())
                        {
                            if(!(p.box))
                            {
                                Toast.makeText(RenameGroupActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if (p.box){
                                    result += "\n" + p.name;
                                    name = p.name;
                                    {
                                        myDatabaseHelper.deleteTable(name); //method is calling
                                        rowId3 = myDatabaseHelper1.deleteData6(name);

                                    }

                                }
                            }
                        }
                        //if ((rowId4 > 0)&&(rowId3 > 0))
                        if ((rowId3 > 0)) {
                            Toast.makeText(RenameGroupActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Log.d("response>>", name + "  " + number);
                            Intent intent = new Intent(RenameGroupActivity.this,RenameGroupActivity.class);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(RenameGroupActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        RenameGrouplistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RenameGroupActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.rename_group,null);
                final EditText groupRenameEditText = (EditText)mView.findViewById(R.id.groupRenameEditText);
                final String gName = allGroupNames.get(position);
                groupRenameEditText.setText(gName);
                Button groupSaveButton = (Button)mView.findViewById(R.id.groupSaveButton);

                myDatabaseHelper3 = new MyDatabaseHelper(RenameGroupActivity.this);

                groupSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String gNames= groupRenameEditText.getText().toString().trim();
                        gNames = gNames.replaceAll("[^a-zA-Z]", "");

                        if(gNames.isEmpty())
                        {
                            groupRenameEditText.setError("Please Enter a Group Name");
                            groupRenameEditText.requestFocus();
                        }
                        else
                        {
                            myDatabaseHelper3.RenameTable(gName,gNames);
                            //cursor6= myDatabaseHelper.updateData(gName,gNames);
                            myDatabaseHelper.updateData(gName,gNames);

                            Toast.makeText(RenameGroupActivity.this,"Rename Successfully",Toast.LENGTH_SHORT).show();
                            groupRenameEditText.setText("");

                            Intent intent = new Intent(RenameGroupActivity.this,RenameGroupActivity.class);
                            startActivity(intent);
                        }

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    private void loadData() {

        cursor3 = myDatabaseHelper.showAllData4();     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper


        if(cursor3.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Create Group");
            RenameGrouplistView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
            //return;
            RenameDeleteButton.setEnabled(false);
        }
        else
        {
            noData.setVisibility(View.GONE);
            RenameDeleteButton.setEnabled(true);
            if(cursor3.moveToFirst())
            {
                do {
                    String name;
                    int number;
                    name = cursor3.getString(0);

                    allGroupNames.add(name);

                    cursor2 = myDatabaseHelper.showAllData5(name);

                    if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                    {
                        //there is no data so we will diplay message
//                        showData("Error","No Data Found");
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
                        //return;
                        renameGroups.add(new RenameGroup(name,0,false));
                    }
                    else
                    {
                        if(cursor2.moveToFirst())
                        {
                            do{
                                count++;
                                number = count;
                            }
                            while(cursor2.moveToNext());  //to check till there is a row after a previous row
                            {
                                count=0;
                                //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                            }

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                            renameGroups.add(new RenameGroup(name,number,false));
                        }
                    }


                }
                //it will check the String from first to last in the cursor
                while(cursor3.moveToNext());  //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                }

                Log.d("allnames", String.valueOf(allGroupNames));
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
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        Intent intent = new Intent(RenameGroupActivity.this,GroupCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();   //"MenuInflater" is a service which coverts xml file into java file
        menuInflater.inflate(R.menu.menu_layout,menu);    //"Inflate" method turns the xml file into java file and pass the menu class object

        MenuItem menuItem = menu.findItem(R.id.searchViewId);        //Find MenuItem which is a searchView. Because searchView is in the menuItem
        SearchView searchView = (SearchView) menuItem.getActionView();   //The menuItem which we get that is kept in the searchView variable  and in the xml file we set the searchView as a menuItem using actionViewClass .So we have to get the ActionView by the menuItem

        searchView.setQueryHint("Search a Group Name");

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
        getMenuInflater().inflate(R.menu.rename_group, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        renameGroups.clear();

        myDatabaseHelper4 = new MyDatabaseHelper(this);

        Emergency emergency = null;

        cursor6 = myDatabaseHelper4.retrieve4(searchTerm);

        if(cursor6.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No "+searchTerm+" Group Found.");
            //bloodListViewId.setVisibility(View.GONE);
            //noData.setVisibility(View.VISIBLE);
            //return;
            loadData();

        }

        else
        {
            while (cursor6.moveToNext())
            {
                String name;
                int number;
                name = cursor6.getString(0);


//                bloodSearch1 = new BloodSearch();
//
//                bloodSearch1.setName(name);
//                bloodSearch1.setNumber(number);
//                bloodSearch1.setBlood(blood);
//
//                bloodSearches.add(bloodSearch1);

                cursor2 = myDatabaseHelper.showAllData5(name);

                if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                {
                    //there is no data so we will diplay message
//                        showData("Error","No Data Found");
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
                    //return;
                    renameGroups.add(new RenameGroup(name,0,false));
                }
                else
                {
                    if(cursor2.moveToFirst())
                    {
                        do{
                            count++;
                            number = count;
                        }
                        while(cursor2.moveToNext());  //to check till there is a row after a previous row
                        {
                            count=0;
                            //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata
                        }

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                        renameGroups.add(new RenameGroup(name,number,false));
                    }
                }
            }

            RenameGrouplistView.setAdapter(renameGroupCustomAdapter);

        }


    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            Intent intent = new Intent(RenameGroupActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(RenameGroupActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(RenameGroupActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(RenameGroupActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {

            Intent intent = new Intent(RenameGroupActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {

            Intent intent = new Intent(RenameGroupActivity.this,GroupContactsActivity.class);
            startActivity(intent);


        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(RenameGroupActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(RenameGroupActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(RenameGroupActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(RenameGroupActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
