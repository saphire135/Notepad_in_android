package com.fareye.divyanshu.notepad.ShowNotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fareye.divyanshu.notepad.Database.StoreValues;
import com.fareye.divyanshu.notepad.R;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Notes extends AppCompatActivity {

    Button addaNote;
    EditText password;
    ListView listView;
    StoreValues storeValues;
    ArrayList<String> listOfFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listview);
        addaNote = (Button) findViewById(R.id.button);
        password = (EditText) findViewById(R.id.editText3);

        storeValues = new StoreValues(Notes.this);

        Cursor cursor = storeValues.getAllNotes();
        listOfFiles = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                String getTitle = cursor.getString(cursor.getColumnIndex("title"));
                Log.d("Print table",getTitle);
                listOfFiles.add(getTitle);
            }while(cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listOfFiles);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Notes.this, YourNote.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String entry = (String) parent.getItemAtPosition(position);
                Log.d("ListView",entry);
                intent.putExtra(EXTRA_MESSAGE, entry);
                startActivity(intent);
            }
        });

        addaNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals("123456")) {
                    Log.d("AddaNote is working", "going to yournote class");
                    Intent intent = new Intent(Notes.this, YourNote.class);
                    intent.putExtra(EXTRA_MESSAGE, "");
                    startActivity(intent);
                }
            }
        });
    }
}