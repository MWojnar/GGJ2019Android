package com.ggj2019android;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ClockService extends Service
{
    private ClockTask _task;

    public ClockService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        _task = new ClockTask(null);
        _task.execute();
    }
}
