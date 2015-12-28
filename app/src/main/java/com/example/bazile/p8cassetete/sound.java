package com.example.bazile.p8cassetete;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.MediaController;

/**
 * Created by bazile on 23/11/2015.
 */
public class sound extends MediaPlayer implements MediaController.MediaPlayerControl {

    MediaPlayer Player;

    public void Musique(Context context){
              Player = MediaPlayer.create(context,R.raw.sound);
    }



    @Override
    public void start() {
        Player.start();
    }

    public void pause(){
        Player.pause();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {

        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    public void stop(){
        Player.stop();
    }


}
