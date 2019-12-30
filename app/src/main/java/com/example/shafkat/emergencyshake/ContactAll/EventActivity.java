package com.example.shafkat.emergencyshake.ContactAll;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.Blood.BloodSearchActivity;
import com.example.shafkat.emergencyshake.ContactAll.Utils.MyDividerItemDecoration;
import com.example.shafkat.emergencyshake.ContactAll.Utils.RecyclerTouchListener;
import com.example.shafkat.emergencyshake.Database.MyDatabaseHelper;
import com.example.shafkat.emergencyshake.EmergencyList.Emergency;
import com.example.shafkat.emergencyshake.EmergencyList.EmergencyListActivity;
import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergency;
import com.example.shafkat.emergencyshake.EmergencySms.EmergencySmsActivity;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.Group.RenameGroup;
import com.example.shafkat.emergencyshake.Group.RenameGroupActivity;
import com.example.shafkat.emergencyshake.Group.RenameGroupCustomAdapter;
import com.example.shafkat.emergencyshake.GroupContact.GroupContactsActivity;
import com.example.shafkat.emergencyshake.HomeActivity.HomeActivity;
import com.example.shafkat.emergencyshake.MainActivity;
import com.example.shafkat.emergencyshake.R;
import com.example.shafkat.emergencyshake.Reminder.ReminderActivity;
import com.example.shafkat.emergencyshake.SendSms.SendSmsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EventAdapter eventAdapter;
    private List<Event> eventList = new ArrayList<>();
    ArrayList<EventDate> eventDates = new ArrayList<EventDate>();
    public List<String> allEventNames = new ArrayList<String>();
    public List<String> allEventDates = new ArrayList<String>();
    public List<Integer> allEventId = new ArrayList<Integer>();
    EventDateCustomAdapter eventDateCustomAdapter;
//    private CoordinatorLayout coordinatorLayout;
//    private RecyclerView recyclerView;
//    private TextView noEventView;

    public String number,name1;
    String parsedDate;
    String parsedDate1;
    String parsedDate2;

    int rowId;

    ListView EventListView;
    Button EventDeleteButton;

    ImageView noData;

    DatePickerDialog datePickerDialog;

    private MyDatabaseHelper db,db1,db2,myDatabaseHelper2;
    Cursor cursor,cursor2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.event_list);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        coordinatorLayout = findViewById(R.id.coordinator_layout);
//        recyclerView = findViewById(R.id.recycler_view);
//        noEventView = findViewById(R.id.empty_notes_view);

//        Bundle bundle=getIntent().getExtras();
//        phone =  bundle.getString("number");

//        Bundle bundle=getIntent().getExtras();
//        number=bundle.getString("number");

        EventClass eventClass = EventClass.getInstance();

        number = eventClass.getNumber();
        name1 = eventClass.getName();

        EventListView = (ListView)findViewById(R.id.EventlistView);
        eventDateCustomAdapter = new EventDateCustomAdapter(this,eventDates);
        EventListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        EventListView.setAdapter(eventDateCustomAdapter);

        EventDeleteButton = (Button)findViewById(R.id.EventDeleteButton);

        noData = (ImageView)findViewById(R.id.noData);
        noData.setVisibility(View.GONE);


        db = new MyDatabaseHelper(this);
