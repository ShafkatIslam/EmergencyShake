package com.example.shafkat.emergencyshake.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.shafkat.emergencyshake.ContactAll.Event;
import com.example.shafkat.emergencyshake.Group.GroupCreateActivity;
import com.example.shafkat.emergencyshake.Group.GroupTable;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

//    public static GroupTable groupTable = new GroupTable();
//
//    public static String newTableName = groupTable.groupName;

    public static final String DATABASE_NAME ="contacts.db";
    public static final String TABLE_NAME ="contact_details";
    public static final String TABLE_NAME1 ="friends_details";
    public static final String TABLE_NAME2 ="emergency_list";
    public static final String TABLE_NAME3 ="person_details";
    public static final String TABLE_NAME4 ="group_name";
    public static final String TABLE_NAME5 = "notes";
    public static final String TABLE_NAME6 = "persons";
    public static final String TABLE_NAME7 = "emergency_sms";
    //public static final String TABLE_NAME5 = newTableName;

    public static final String NAME ="Name";
    public static final String PHONE ="Phone";
    public static final String IMAGE ="Image";
    public static final String GROUP = "GName";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "times";
    public static final String NUMBER = "number";

    public static final String PID = "Pid";
    public static final String PNAME = "Pname";
    public static final String PNUMBERA = "PnumberA";
    public static final String PNUMBERB = "PnumberB";
    public static final String PNUMBERC = "PnumberC";
    public static final String PEMAIL = "Pemail";
    public static final String PBGROUP = "Pbgroup";
    public static final String PIMAGE = "Pimage";

    public static final String SID = "Sid";
    public static final String SMS = "Sms";

    public static  final int VERSION_NUMBER =3;

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" (" +NAME+ " VARCHAR(255) NOT NULL, " +PHONE+" TEXT NOT NULL);";
    public static final String CREATE_TABLE1 = "CREATE TABLE "+TABLE_NAME1+" (" +NAME+ " VARCHAR(255) NOT NULL, " +PHONE+" TEXT NOT NULL);";
    public static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAME2+" (" +NAME+ " VARCHAR(255) NOT NULL, " +PHONE+" TEXT NOT NULL);";
    public static final String CREATE_TABLE3 = "CREATE TABLE "+TABLE_NAME3+" (" +NAME+ " VARCHAR(255) NOT NULL, " +PHONE+" TEXT NOT NULL, " +IMAGE+" VARCHAR(255) NOT NULL);";
    public static final String CREATE_TABLE4 = "CREATE TABLE "+TABLE_NAME4+" (" +GROUP+ " VARCHAR(255) NOT NULL);";
    public static final String CREATE_TABLE6 = "CREATE TABLE "+TABLE_NAME6+" (" +PID+ " INTEGER NOT NULL, " +PNAME+ " VARCHAR(255) NOT NULL, " +PNUMBERA+" TEXT NOT NULL, " +PNUMBERB+" TEXT NOT NULL, " +PNUMBERC+" TEXT NOT NULL, " +PEMAIL+" TEXT NOT NULL, " +PBGROUP+" TEXT NOT NULL, " +PIMAGE+" VARCHAR(255) NOT NULL);";
    public static final String CREATE_TABLE7 = "CREATE TABLE "+TABLE_NAME7+" (" +SID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +SMS+" TEXT"+ ")";
    //public static final String CREATE_TABLE5 = "CREATE TABLE "+TABLE_NAME5+" (" +NAME+ " VARCHAR(255) NOT NULL, " +PHONE+" TEXT NOT NULL);";
    // Create table SQL query
    public static final String CREATE_TABLE5 =
            "CREATE TABLE " + TABLE_NAME5 + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_TIMESTAMP + " TEXT,"
                    + NUMBER + " TEXT,"
                    + NAME + " TEXT"
                    + ")";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    public static final String DROP_TABLE1 = "DROP TABLE IF EXISTS "+ TABLE_NAME1;
    public static final String DROP_TABLE2 = "DROP TABLE IF EXISTS "+ TABLE_NAME2;
    public static final String DROP_TABLE3 = "DROP TABLE IF EXISTS "+ TABLE_NAME3;
    public static final String DROP_TABLE4 = "DROP TABLE IF EXISTS "+ TABLE_NAME4;
    public static final String DROP_TABLE5 = "DROP TABLE IF EXISTS "+ TABLE_NAME5;
    public static final String DROP_TABLE6 = "DROP TABLE IF EXISTS "+ TABLE_NAME6;
    public static final String DROP_TABLE7 = "DROP TABLE IF EXISTS "+ TABLE_NAME7;

    public static final String SELECT_ALL = "SELECT * FROM "+ TABLE_NAME;
    public static final String SELECT_ALL1 = "SELECT * FROM "+ TABLE_NAME1;
    public static final String SELECT_ALL2 = "SELECT * FROM "+ TABLE_NAME2;
    public static final String SELECT_ALL3 = "SELECT * FROM "+ TABLE_NAME3;
    public static final String SELECT_ALL4 = "SELECT * FROM "+ TABLE_NAME4;
    //public static final String SELECT_ALL5 = "SELECT * FROM "+ TABLE_NAME5;
    public static final String SELECT_ALL6 = "SELECT * FROM "+ TABLE_NAME5;
    public static final String SELECT_ALL7 = "SELECT * FROM "+ TABLE_NAME6;
    public static final String SELECT_ALL8 = "SELECT * FROM "+ TABLE_NAME7;





    private Context context;
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            Toast.makeText(context,"onCreate is called",Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
            db.execSQL(CREATE_TABLE3);
            db.execSQL(CREATE_TABLE4);
            db.execSQL(CREATE_TABLE5);
            db.execSQL(CREATE_TABLE6);
            db.execSQL(CREATE_TABLE7);

        }catch (Exception e)
        {
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            db.execSQL(DROP_TABLE1);
            db.execSQL(DROP_TABLE2);
            db.execSQL(DROP_TABLE3);
            db.execSQL(DROP_TABLE4);
            db.execSQL(DROP_TABLE5);
            db.execSQL(DROP_TABLE6);
            db.execSQL(DROP_TABLE7);
            onCreate(db);
        } catch(Exception e)
        {
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_SHORT).show();
        }
    }

    public long insertNote(String note,String times,String number,String name) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_NOTE, note);
        values.put(COLUMN_TIMESTAMP, times);
        values.put(NUMBER, number);
        values.put(NAME, name);

        // insert row
        long id = db.insert(TABLE_NAME5, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Cursor showAllEvent(String phone)  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery("select * from notes where number='"+phone+"' order by times ASC",null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    public Cursor reminderEvent()
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        String query="SELECT * from "+TABLE_NAME5 +" WHERE "+ COLUMN_TIMESTAMP +" <= date('now','localtime', '+7 day') AND "+ COLUMN_TIMESTAMP +" >=  date('now','localtime', '0 day') order by times ASC";

        Cursor cursor = db.rawQuery(query,null);

        return  cursor;
    }

    public void DeleteEvent(String EventName,String EventDate,String Number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM notes WHERE note='"+EventName+"'"+" AND times='"+EventDate+"'"+" AND number='"+Number+"'");
    }

    public void updateEvent(String EventName,String EventDate,String Number,int id)
    {
        SQLiteDatabase ourDatabase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTE, EventName);
        cv.put(COLUMN_TIMESTAMP, EventDate);
//        long rowId=
        ourDatabase.update(TABLE_NAME5, cv, NUMBER + "= ? AND " +COLUMN_ID + " = ?", new String[] {Number,String.valueOf(id)});
//        return rowId;
    }

