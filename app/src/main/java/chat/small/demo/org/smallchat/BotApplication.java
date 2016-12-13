package chat.small.demo.org.smallchat;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


public class BotApplication extends Application {

    private static BotService mService;
    private static boolean mBound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BotService.LocalBinder binder = (BotService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, BotService.class);
        intent.setAction(Constants.ACTION_QUESTION);
        startService(intent);

        // Bind to LocalService
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public static boolean isBrainLoaded(){
        if (mBound) {
            return mService.isLoaded();
        }
        return false;
    }

}