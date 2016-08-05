package com.example.knothing.androidanydemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.knothing.androidanydemo.messenger.MessengerService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Messenger activityMessenger = null ;

    private Messenger messenger = null ;

    private Button messengerBtn ;


    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 0:
                    Log.i("KKK",String.valueOf(msg.obj));
                    break;

                default:
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();

    }

    private void initView() {

        messengerBtn = (Button) findViewById(R.id.messengerBtn);
        messengerBtn.setOnClickListener(this);

        initData();
    }

    private void initData() {

        activityMessenger = new Messenger(myHandler) ;
        bindService(new Intent(this, MessengerService.class), connection, Service.BIND_AUTO_CREATE);
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messenger = null ;
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.messengerBtn:
                Message msg = Message.obtain();
                msg.what = 0 ;
                msg.replyTo = activityMessenger ;
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            default:

                break;


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
