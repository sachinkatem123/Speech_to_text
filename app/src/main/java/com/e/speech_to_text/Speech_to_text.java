package com.e.speech_to_text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Sampler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Speech_to_text extends AppCompatActivity {

    Button openingmic,save;
    int STROAGE_PERMISSION_CODE=23;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String timesysa;


    TextView output;
    private  static final int REQ_CODE_SPEECH_INPUT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openingmic = findViewById(R.id.openingmic);
        save=findViewById(R.id.button);
        output = findViewById(R.id.output);

        openingmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                talkslowly();
            }
        });
    }


    private void talkslowly() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //to caputre additional langugage(extralangmodel)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //extra language also supported ....local getdefault it will alwys go for default lang
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hii speak something");
        //

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

        } catch (ActivityNotFoundException a) {
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch ( requestCode) {
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK&&null !=data){
  //        getting result from recorgizer intent and storing in them in a array of string called as result
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //textview is (output) store in array
                    output.setText(result.get(0));
                    writedatainfile(output.getText().toString());


                }
                break;
            }
        }
    }

    private void writedatainfile(String text) {

        calendar = calendar.getInstance();
        simpleDateFormat =new SimpleDateFormat("DD-MM-YYYY HH:mm:ss");
       // timesysa = simpleDateFormat.format(calendar.get());
        timesysa="ExternalData"+timesysa+".txt";
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STROAGE_PERMISSION_CODE);
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myfile=new File(folder,timesysa);
        writeData(myfile,text);




    }

    private void writeData(File myfile, String text) {
        FileOutputStream fileOutputStream =null;
        try {
            fileOutputStream =new FileOutputStream(myfile);
            String result = null;
            fileOutputStream.write(result.getBytes());
            Toast.makeText(this, "Done"+myfile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();

                }catch (IOException e){
                    e.printStackTrace();
                }

            }

        }


    }
}


