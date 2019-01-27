package com.ggj2019android;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.ggj2019android.model.Game;

public class ClockTask extends AsyncTask<Void, Void, Void>
{
    private ProgressBar _progress;

    public ClockTask(ProgressBar progress)
    {
        _progress = progress;
        _progress.setMax((int)Game.MILLISECONDS_PER_YEAR);
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        while (!isCancelled())
        {
            try
            {
                Game game = Game.INSTANCE;
                if (game != null)
                {
                    game.updateTimeElapsed();
                    publishProgress();
                }
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex)
            {
                // Do nothing
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Game game = Game.INSTANCE;
        if (game != null && _progress != null)
        {
            _progress.setProgress((int)game.getTimeElapsed());
        }
    }
}
