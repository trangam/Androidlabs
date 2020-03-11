package com.example.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private ListView listView;
    private Button send;
    private Button receive;
    private EditText editText;
    private DatabaseMessage helper = new DatabaseMessage(this);
    private ContentValues value = new ContentValues();
    private Cursor cursor;
    private SQLiteDatabase sql;
    private FrameLayout frameLayout;
    public static final String ITEM_MESSAGE = "MESSAGE";
    public static final String ITEM_ID = "ID";
    public static final String IS_SEND = "IS_SEND";
    class ChatAdapter extends BaseAdapter {
        private List<Message> list;
        private int sendOrReceive;
        private Context context;


        public ChatAdapter(Context context, int layout, List<Message> list) {
            this.list = list;
            this.sendOrReceive = layout;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Message getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(getItem(position).getSendOrReceive(), null);
            TextView message = (TextView) convertView.findViewById(R.id.message_body);
            message.setText(getItem(position).getMessage());

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        listView = (ListView) findViewById(R.id.messages_view);
        send = (Button) findViewById(R.id.send);
        receive = (Button) findViewById(R.id.receive);
        editText = (EditText) findViewById(R.id.editText4);
        sql = helper.getWritableDatabase();
      //  helper.onUpgrade(sql,1, 1);

        ArrayList<Message> arrayList = new ArrayList<>();
        ChatAdapter arrayAdapter = new ChatAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        cursor = sql.rawQuery("Select * from Contacts", null);
        printCursor(cursor, sql);
        for (int i = 0; i < cursor.getCount(); i++) {
            arrayList.add(new Message(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        arrayAdapter.notifyDataSetChanged();

        send.setOnClickListener(e -> {
            value.put(DatabaseMessage.COL_MESSAGE,editText.getText().toString());
            value.put(DatabaseMessage.COL_SEND_OR_RECEIVE,R.layout.send_layout);
            long id = sql.insert(DatabaseMessage.TABLE_NAME,null,value);

            arrayList.add(new Message(id, editText.getText().toString(), R.layout.send_layout));
            arrayAdapter.notifyDataSetChanged();
            editText.getText().clear();
        });
        receive.setOnClickListener(e -> {
            value.put(DatabaseMessage.COL_MESSAGE,editText.getText().toString());
            value.put(DatabaseMessage.COL_SEND_OR_RECEIVE,R.layout.receive_layout);

            long id = sql.insert(DatabaseMessage.TABLE_NAME,null,value);

            arrayList.add(new Message(id, editText.getText().toString(), R.layout.receive_layout));
            arrayAdapter.notifyDataSetChanged();
            editText.getText().clear();
        });

      frameLayout=findViewById(R.id.fragmentLocation);
        boolean isTablet = frameLayout!= null;
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            Message message = (Message) parent.getAdapter().getItem(position);

            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_MESSAGE,message.getMessage() );
            dataToPass.putLong(ITEM_ID, message.getId());
            dataToPass.putBoolean(IS_SEND, message.getSendOrReceive() == R.layout.send_layout);
            if(isTablet)
            {
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment.
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }

        });
        listView.setOnItemLongClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            Message message = (Message)  parent.getAdapter().getItem(position);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.row_selected) + " " + position + "\n" +
                            getString(R.string.database_id) + " " + id)
                    .setPositiveButton(R.string.yes, (DialogInterface dialog, int which) -> {
                        sql.delete(DatabaseMessage.TABLE_NAME, DatabaseMessage.COL_ID + "=" + message.getId(), null);
                        arrayList.remove(position);
            for(Fragment fragment: getSupportFragmentManager().getFragments()){
                if(fragment.getArguments().getLong(ITEM_ID)==id){
                   getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
}
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(ChatRoomActivity.this, getString(R.string.yes_message), Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton(R.string.no, (DialogInterface dialog, int which)->{
                        Toast.makeText(ChatRoomActivity.this, getString(R.string.no_message), Toast.LENGTH_LONG).show();});

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();

            return listView.isLongClickable();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void printCursor(Cursor c, SQLiteDatabase sql){
        c.moveToNext();
        String results = "";
        for(int i = 0; i < c.getCount(); i++){
            results = results + "Row " + (i+1) + ": id: " + c.getInt(0) +
                    ", Message: " + c.getString(1) + ", Layout Id: " + c.getInt(2) + ".\n";
            c.moveToNext();
        }

        Log.i("Database_information", "\nDatabase version number: " + sql.getVersion()
                + ".\nNumber of columns: " + c.getColumnCount()+ ".\nColumns name: "
                + c.getColumnName(0) + ", " + c.getColumnName(1) + ", "
                + c.getColumnName(2) + ".\nResults:\n" + results);
        c.moveToFirst();
    }


    public class DatabaseMessage extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "DatabaseFile",
                TABLE_NAME = "Contacts",
                COL_ID = "id",
                COL_MESSAGE = "MESSAGE",
                COL_SEND_OR_RECEIVE = "sendOrReceive";


        public static final int VERSION_NUM = 1;


        public DatabaseMessage(Activity ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_MESSAGE + " text, "
                    + COL_SEND_OR_RECEIVE + " INTEGER)");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
        }
    }
}