//    public void deleteEvent(String EventName,String EventDate,String Number){
//        SQLiteDatabase data = this.getWritableDatabase();
//        //String whereClause = "note=? AND times =? AND number =?" ;
//        //String whereArgs[] = new String[] {EventName, EventDate, Number};
//
//        data.delete(TABLE_NAME5, "note=? AND times=? AND number=?", new String[] {EventName, EventDate, Number});
//        data.close();
//
//    }

    public Event getEvent(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME5,
                new String[]{COLUMN_ID,COLUMN_NOTE,COLUMN_TIMESTAMP},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Event event = new Event(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return event;
    }

    public List<Event> getAllEvents(String phone) {
        List<Event> events = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME5 + " WHERE "+ NUMBER +" = "+phone+" ORDER BY " +
                COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                event.setEvent(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                event.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

                events.add(event);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return events;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME5;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, event.getEvent());
        values.put(COLUMN_TIMESTAMP,event.getTimestamp());

        // updating row
        return db.update(TABLE_NAME5, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    public void deleteNote(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME5, COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }

    public void AddDesiredTable(String TableName)
    {
        SQLiteDatabase ourDatabase=this.getWritableDatabase();

        ourDatabase.execSQL("CREATE TABLE "+TableName+" (" +NAME+ " VARCHAR(255) NOT NULL, " +PHONE+" TEXT NOT NULL);");
    }

    public long insertData5(String TableName,String name,String phoneNumber)
    {
        SQLiteDatabase ourDatabase = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phoneNumber);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= ourDatabase.insert(TableName,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    //to create a method to insert the data and here is passing UserDetails userDetails object as a parameter
    public long insertData6(int id,String name,String phoneNumberA,String phoneNumberB,String phoneNumberC,String email,String bloodGroup,byte[] image)
//    public long insertData3(String name,String phoneNumber,String image)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(PID,id);
        contentValues.put(PNAME,name);
        contentValues.put(PNUMBERA,phoneNumberA);
        contentValues.put(PNUMBERB,phoneNumberB);
        contentValues.put(PNUMBERC,phoneNumberC);
        contentValues.put(PEMAIL,email);
        contentValues.put(PBGROUP,bloodGroup);
        contentValues.put(PIMAGE,image);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME6,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    public Cursor showAllData6(String number)  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        //Cursor cursor = db.rawQuery(SELECT_ALL7 +" WHERE PnumberA LIKE '"+number+"'"+" AND Pid LIKE '"+id+"'",null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL7 +" WHERE PnumberA LIKE '"+number+"'",null);
        return cursor;

//        db.execSQL("DELETE FROM notes WHERE note='"+EventName+"'"+" AND times='"+EventDate+"'"+" AND number='"+Number+"'");
    }

    public Cursor showAllDataBlood()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        //Cursor cursor = db.rawQuery(SELECT_ALL7 +" WHERE PnumberA LIKE '"+number+"'"+" AND Pid LIKE '"+id+"'",null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL7,null);
        return cursor;

//        db.execSQL("DELETE FROM notes WHERE note='"+EventName+"'"+" AND times='"+EventDate+"'"+" AND number='"+Number+"'");
    }

    public Cursor retrieve(String searchTerm)
    {
        String[] columns={PID,PNAME,PNUMBERA,PNUMBERB,PNUMBERC,PEMAIL,PBGROUP,PIMAGE};
        Cursor c=null;

        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TABLE_NAME6+" WHERE "+PBGROUP+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;

        }

        c=db.query(TABLE_NAME6,columns,null,null,null,null,null);
        return c;
    }

    public Cursor retrieve1(String searchTerm)
    {
        String[] columns={NAME,PHONE};
        Cursor c=null;

        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TABLE_NAME+" WHERE "+NAME+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;

        }

        c=db.query(TABLE_NAME,columns,null,null,null,null,null);
        return c;
    }

    public Cursor retrieve2(String searchTerm)
    {
        String[] columns={NAME,PHONE};
        Cursor c=null;

        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TABLE_NAME2+" WHERE "+NAME+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;

        }

        c=db.query(TABLE_NAME2,columns,null,null,null,null,null);
        return c;
    }

    public Cursor retrieve3(String searchTerm)
    {
        String[] columns={COLUMN_ID,COLUMN_NOTE,COLUMN_TIMESTAMP,NUMBER,NAME};
        Cursor c=null;

        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TABLE_NAME5+" WHERE "+COLUMN_NOTE+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;

        }

        c=db.query(TABLE_NAME5,columns,null,null,null,null,null);
        return c;
    }

    public Cursor retrieve4(String searchTerm)
    {
        String[] columns={GROUP};
        Cursor c=null;

        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TABLE_NAME4+" WHERE "+GROUP+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;

        }

        c=db.query(TABLE_NAME4,columns,null,null,null,null,null);
        return c;
    }

    public void updateContactData(int id,String name,String phoneNumberA,String phoneNumberB,String phoneNumberC,String email,String bloodGroup,byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(PID,id);
        contentValues.put(PNAME,name);
        contentValues.put(PNUMBERA,phoneNumberA);
        contentValues.put(PNUMBERB,phoneNumberB);
        contentValues.put(PNUMBERC,phoneNumberC);
        contentValues.put(PEMAIL,email);
        contentValues.put(PBGROUP,bloodGroup);
        contentValues.put(PIMAGE,image);
//        long rowId=
        db.update(TABLE_NAME6, contentValues, PNUMBERA + "= ?", new String[] {phoneNumberA});
//        return rowId;
    }

    public void updateData(String GroupName,String NewGroupName)
    {
        SQLiteDatabase ourDatabase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(GROUP, NewGroupName);
//        long rowId=
        ourDatabase.update(TABLE_NAME4, cv, GROUP + "= ?", new String[] {GroupName});
//        return rowId;
    }

    public int checkDataAvailablity(String TableName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM "+TableName;
        Cursor mcursor = db.rawQuery(count,null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount;
    }

    public void RenameTable(String TableName1,String TableName2)
    {
        SQLiteDatabase ourDatabase=this.getWritableDatabase();
        ourDatabase.execSQL("ALTER TABLE " + TableName1 + " RENAME TO " + TableName2);
    }

    public int deleteData5(String TableName) //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TableName, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

//    public int deleteTable(String TableName)
//    {
//        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
//        return db.delete(TableName, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type
//    }

    public void deleteTable(String TableName)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS "+TableName);
    }

