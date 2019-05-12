package com.example.bluetooth_test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button2);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button3 = (Button) findViewById(R.id.button3);

        button.setOnClickListener(this);
        button5.setOnClickListener(this);
        button3.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
           switch (v.getId()){
            case R.id.button2:
                Intent intent;
                intent = new Intent(MainActivity.this, Secondactivity.class);
                startActivity(intent); // startActivity allow you to move
                break;
               /*case R.id.button_PT:
                   Intent intent1;
                   intent1 = new Intent(MainActivity.this, LeaugeStandings.class);
                   startActivity(intent1); // startActivity allow you to move
                   break;*/
               case R.id.button3:
                   Intent intent2;
                   intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dublingaa.ie/competitions"));
                   startActivity(intent2); // startActivity allow you to move
                   break;
               case R.id.button5:
                   Intent intent1;
                   intent1 = new Intent(MainActivity.this, DataReports.class);
                   startActivity(intent1); // startActivity allow you to move
                   break;
           }
    }
}



           /* @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, Secondactivity.class);
                startActivity(intent); // startActivity allow you to move
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2;
                intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.ie"));
                startActivity(intent2); // startActivity allow you to move
            }
        });*/

        /*button_PT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                intent1 = new Intent(MainActivity.this, Player_Tracking.class);
                startActivity(intent1); // startActivity allow you to move
            }
        });*/

