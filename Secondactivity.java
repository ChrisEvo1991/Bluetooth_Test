package com.example.bluetooth_test;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.text.DecimalFormat;

public class Secondactivity extends AppCompatActivity {

    // GUI Components
    private TextView mBluetoothStatus, mReadBuffer, mGoals_txt, mPoints_txt;
    private EditText mName_editTxt, mPosition_editTxt, mScore_editTxt;
    private Button mGoalsSuccess, mGoalsFail, mPointsSuccess, mPointsFail, mHandpassSuccess, mHandPassFail, mKickpassSuccess, mKickPassFail, mAdd_btn, mScanBtn, mListPairedDevicesBtn ;
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;
    private ListView mDevicesListView;

    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status
    private float goalsS, goalsF, goalsC, goalsPC;// variables for goals
    private float pointsS, pointsF, pointsC, pointsPC;// variables for points
    private float handpassS, handpassF, handpassC, handpassPC;// variables for handpasses
    private float kickpassS, kickpassF, kickpassC, kickpassPC;// variables for kickpasses
    private DecimalFormat df2 = new DecimalFormat("00%");// Deciaml format

    @SuppressLint({"HandlerLeak", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mName_editTxt = findViewById(R.id.name_editText);
        mPosition_editTxt = findViewById(R.id.position_editText2);
        mScore_editTxt = findViewById(R.id.goals_editText3);
       // mPoints_txt = findViewById(R.id.points_editText);

        //Button to capture Goals scored
        mGoalsSuccess = findViewById(R.id.goalsS);
        mGoalsSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsS++;
                Toast.makeText(Secondactivity.this, "Successful Goal Logged " + goalsS, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Goals missed
        mGoalsFail = findViewById(R.id.goalsF);
        mGoalsFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsF++;
                Toast.makeText(Secondactivity.this, "Unsuccessful Goal Logged " + goalsF, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Points Scored
        mPointsSuccess = findViewById(R.id.pointsS);
        mPointsSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsS++;
                Toast.makeText(Secondactivity.this, "Successful Point Logged " + pointsS, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Points missed
        mPointsFail = findViewById(R.id.pointsF);
        mPointsFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsF++;
                Toast.makeText(Secondactivity.this, "Unsuccessful Point Logged " + pointsF, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Hand Passes completed
        mHandpassSuccess = findViewById(R.id.handpassS);
        mHandpassSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handpassS++;
                Toast.makeText(Secondactivity.this, "Successful Handpass Logged " + handpassS, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Hand Passes missed
        mHandPassFail = findViewById(R.id.handpassF);
        mHandPassFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handpassF++;
                Toast.makeText(Secondactivity.this, "Unsuccessful Handpass Logged " + handpassF, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Kick Passes completed
        mKickpassSuccess = findViewById(R.id.kickpassS);
        mKickpassSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kickpassS++;
                Toast.makeText(Secondactivity.this, "Successful Kickpass Logged " + kickpassS, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to capture Kick Passes missed
        mKickPassFail = findViewById(R.id.kickpassF);
        mKickPassFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kickpassF++;
                Toast.makeText(Secondactivity.this, "Unsuccessful Kickpass Logged " + kickpassF, Toast.LENGTH_SHORT).show();
            }
        });

        //Button to add captured player report data to DB
        mAdd_btn = findViewById(R.id.add_button);
        mAdd_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //calculating percentage of success for each match event
                goalsC = goalsF + goalsS;
                goalsPC =  goalsS/goalsC;
                pointsC = pointsF + pointsS;
                pointsPC = pointsS/pointsC;
                handpassC = handpassF + handpassS;
                handpassPC = handpassS/handpassC;
                kickpassC = kickpassF + kickpassS;
                kickpassPC = kickpassS/ kickpassC;

                //Send data to database
                Report report = new Report();
                report.setName(mName_editTxt.getText().toString());
                report.setPosition(mPosition_editTxt.getText().toString());
                report.setGoalsfor(goalsS);
                report.setGoalsmiss(goalsF);
                report.setGoals_percentage(df2.format(goalsPC));//convertfloattodecimalformatandsendtoreport
                report.setPointsfor(pointsS);
                report.setPointsmiss(pointsF);
                report.setPoints_percentage(df2.format(pointsPC));//convertfloattodecimalformatandsendtoreport
                report.setHandpassfor(handpassS);
                report.setHandpassmiss(handpassF);
                report.setHandpass_percentage(df2.format(handpassPC));//convertfloattodecimalformatandsendtoreport
                report.setKickpassfor(kickpassS);
                report.setKickpassmiss(kickpassF);
                report.setKickpass_percentage(df2.format(kickpassPC));//convertfloattodecimalformatandsendtoreport
                report.setScore(mScore_editTxt.getText().toString());
                report.setSteps(mReadBuffer.getText().toString());

                new FirebaseDatabaseHelper().addReport(report, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Report> reports, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(Secondactivity.this, "The player report has been inserted successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }

                });
            }
        });

        mBluetoothStatus = (TextView)findViewById(R.id.bluetoothStatus);
        mReadBuffer = (TextView) findViewById(R.id.readBuffer);
        mScanBtn = (Button)findViewById(R.id.scan);
        mListPairedDevicesBtn = (Button)findViewById(R.id.PairedBtn);

        mBTArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        mDevicesListView = (ListView)findViewById(R.id.devicesListView);
        mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
        mDevicesListView.setOnItemClickListener(mDeviceClickListener);


        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    mReadBuffer.setText(readMessage);
                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device: " + (String)(msg.obj));
                    else
                        mBluetoothStatus.setText("Connection Failed");
                }
            }
        };

        if (mBTArrayAdapter == null) {
            // Device does not support Bluetooth
            mBluetoothStatus.setText("Status: Bluetooth not found");
            Toast.makeText(getApplicationContext(),"Bluetooth device not found!",Toast.LENGTH_SHORT).show();
        }
        else {

            mScanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bluetoothOn(v);
                }
            });

            mListPairedDevicesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    listPairedDevices(v);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void bluetoothOn(View view){
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothStatus.setText("Bluetooth enabled");
            Toast.makeText(getApplicationContext(),"Bluetooth turned on",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    // Enter here after user selects "yes" or "no" to enabling radio
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                mBluetoothStatus.setText("Enabled");
            }
            else
                mBluetoothStatus.setText("Disabled");
        }
    }


    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private void listPairedDevices(View view){
        mPairedDevices = mBTAdapter.getBondedDevices();
        if(mBTAdapter.isEnabled()) {
            // put it's one to the adapter
            for (BluetoothDevice device : mPairedDevices)
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        }
        else
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @SuppressLint("SetTextI18n")
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            mDevicesListView.setVisibility(mDevicesListView.isShown() ? View.GONE : View.VISIBLE);

            if(!mBTAdapter.isEnabled()) {
                Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }

            mBluetoothStatus.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0,info.length() - 17);

            // Spawn a new thread to avoid blocking the GUI one
            new Thread()
            {
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                    // Establish the Bluetooth socket connection.
                    try {
                        mBTSocket.connect();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            //insert code to deal with this
                            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(!fail) {
                        mConnectedThread = new ConnectedThread(mBTSocket);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();
                    }
                }
            }.start();
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
       /* public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        /*public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }*/
    }
}