//    public Cursor deleteTable(String TableName)
//    {
//        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
//        Cursor cursor = getReadableDatabase().rawQuery("DROP TABLE "+TableName, new String[] {TableName});
//        //return db.delete(TableName, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type
//        return cursor;
//    }

    public int deleteData6(String GroupName) //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TABLE_NAME4, GROUP+"=?", new String[]{GroupName});  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

    public int deleteData7(String number,String name) //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TABLE_NAME2, PHONE+"=? AND "+NAME+"=?" , new String[]{number,name});  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

//    public void updateData(String GroupName,String NewGroupName) //declare a method to delete data according to its primary key "id"
//    {
//        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
//        db.execSQL("UPDATE "+TABLE_NAME4+" SET "+GROUP+" = "+NewGroupName+" WHERE "+GROUP+" = "+GroupName);//delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type
//        //db.execSQL("UPDATE "+TABLE_NAME4+" SET "+GROUP+" = "+NewGroupName);//delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type
//    }

//    public Cursor updateData(String name, String updatename){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query="UPDATE group_name set name = ? where name = ?";
//        String[] selections={updatename, name};
//        Cursor cursor=db.rawQuery(query, selections);
//        return cursor;
//    }


    public Cursor showAllData5(String TableName)  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery("SELECT * FROM "+TableName+ " ORDER BY " +NAME+ " ASC ",null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    //to create a method to insert the data and here is passing UserDetails userDetails object as a parameter
    public long insertData(String name,String phoneNumber)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phoneNumber);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    public Cursor showAllData()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL+ " ORDER BY " +NAME+ " ASC ",null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    //to create a method to insert the data and here is passing UserDetails userDetails object as a parameter
    public long insertData1(String name,String phoneNumber)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phoneNumber);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME1,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    public Cursor showAllData1()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL1,null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    public long insertData2(String name,String phoneNumber)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phoneNumber);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME2,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    public Cursor showAllData2()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL2,null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    //to create a method to insert the data and here is passing UserDetails userDetails object as a parameter
    public long insertData3(String name,String phoneNumber,byte[] image)
