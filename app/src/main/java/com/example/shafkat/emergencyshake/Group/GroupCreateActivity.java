package com.example.shafkat.emergencyshake.Group;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.Emergency;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyCustomAdapter;
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
import java.util.List;

public class GroupCreateActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<AllGroup> allGroups = new ArrayList<AllGroup>();

    private ListView allGroupListView;

    Spinner spinner;
    public String groupName;
    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper1,myDatabaseHelper2,myDatabaseHelper3,myDatabaseHelper4;
    Cursor cursor2,cursor3,cursor4,cursor5;
    AllGroupCustomAdapter allGroupCustomAdapter;
    public long rowId3,rowId4,rowId5,count;
    Button allGroupSendButton;
    //String[] groupNames;
    public List<String> groupNames = new ArrayList<String>();

    TextView headerNameTextView,headerNumberTextView;
    ImageView headerImageView;

    ImageView noData;

    private AlertDialog.Builder alertdialogBuilder1;

    public static String stringValue;

//    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.group_create);
        setContentView(R.layout.activity_group_create);

//        this.mHandler = new Handler();
//
//        m_Runnable.run();

        allGroupListView = (ListView) findViewById(R.id.allGrouplistView);
        myDatabaseHelper3 = new MyDatabaseHelper(this);

        allGroupCustomAdapter = new AllGroupCustomAdapter(this,allGroups);
        allGroupListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        allGroupListView.setAdapter(allGroupCustomAdapter);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        allGroupSendButton = (Button)findViewById(R.id.allGroupSendButton);

        spinner = (Spinner) findViewById(R.id.spinnerId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDatabaseHelper = new MyDatabaseHelper(GroupCreateActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(GroupCreateActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.group_create,null);
                final EditText groupCreateEditText = (EditText)mView.findViewById(R.id.groupCreateEditText);
                Button createButton = (Button)mView.findViewById(R.id.createButton);

                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        groupName = groupCreateEditText.getText().toString().trim();
                        groupName = groupName.replaceAll("[^a-zA-Z]", "");

                        boolean result;
                        result = myDatabaseHelper.groupAlreadyExits(MyDatabaseHelper.TABLE_NAME4,MyDatabaseHelper.GROUP,groupName);

                        if(groupName.isEmpty())
                        {
                            groupCreateEditText.setError("Please Enter a Group Name");
                            groupCreateEditText.requestFocus();
                        }
                        else if(result)
                        {
                            groupCreateEditText.setError("Group name already exists");
                            groupCreateEditText.requestFocus();
                            //Toast.makeText(getApplicationContext(), "Group name already exists", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            rowId3 = myDatabaseHelper.insertData4(groupName);

                            if (rowId3 > 0) {
                                Toast.makeText(GroupCreateActivity.this, groupName+" group is created successfully", Toast.LENGTH_SHORT).show();

                                groupCreateEditText.setText("");

//                                stringValue = groupName;
//
//                                GroupTable groupTable = new GroupTable();
//                                groupTable.groupName = groupName;

                                myDatabaseHelper.AddDesiredTable(groupName);


                                Intent refresh =new Intent(GroupCreateActivity.this,GroupCreateActivity.class);
//                                refresh.putExtra("group",groupName);
                                startActivity(refresh);

                                //stringValue = getIntent().getExtras().getString("group");
                            }
                            else
                            {
                                Toast.makeText(GroupCreateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        allGroupSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allGroupCustomAdapter.getBox().isEmpty())
                {
                    Toast.makeText(GroupCreateActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //rowId1 = myDatabaseHelper.deleteData2();

                    if(cursor3.getCount() == 0)          //now we have to check how many no of rows are in the cursor
                    {

                        Toast.makeText(GroupCreateActivity.this,"You don't have any group",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String result = "Selected Product are :";
                        String name ="";
                        String number ="";
                        String groupname = spinner.getSelectedItem().toString();
                        count = myDatabaseHelper.checkDataAvailablity(groupname);

                        if(count<0)
                        {
                            for (AllGroup p : allGroupCustomAdapter.getBox()) {

                                if(!(p.box))
                                {
                                    Toast.makeText(GroupCreateActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    if (p.box){
                                        result += "\n" + p.name;
                                        name = p.name;
                                        number = p.number;
                                        {
                                            rowId4 = myDatabaseHelper.insertData5(groupname,name,number); //method is calling

                                        }

                                    }
                                }

                            }

                            if (rowId4 > 0) {
                                Toast.makeText(GroupCreateActivity.this, "Information is saved", Toast.LENGTH_SHORT).show();
                                Log.d("response>>", name + "  " + number);

                                Intent intent = new Intent(GroupCreateActivity.this,GroupCreateActivity.class);
                                startActivity(intent);

                            }

                            else
                            {
                                Toast.makeText(GroupCreateActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                            }
                        }

                        else
                        {
                            rowId5 = myDatabaseHelper.deleteData5(groupname);
                            for (AllGroup p : allGroupCustomAdapter.getBox()) {

                                if(!(p.box))
                                {
                                    Toast.makeText(GroupCreateActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    if (p.box){
                                        result += "\n" + p.name;
                                        name = p.name;
                                        number = p.number;
                                        {
                                            rowId4 = myDatabaseHelper.insertData5(groupname,name,number); //method is calling

                                        }

                                    }
                                }

                            }

                            if (rowId4 > 0) {
                                Toast.makeText(GroupCreateActivity.this, "Information is saved", Toast.LENGTH_SHORT).show();
                                Log.d("response>>", name + "  " + number);

                                Intent intent = new Intent(GroupCreateActivity.this,GroupCreateActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                Toast.makeText(GroupCreateActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                            }
                        }
                    }


                }
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



        myDatabaseHelper2 = new MyDatabaseHelper(this);

        cursor3 = myDatabaseHelper2.showAllData4();

        if(cursor3.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {

            Toast.makeText(GroupCreateActivity.this,"You don't have any group",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(cursor3.moveToFirst())
            {

                do {

                    String name1;
                    name1= cursor3.getString(0);
                    groupNames.add(name1);
                }
                //it will check the String from first to last in the cursor
                while(cursor3.moveToNext()); //to check till there is a row after a previous row
                {
                    //listData.add(cursor.getString(1)+" \t "+cursor.getString(3));  //everytime add the 0 and 1 index value with the listdata

                }
                Log.d("name>>", String.valueOf(groupNames));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroupCreateActivity.this,R.layout.spinnerview,R.id.textViewId,groupNames); //creating object of ArrayAdapter....thre are 4 parameters in ArrayAdapter
        spinner.setAdapter(adapter);   //setting the adapter in spinner

        loadData();

    }

    private void loadData() {

        cursor5 = myDatabaseHelper3.showAllData();     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper


        if(cursor5.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Import Contact List");
            allGroupListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
            //return;
            allGroupSendButton.setEnabled(false);
        }
        else
        {
            noData.setVisibility(View.GONE);
            allGroupSendButton.setEnabled(true);
            if(cursor5.moveToFirst())
            {
                do {
                    String name,number;
                    name = cursor5.getString(0);
                    number = cursor5.getString(1);

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);

                    allGroups.add(new AllGroup(name,number,false));

                }
                //it will check the String from first to last in the cursor
                while(cursor5.moveToNext());  //to check till there is a row after a previous row
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

//    private final Runnable m_Runnable = new Runnable()
//    {
//        public void run()
//
//        {
//            //Toast.makeText(GroupCreateActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
//
//            GroupCreateActivity.this.mHandler.postDelayed(m_Runnable, 2000);
//        }
//
//    };//runnable

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        Intent intent = new Intent(GroupCreateActivity.this,HomeActivity.class);
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
        getMenuInflater().inflate(R.menu.group_create, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        allGroups.clear();

        myDatabaseHelper4 = new MyDatabaseHelper(this);

        AllGroup allGroup = null;

        cursor2 = myDatabaseHelper4.retrieve1(searchTerm);

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

                allGroups.add(new AllGroup(name,number));
            }

            allGroupListView.setAdapter(allGroupCustomAdapter);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rename_group) {

            Intent intent = new Intent(GroupCreateActivity.this,RenameGroupActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {

            Intent intent = new Intent(GroupCreateActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(GroupCreateActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(GroupCreateActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(GroupCreateActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {

//            Intent intent = new Intent(GroupCreateActivity.this,GroupCreateActivity.class);
//            startActivity(intent);

        } else if (id == R.id.groupContacts) {

            Intent intent = new Intent(GroupCreateActivity.this,GroupContactsActivity.class);
            startActivity(intent);


        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(GroupCreateActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(GroupCreateActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(GroupCreateActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(GroupCreateActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
