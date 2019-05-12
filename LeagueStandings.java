package com.example.bluetooth_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOError;
import java.io.IOException;

public class LeagueStandings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_standings);

        try {
            Document doc = (Document) Jsoup.connect("https://www.dublingaa.ie/competitions");
        }catch(IOError e){
            e.printStackTrace();
        }
    }
}
