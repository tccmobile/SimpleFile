package com.example.drwillsmith.simplefile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    
    
    EditText enterText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        enterText = (EditText) findViewById(R.id.editText);
        saveButton = (Button) findViewById(R.id.saveButton);
        
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterText.getText().toString().equals("")){
                    String data=enterText.getText().toString();
                    writeToFile(data);
                } else {
                    Toast.makeText(getApplicationContext(),"Please enter text for your file.",Toast.LENGTH_LONG).show();
                }
            }
        });
        
        if (readFromFile()!=null){
            enterText.setText(readFromFile());
        } else {
            enterText.setText("");
        }
        
    }

    private String readFromFile() {
        String result = "";

        try{
            InputStream inputStream =  openFileInput("myFile.txt");

            if (inputStream!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((tempString =bufferedReader.readLine())!=null){
                    stringBuilder.append(tempString);
                }
                inputStream.close();
                result=stringBuilder.toString();
            }

        }catch(FileNotFoundException e){
            Log.v("MyActivity","File Not Found"+e.toString());
        }catch(IOException e){
            e.printStackTrace();
        }

        return result;
    }

    private void writeToFile(String data) {

        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("myFile.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        }catch(IOException e){
            Log.v("MyActivity", e.toString());
        }
    }
}