//        eventList.addAll(db.getAllEvents(phone));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                showNoteDialog(false, null, -1);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.event_dialog,null);
                final EditText inputNote = mView.findViewById(R.id.note);
                final TextView eventDateTextView = mView.findViewById(R.id.eventDateTextView);
                final Button eventDateButton = mView.findViewById(R.id.eventDateButtonId);
                final Button createButton = (Button)mView.findViewById(R.id.eventCreateButton);



                eventDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePicker datePicker = new DatePicker(EventActivity.this);
                        int currentday= datePicker.getDayOfMonth();
                        int currentMonth = (datePicker.getMonth());
                        int currentYear = datePicker.getYear();
                        datePickerDialog = new DatePickerDialog(EventActivity.this,

                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                        String result = (dayOfMonth+"/"+(month+1)+"/"+year);
                                        eventDateTextView.setText(result);

//                                EventClass eventClass = EventClass.getInstance();
//                                eventClass.setDate(result);

                                    }
                                },currentYear,currentMonth,currentday);

                        datePickerDialog.show();
                    }
                });

                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String notes = inputNote.getText().toString();
                        String date = eventDateTextView.getText().toString();

                        if(notes.isEmpty())
                        {
                            inputNote.setError("Please Enter a Group Name");
                            inputNote.requestFocus();
                        }

                        else if(date.isEmpty())
                        {
                            Toast.makeText(EventActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {

                            try {
                                Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                parsedDate = formatter.format(initDate);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            long id = db.insertNote(notes,parsedDate,number,name1);

                            if (id > 0) {
                                Toast.makeText(EventActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                                inputNote.setText("");

//                                stringValue = groupName;
//
//                                GroupTable groupTable = new GroupTable();
//                                groupTable.groupName = groupName;


                                Intent refresh =new Intent(EventActivity.this,EventActivity.class);
//                                refresh.putExtra("group",groupName);
                                startActivity(refresh);

                                //stringValue = getIntent().getExtras().getString("group");
                            }
                            else
                            {
                                Toast.makeText(EventActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }
        });

//        eventAdapter = new EventAdapter(this, eventList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
//        recyclerView.setAdapter(eventAdapter);
//
//        toggleEmptyNotes();
//
//        /**
//         * On long press on RecyclerView item, open alert dialog
//         * with options to choose
//         * Edit and Delete
//         * */
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
//                recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, final int position) {
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                showActionsDialog(position);
//            }
//        }));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        loadData();

        EventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.event_dialog,null);
                final EditText inputNote = mView.findViewById(R.id.note);
                final TextView eventDateTextView = mView.findViewById(R.id.eventDateTextView);
                final Button eventDateButton = mView.findViewById(R.id.eventDateButtonId);
                final Button createButton = (Button)mView.findViewById(R.id.eventCreateButton);

                final int id1 = allEventId.get(position);
                final String eName = allEventNames.get(position);
                final String eDate = allEventDates.get(position);
                inputNote.setText(eName);
                eventDateTextView.setText(eDate);

                db1 = new MyDatabaseHelper(EventActivity.this);

                eventDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePicker datePicker = new DatePicker(EventActivity.this);
                        final int currentday= datePicker.getDayOfMonth();
                        final int currentMonth = (datePicker.getMonth());
                        final int currentYear = datePicker.getYear();
                        datePickerDialog = new DatePickerDialog(EventActivity.this,

                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                        String result = (dayOfMonth+"/"+(month+1)+"/"+year);
                                        eventDateTextView.setText(result);

//                                        String mDate=convertDate(convertToMillis(currentday,currentMonth,currentYear));
                                        //String date = DateFormater(result);

//                                EventClass eventClass = EventClass.getInstance();
//                                eventClass.setDate(result);

                                    }
                                },currentYear,currentMonth,currentday);

                        datePickerDialog.show();
                    }
                });

                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String notes = inputNote.getText().toString();
                        String date = eventDateTextView.getText().toString();

                        if(notes.isEmpty())
                        {
                            inputNote.setError("Please Enter a Group Name");
                            inputNote.requestFocus();
                        }

                        else if(date.isEmpty())
                        {
                            Toast.makeText(EventActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {

                            try {
                                Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                parsedDate2 = formatter.format(initDate);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            db1.updateEvent(notes,parsedDate2,number,id1);
                            Toast.makeText(EventActivity.this,"Update Successfully",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(EventActivity.this,EventActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });

        db2 = new MyDatabaseHelper(EventActivity.this);

        EventDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventDateCustomAdapter.getBox().isEmpty())
                {
                    Toast.makeText(EventActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String result = "Selected Product are :";
                    String eName;
                    String eDate;

                    eName = "";
                    eDate = "";
                    EventClass eventClass = EventClass.getInstance();
                    String eNumber = eventClass.getNumber();
                    for (EventDate p : eventDateCustomAdapter.getBox())
                    {
                        if(!(p.box))
                        {
                            Toast.makeText(EventActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if (p.box){
                                result += "\n" + p.eventName;
                                eName = p.eventName;
                                eDate = p.eventDate;
                                {
//                                    myDatabaseHelper.deleteTable(name); //method is calling
//                                    rowId3 = myDatabaseHelper1.deleteData6(name);

//                                   db2.deleteEvent(eName,eDate,eNumber);
                                    db2.DeleteEvent(eName,eDate,eNumber);

                                }

                            }
                        }
                    }

                    Toast.makeText(EventActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EventActivity.this,EventActivity.class);
                    startActivity(intent);

//                    if ((rowId > 0)) {
//                        Toast.makeText(EventActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(EventActivity.this,EventActivity.class);
//                        startActivity(intent);
//                    }
//
//                    else
//                    {
//                        Toast.makeText(EventActivity.this, "Please Select", Toast.LENGTH_LONG).show();
//                    }
                }
            }
        });
    }

//    public String DateFormater (String date)
//    {
//        SimpleDateFormat sdf;
//        sdf = new SimpleDateFormat("dd MMM yyyy");  //format of the date which you send as parameter(if the date is like 08-Aug-2016 then use dd-MMM-yyyy)
//        String s = "";
//        try {
//            Date dt = sdf.parse(date);
//            sdf = new SimpleDateFormat("yyyy-MM-dd");
//            s = sdf.format(dt);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return s;
//    }
//
//    public String convertDate(long mTime) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
////        String formattedDate = df.format(mTime);
////        return formattedDate;
//        String s = "";
//        try {
//            Date dt = sdf.parse(String.valueOf(mTime));
//            sdf = new SimpleDateFormat("yyyy-MM-dd");
//            s = sdf.format(dt);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return s;
//    }
//
//    public long convertToMillis(int day, int month, int year) {
//        Calendar calendarStart = Calendar.getInstance();
//        calendarStart.set(Calendar.YEAR, year);
//        calendarStart.set(Calendar.MONTH, month);
//        calendarStart.set(Calendar.DAY_OF_MONTH, day);
//        return calendarStart.getTimeInMillis();
//    }

    private void loadData() {

//        Bundle bundle=getIntent().getExtras();
//        final String phone =  bundle.getString("number");

        EventClass eventClass = EventClass.getInstance();

        String phoneNumber = eventClass.getNumber();

//        if(phoneNumber.isEmpty())
//        {
//
//        }
//        else
//        {
//            Log.d("Number",phoneNumber);
//        }

        cursor = db.showAllEvent(phoneNumber);

        if(cursor.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error","No Event Found.");
            EventListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
//            emergencyUpdateButton.setEnabled(false);
//            emergencySendButton.setEnabled(false);
//            emergencyShowButton.setEnabled(false);
            //return;
            EventDeleteButton.setEnabled(false);
        }

        else
        {
            noData.setVisibility(View.GONE);
            EventDeleteButton.setEnabled(true);
            if(cursor.moveToFirst())
            {
                do {
                    String note,time;
                    int id;
                    id = cursor.getInt(0);
                    note = cursor.getString(1);
                    time = cursor.getString(2);

                    try {
                        Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        parsedDate1 = formatter.format(initDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    Friends friends = new Friends(name,number);
//                    friendsCustomAdapter.add(friends);
//                    list.add(friends);
                    allEventNames.add(note);
                    allEventDates.add(parsedDate1);
                    allEventId.add(id);

                    eventDates.add(new EventDate(note,time,false));
//                    allGroups.add(new AllGroup(name,number,false));

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

//    private void showNoteDialog(final boolean shouldUpdate, final Event event, final int position) {
//        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
//        View view = layoutInflaterAndroid.inflate(R.layout.event_dialog, null);
//
//        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(EventActivity.this);
//        alertDialogBuilderUserInput.setView(view);
//
//        final EditText inputNote = view.findViewById(R.id.note);
//        final TextView eventDateTextView = view.findViewById(R.id.eventDateTextView);
//        final Button eventDateButton = view.findViewById(R.id.eventDateButtonId);
//
//        Bundle bundle=getIntent().getExtras();
//        final String number=bundle.getString("number");
//
//        eventDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePicker datePicker = new DatePicker(EventActivity.this);
//                int currentday= datePicker.getDayOfMonth();
//                int currentMonth = (datePicker.getMonth()+1);
//                int currentYear = datePicker.getYear();
//                datePickerDialog = new DatePickerDialog(EventActivity.this,
//
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                                String result = (dayOfMonth+"/"+(month+1)+"/"+year);
//                                eventDateTextView.setText(result);
//
////                                EventClass eventClass = EventClass.getInstance();
////                                eventClass.setDate(result);
//
//                            }
//                        },currentYear,currentMonth,currentday);
//
//                datePickerDialog.show();
//            }
//        });
//
////        EventClass eventClass = EventClass.getInstance();
////        String date;
////        date = eventClass.getDate();
////        eventDateTextView.setText(date);
//
//        TextView dialogTitle = view.findViewById(R.id.dialog_title);
//        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));
//
//        if (shouldUpdate && event != null) {
//            inputNote.setText(event.getEvent());
//        }
//        alertDialogBuilderUserInput
//                .setCancelable(false)
//                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogBox, int id) {
//
//                    }
//                })
//                .setNegativeButton("cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialogBox, int id) {
//                                dialogBox.cancel();
//                            }
//                        });
//
//        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
//        alertDialog.show();
//
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Show toast message when no text is entered
//
//                if (TextUtils.isEmpty(inputNote.getText().toString())) {
//                    Toast.makeText(EventActivity.this, "Enter note!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if (TextUtils.isEmpty(eventDateTextView.getText().toString())) {
//                    Toast.makeText(EventActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    alertDialog.dismiss();
//                }
//
//                // check if user updating note
//                if (shouldUpdate && event != null) {
//                    // update note by it's id
//                    updateNote(inputNote.getText().toString(),eventDateTextView.getText().toString(), position);
//                } else {
//                    // create new note
//                    createNote(inputNote.getText().toString(),eventDateTextView.getText().toString(),number);
//                }
//            }
//        });
//    }


    /**
     * Inserting new note in db
     * and refreshing the list
     */
//    private void createNote(String event,String date,String number) {
//        // inserting note in db and getting
//        // newly inserted note id
//        long id = db.insertNote(event,date,number);
//
//        // get the newly inserted note from db
//        Event n = db.getEvent(id);
//
//        if (n != null) {
//            // adding new note to array list at 0 position
//            eventList.add(0, n);
//
//            // refreshing the list
//            eventAdapter.notifyDataSetChanged();
//
//            toggleEmptyNotes();
//        }
//    }
//
//    /**
//     * Updating note in db and updating
//     * item in the list by its position
//     */
//    private void updateNote(String event,String date,int position) {
//        Event n = eventList.get(position);
//        // updating note text
//        n.setEvent(event);
//        n.setTimestamp(date);
//
//        // updating note in db
//        db.updateNote(n);
//
//        // refreshing the list
//        eventList.set(position, n);
//        eventAdapter.notifyItemChanged(position);
//
//        toggleEmptyNotes();
//    }
//
//    /**
//     * Deleting note from SQLite and removing the
//     * item from the list by its position
//     */
//    private void deleteNote(int position) {
////        // deleting the note from db
//        db.deleteNote(eventList.get(position));
//
//        // removing the note from the list
//        eventList.remove(position);
//        eventAdapter.notifyItemRemoved(position);
//
//        toggleEmptyNotes();
//    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
//    private void showActionsDialog(final int position) {
//        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Choose option");
//        builder.setItems(colors, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//                    showNoteDialog(true, eventList.get(position), position);
//                } else {
//                    deleteNote(position);
//                }
//            }
//        });
//        builder.show();
//    }
//
//    /**
//     * Toggling list and empty notes view
//     */
//    private void toggleEmptyNotes() {
//        // you can check notesList.size() > 0
//
//        if (db.getNotesCount() > 0) {
//            noEventView.setVisibility(View.GONE);
//        } else {
//            noEventView.setVisibility(View.VISIBLE);
//        }
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        Intent intent = new Intent(EventActivity.this,ContactProfileActivity.class);
//        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();   //"MenuInflater" is a service which coverts xml file into java file
        menuInflater.inflate(R.menu.menu_layout,menu);    //"Inflate" method turns the xml file into java file and pass the menu class object

        MenuItem menuItem = menu.findItem(R.id.searchViewId);        //Find MenuItem which is a searchView. Because searchView is in the menuItem
        SearchView searchView = (SearchView) menuItem.getActionView();   //The menuItem which we get that is kept in the searchView variable  and in the xml file we set the searchView as a menuItem using actionViewClass .So we have to get the ActionView by the menuItem

        searchView.setQueryHint("Search an Event Name");

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
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    private void getPlanets(String searchTerm) {

        eventDates.clear();

        myDatabaseHelper2 = new MyDatabaseHelper(this);

        EventDate eventDate = null;

        cursor2 = myDatabaseHelper2.retrieve3(searchTerm);

        if(cursor2.getCount() == 0)          //now we have to check how many no of rows are in the cursor
        {
            //there is no data so we will diplay message
            showData("Error",searchTerm+" is not found in the list.");
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
                name = cursor2.getString(1);
                number = cursor2.getString(2);

//                bloodSearch1 = new BloodSearch();
//
//                bloodSearch1.setName(name);
//                bloodSearch1.setNumber(number);
//                bloodSearch1.setBlood(blood);
//
//                bloodSearches.add(bloodSearch1);

                eventDates.add(new EventDate(name,number));
            }

            EventListView.setAdapter(eventDateCustomAdapter);

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

            Intent intent = new Intent(EventActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.emergenyList) {

            Intent intent = new Intent(EventActivity.this,EmergencyListActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.emergenySms) {

            Intent intent = new Intent(EventActivity.this,EmergencySmsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactList) {

            Intent intent = new Intent(EventActivity.this,ContactAllActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupCreate) {
            Intent intent = new Intent(EventActivity.this,GroupCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.groupContacts) {
            Intent intent = new Intent(EventActivity.this,GroupContactsActivity.class);
            startActivity(intent);

        } else if (id == R.id.sendSms) {

            Intent intent = new Intent(EventActivity.this,SendSmsActivity.class);
            startActivity(intent);

        }else if (id == R.id.reminder) {

            Intent intent = new Intent(EventActivity.this,ReminderActivity.class);
            startActivity(intent);

        }else if (id == R.id.blood) {

            Intent intent = new Intent(EventActivity.this,BloodSearchActivity.class);
            startActivity(intent);

        }else if (id == R.id.settings) {
            Intent intent = new Intent(EventActivity.this,MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.comments) {

        }else if (id == R.id.aboutUs) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
