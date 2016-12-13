package chat.small.demo.org.smallchat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


public class BotService extends Service {

    private static final int NOTIFICATION_ID = 1337;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    private boolean isRunning  = false;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BotService","onCreate()");
        isRunning = true;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent==null){
            Log.d("BotService","onStartCommand() null");
            return Service.START_STICKY;
        }

        String action = intent.getAction();

        if(action.equalsIgnoreCase(Constants.ACTION_QUESTION)){

            String question = intent.getStringExtra(Constants.EXTRA_QUESTION);
            if(question!=null){
                Log.d("BotService","onStartCommand() question:"+question);
                String answer = "TBD";


                Intent localIntent =
                        new Intent(Constants.BROADCAST_ACTION_BRAIN_ANSWER)
                                // Puts the answer into the Intent
                                .putExtra(Constants.EXTRA_ANSWER, answer);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(BotService.this).sendBroadcast(localIntent);

            }

            return Service.START_STICKY;

        }


        if(action.equalsIgnoreCase(Constants.ACTION_STOP)){
            Log.d("BotService","onStartCommand() ACTION_STOP");
            stopForeground(true);
            stopSelf();
            return Service.START_NOT_STICKY;
        }

        if(action.equalsIgnoreCase(Constants.ACTION_START)){
            Log.d("BotService","onStartCommand() ACTION_START");
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        //return null;
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BotService", "onDestroy()");
        isRunning = false;
    }


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        BotService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BotService.this;
        }
    }

    /** method for clients */
    public boolean isLoaded() {
        return true;
    }

}