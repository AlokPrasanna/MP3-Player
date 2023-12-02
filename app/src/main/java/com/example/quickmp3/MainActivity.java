package com.example.quickmp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noSongsFoundTextView;
    ArrayList<AudioModel> audioModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.songs_list);
        noSongsFoundTextView = findViewById(R.id.empty_songs_msg);

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        if(checkAccessibility() == false){
            requestPermission();
            return;
        }

        // Get All Songs fro external storage
        String[] projection = {
             MediaStore.Audio.Media.TITLE,
             MediaStore.Audio.Media.DATA,
             MediaStore.Audio.Media.DURATION,
             MediaStore.Audio.Media.ARTIST,
             MediaStore.Audio.Media.ALBUM
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";

        Cursor cursor = getContentResolver().query(uri,projection,selection,null,null);
        while (cursor.moveToNext()){
            AudioModel audioModel = new AudioModel(cursor.getString(0),cursor.getString(3),cursor.getString(4),cursor.getString(2),cursor.getString(1));
            if(new File(audioModel.getPath()).exists()){
                audioModelArrayList.add(audioModel);
            }
        }
        if(audioModelArrayList.size() == 0){
            noSongsFoundTextView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new AudioListAdapter(audioModelArrayList,getApplicationContext()));
        }
    }

    boolean checkAccessibility() {
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
       if(check == PackageManager.PERMISSION_GRANTED){
           return true;
       }else {
           return false;
       }
    }
   void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Read Permission Is Required! Please Allow To Access The External Storage.",Toast.LENGTH_SHORT);
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},00);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView != null){
            recyclerView.setAdapter(new AudioListAdapter(audioModelArrayList,getApplicationContext()));
        }
    }
}