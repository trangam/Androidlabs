package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private EditText editText;
    private Button send, receive;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        send = (Button) findViewById(R.id.send);
        receive=(Button)findViewById(R.id.receive);
        editText = (EditText) findViewById(R.id.editText4);
        listView = (ListView) findViewById(R.id.messages_view);
        ArrayList<myMessage> list = new ArrayList<>();
        MyListAdapter myAdapter = new MyListAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(myAdapter);
        send.setOnClickListener(e -> {
            list.add(new myMessage(R.layout.send_layout, editText.getText().toString()));
            myAdapter.notifyDataSetChanged();
            editText.getText().clear();
        });
        receive.setOnClickListener(e->{
            list.add(new myMessage(R.layout.receive_layout, editText.getText().toString()));
            myAdapter.notifyDataSetChanged();
            editText.getText().clear();
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                myMessage message=(myMessage)listView.getAdapter().getItem(position);
                AlertDialog.Builder alert= new AlertDialog.Builder(listView.getContext());
                alert.setTitle(getText(R.string.delete));
                alert.setMessage(getText(R.string.row_selected)+" "+ message.getMessage()+"\n" +getText(R.string.database_id)+" "+listView.getAdapter().getItemId(position) +"\n");
                alert.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(ChatRoomActivity.this, getText(R.string.yes_message), Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ChatRoomActivity.this, getText(R.string.no_message), Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNeutralButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ChatRoomActivity.this, getText(R.string.no_message), Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog=alert.create();
                alertDialog.show();
                return listView.isLongClickable();
            }

        });
    }

    private class myMessage {
        private int layout;
        private String textMess;

        private myMessage(int layout, String textMess) {
            this.layout = layout;
            this.textMess = textMess;
        }

        private String getMessage() {
            return textMess;
        }

        private int getLayout() {
            return layout;
        }
    }

    private class MyListAdapter extends ArrayAdapter {
        public MyListAdapter(Context context, int resource, List<myMessage> list) {
            super(context, resource, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myMessage myMess = (myMessage) super.getItem(position);
            convertView = inflater.inflate(myMess.getLayout(), null);
            TextView anything = (TextView) convertView.findViewById(R.id.message_body);
            anything.setText(myMess.getMessage());
            return convertView;
        }
    }

}
