package com.fareye.divyanshu.notepad.ShowNotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fareye.divyanshu.notepad.R;
import com.fareye.divyanshu.notepad.encryptDecrypt.EncryptDecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class YourNote extends AppCompatActivity {

    EditText title;
    EditText body;
    Button save;
    EncryptDecrypt encryptDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_note);

        title = (EditText) findViewById(R.id.editText2);
        body = (EditText) findViewById(R.id.editText);
        save = (Button) findViewById(R.id.button2);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String fileName = title.getText().toString();
                String bodyText = body.getText().toString();

                encryptDecrypt = new EncryptDecrypt();
                String encryptedBodyText = encryptDecrypt.encrypt(bodyText);

                generateNote(fileName, encryptedBodyText);
                Log.d("savebutton is working", "to save file");
//                startActivity(new Intent(Notes.this, YourNote.class));
                startActivity(new Intent(YourNote.this, Notes.class));

            }
        });

    }

    public void generateNote(String sFileName, String sBody) {

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File textfile = new File(root, sFileName+".txt");

            FileWriter writer = new FileWriter(textfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
//            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}