//    public long insertData3(String name,String phoneNumber,String image)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phoneNumber);
        contentValues.put(IMAGE,image);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME3,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    public Cursor showAllData3()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL3,null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }


    //to create a method to insert the data and here is passing UserDetails userDetails object as a parameter
    public long insertData4(String group)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(GROUP,group);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME4,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type
        return rowId;
    }

    public long insertSms(String sms)
    {

        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        //to store all the data together we need to use ContentValues class.So we need to make an object of ContentValues
        ContentValues contentValues = new ContentValues();

        //we put the data one by one in the contentValues by using put method and there are two parameter(COLUMN_NAME,VALUE) in the put method
        contentValues.put(SMS,sms);

        //now we can insert the data into database finally
        //to insert the data into database we need to use db.insert() method
        long rowId= db.insert(TABLE_NAME7,null,contentValues);//insert method returns a id of a row if the row is successfully stored into database and the id is a long data type

        db.close();
        return rowId;
    }

    public void updateSms(String sms)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME7+ " SET "+sms +" = "+SMS+" WHERE "+SID+" = '1'");
    }

    public int deleteSms() //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TABLE_NAME7, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

    public Cursor showAllSms()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL8,null); //we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    public Cursor showAllData4()  //create a method to show all data which is a return type of "Cursor"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to access the data from in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase

        //While playng a query all the resultset will return and then to access/read/write it we have to use Cursor interface
        Cursor cursor = db.rawQuery(SELECT_ALL4+ " ORDER BY " +GROUP+ " ASC ",null);//we have to write a query by the help of database "db" and the rawQuery method of SQLiteDatabase returns a dataset or resultset and the resultset must be kept in a variable or interface and it is cursor interface
        return cursor;
    }

    public int deleteData() //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TABLE_NAME1, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

    public int deleteData2() //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TABLE_NAME2, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

    public int deleteData3() //declare a method to delete data according to its primary key "id"
    {
        SQLiteDatabase db = this.getWritableDatabase(); //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
        return db.delete(TABLE_NAME3, null, null);  //delete query has 3 parameters.(Table Name,for which id we have to delete the value,then declare a String Array and the id is passed into it because we will delete the data according to id and the total query has a integer return type

    }

    public boolean userNameAlreadyExits(String TABLE_NAME,String PHONE,String chek)
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase() ; //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
            Cursor cursor = db.rawQuery("SELECT "+PHONE+" FROM "+ TABLE_NAME +" WHERE "+ PHONE + " = '" + chek +"'" ,null);     //we have to play a query to find all the data and all data will keep in the cursor
            if(cursor.moveToFirst())
            {
                db.close();
                //Toast.makeText(context, "User Already Exists", Toast.LENGTH_SHORT).show();
                return true;  //record exists
            }
            db.close();


        }
        catch (Exception e)
        {
            Log.d("Exception occured","Exception occured"+e);
        }
        return false;
    }

    public boolean groupAlreadyExits(String TABLE_NAME4,String GROUP,String chek)
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase() ; //to write or read in database we need to help getWriteableDatabase method and it will return a SqLiteDatabase
            Cursor cursor = db.rawQuery("SELECT "+GROUP+" FROM "+ TABLE_NAME4 +" WHERE "+ GROUP + " = '" + chek +"'" ,null);     //we have to play a query to find all the data and all data will keep in the cursor
            if(cursor.moveToFirst())
            {
                db.close();
                //Toast.makeText(context, "User Already Exists", Toast.LENGTH_SHORT).show();
                return true;  //record exists
            }
            db.close();


        }
        catch (Exception e)
        {
            Log.d("Exception occured","Exception occured"+e);
        }
        return false;
    }

}

