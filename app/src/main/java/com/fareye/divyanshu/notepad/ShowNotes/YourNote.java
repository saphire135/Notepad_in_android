package com.fareye.divyanshu.notepad.ShowNotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fareye.divyanshu.notepad.Database.StoreValues;
import com.fareye.divyanshu.notepad.R;
import com.fareye.divyanshu.notepad.encryptDecrypt.EncryptDecrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class YourNote extends AppCompatActivity {

    EditText title;
    EditText body;
    Button save;
    StoreValues storeValues;
    Button b;
    Button i;
    Button bi;

    EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_note);
        Intent intent = getIntent();

        title = (EditText) findViewById(R.id.editText2);
        body = (EditText) findViewById(R.id.editText);


        String extraTxt = intent.getStringExtra(EXTRA_MESSAGE).toString();
        if (!extraTxt.isEmpty()) {
            String extraText = extraTxt.substring(0, extraTxt.length() - 4);
            Log.d(extraText, extraTxt);
            File path = new File(Environment.getExternalStorageDirectory(), "Notes");
            File file = new File(path, extraTxt);

            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                    Log.d("Decrypted text", text.toString());
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }
            Log.d("Decrypted", text.toString());
            String decryptedBodyText = encryptDecrypt.decrypt(text.toString());
            Log.d("After Decryption", decryptedBodyText);

            title.setText(extraText, TextView.BufferType.EDITABLE);
            body.setText(decryptedBodyText, TextView.BufferType.EDITABLE);
        }
        b =  (Button) findViewById(R.id.button3);
        i =  (Button) findViewById(R.id.button4);
        bi =  (Button) findViewById(R.id.button5);

        save = (Button) findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String field = body.getText().toString();
                if(!(field.isEmpty())){
                    String sourceString = "<b>" + field + "</b> ";
                    body.setText(Html.fromHtml(sourceString));
                }
            }
        });

        i.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String field = body.getText().toString();
                if(!(field.isEmpty())){
                    String sourceString = "<i>" + field + "</i> ";
                    body.setText(Html.fromHtml(sourceString));
                }
            }
        });

        bi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String field = body.getText().toString();
                if(!(field.isEmpty())){
                    String sourceString = "<i><b>" + field + "</b></i> ";
                    body.setText(Html.fromHtml(sourceString));
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String fileName = title.getText().toString();
                String bodyText = body.getText().toString();

                if (!(fileName.isEmpty())) {
                    String encryptedBodyText = encryptDecrypt.encrypt(bodyText);
                    Log.d("Encrypted",encryptedBodyText);

                    int sizeOfFile = (int) generateNote(fileName, encryptedBodyText);
                    Log.d("savebutton is working", "to save file");

                    storeValues = new StoreValues(YourNote.this);
                    boolean insert = storeValues.insertNotes(fileName,sizeOfFile);

                    Cursor cursor = storeValues.getAllNotes();

                    if (cursor.moveToFirst()){
                        do{
                            String getTitle = cursor.getString(cursor.getColumnIndex("title"));
                            Log.d("Print table",getTitle);

                            String getCreatedon = cursor.getString(cursor.getColumnIndex("createdon"));
                            Log.d("Print table",getCreatedon);

                            String getsize = cursor.getString(cursor.getColumnIndex("size"));
                            Log.d("Print table",getsize);
                        }while(cursor.moveToNext());
                    }
                    cursor.close();

                    Log.d("Print table",cursor.toString());

                    startActivity(new Intent(YourNote.this, Notes.class));
                }
            }
        });

    }

    public long generateNote(String sFileName, String sBody) {

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File textfile = new File(root, sFileName + ".txt");

            FileWriter writer = new FileWriter(textfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            long sizeOfFile = textfile.length();
            return  sizeOfFile;
//            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            return 10;
        }
    }



}