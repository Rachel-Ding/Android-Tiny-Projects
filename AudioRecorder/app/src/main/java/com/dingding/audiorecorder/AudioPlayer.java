package com.dingding.audiorecorder;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 播放音频类
 * Created by Dingding on 2016/1/30.
 */
public class AudioPlayer {

    private MediaPlayer mediaplayer;//播放类
    private String playerPath;//播放路径

    public AudioPlayer() {
    }

    public void setPlayerPath(String playerPath) {
        this.playerPath = playerPath;
    }

    /*播放*/
    public void play() {
        if (mediaplayer == null) {
            mediaplayer = new MediaPlayer();
        }
        try {
            mediaplayer.setDataSource(playerPath);
            mediaplayer.prepare();
            mediaplayer.start();

            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
