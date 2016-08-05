package com.example.knothing.androidanydemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private Messenger serverMessenger = null ;

    private MyHandler myHandler = null ;

    public MessengerService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myHandler = new MyHandler();
        serverMessenger = new Messenger(myHandler);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serverMessenger.getBinder();
    }


    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 0:
                    Log.i("KKK","收到页面发来的消息了！！！");
                    try {
                        Message msg2 = Message.obtain();
                        msg2.what = 0;
                        msg2.obj = "这是来自服务的消息" ;
                        serverMessenger.send(msg2);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case 1:
                    break;

                default:
                    break;

            }
        }
    }

}
