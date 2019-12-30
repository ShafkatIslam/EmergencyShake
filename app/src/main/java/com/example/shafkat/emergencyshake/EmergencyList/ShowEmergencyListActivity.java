package com.example.shafkat.emergencyshake.EmergencyList;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.ContactAllActivity;
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

public class ShowEmergencyListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<ShowEmergency> showEmergencies = new ArrayList<ShowEmergency>();

    private ListView showEmergencylistView;
    MyDatabaseHelper myDatabaseHelper,myDatabaseHelper2;
    Cursor cursor,cursor2;

    ImageView noData;
    ShowEmergencyCustomAdapter showEmergencyCustomAdapter;
    Button okButton;
    public long rowId2;

    private AlertDialog.Builder alertdialogBuilder; //alertDialogBuilder variable declare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.emergency_list);
        setContentView(R.layout.activity_show_emergency_list);


        showEmergencylistView= (ListView)findViewById(R.id.showEmergencylistView);
        myDatabaseHelper = new MyDatabaseHelper(this);
        okButton = (Button)findViewById(R.id.okButton);

        showEmergencyCustomAdapter = new ShowEmergencyCustomAdapter(this,showEmergencies);
        showEmergencylistView.setAdapter(showEmergencyCustomAdapter);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);

        showData();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(showEmergencyCustomAdapter.getBox().isEmpty())
                {
                    Toast.makeText(ShowEmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
                else
                {

                    String result = "Selected Product are :";
                    String name ="";
                    String number ="";
                    for (ShowEmergency p : showEmergencyCustomAdapter.getBox()) {

                        if(!(p.box))
                        {
                            Toast.makeText(ShowEmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if (p.box){
                                result += "\n" + p.name;
                                name = p.name;
                                number = p.number;
                                {
                                    rowId2 = myDatabaseHelper.deleteData7(number,name); //method is calling

                                }

                            }

                        }

                    }

                    if (rowId2 > 0) {
                        Toast.makeText(ShowEmergencyListActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Log.d("response>>", name + "  " + number);
                        Intent intent = new Intent(ShowEmergencyListActivity.this, EmergencyListActivity.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Toast.makeText(ShowEmergencyListActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                    }
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
    }

    private void showData() {

        cursor = myDatabaseHelper.showAllData2();     //we have to call the showAlllData method with the help of myDatabaseHelper because showAllData is a method of myDatabaseHelper


        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Data Found.\nPlease Import Emergency Contact");
            showEmergencylistView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            okButton.setEnabled(false);
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

                    showEmergencies.add(new ShowEmergency(name,number,false));

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
//
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

        Intent intent = new Intent(ShowEmergencyListActivity.this,EmergencyListActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onRestart() {
////        super.onRestart();
////        //When BACK BUTTON is pressed, the activity on the stack is restarted
////        //Do what you want on the refresh procedure here
//        super.onRestart();
//        finish();
//        startActivity(getIntent());
//    }

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
        getMenuInflater().inflate(R.menu.show_emergency_list, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        showEmergencies.clear();

        myDatabaseHelper2 = new MyDatabaseHelper(this);

        Emergency emergency = null;

        cursor2 = myDatabaseHelper2.retrieve2(searchTerm);

        if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error",searchTerm+" is not Found.");
            //bloodListViewId.setVisibility(View.GONE);
            //noData.setVisibility(View.VISIBLE);
            //return;
            showData();

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

                showEmergencies.add(new ShowEmergency(name,number));
            }

            showEmergencylistView.setAdapter(showEmergencyCustomAdapter);

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

            Intent intent = new Intent(ShowEmergencyListActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(ShowEmergencyListActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(ShowEmergencyListActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(ShowEmergencyListActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(ShowEmergencyListActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(ShowEmergencyListActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(ShowEmergencyListActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(ShowEmergencyListActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(ShowEmergencyListActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(ShowEmergencyListActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
