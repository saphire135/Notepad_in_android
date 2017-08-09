package com.fareye.divyanshu.notepad.ShowNotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_note);
            Intent intent = getIntent();

        title = (EditText) findViewById(R.id.editText2);
        body = (EditText) findViewById(R.id.editText);

        String extraTxt = intent.getStringExtra(EXTRA_MESSAGE).toString();
        if(!extraTxt.isEmpty()) {
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
                    Log.d("Decrypted text",text.toString());
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }
            Log.d("Decrypted",text.toString());
            String decryptedBodyText = encryptDecrypt.decrypt(text.toString());
            Log.d("After Decryption",decryptedBodyText);

            title.setText(extraText, TextView.BufferType.EDITABLE);
            body.setText(decryptedBodyText, TextView.BufferType.EDITABLE);
        }



        save = (Button) findViewById(R.id.button2);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String fileName = title.getText().toString();
                String bodyText = body.getText().toString();

                if(!(fileName.isEmpty())) {
                    String encryptedBodyText = encryptDecrypt.encrypt(bodyText);

                    generateNote(fileName, encryptedBodyText);
                    Log.d("savebutton is working", "to save file");
//                startActivity(new Intent(Notes.this, YourNote.class));
                    startActivity(new Intent(YourNote.this, Notes.class));
                }
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