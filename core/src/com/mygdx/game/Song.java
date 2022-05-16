package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Song {
    private String path;
    private boolean playing;
    private String name;
    private String artist;

    public Song(String path, boolean playing, String name, String artist){
        this.path = path;
        this.playing = playing;
        this.name =name;
        this.artist = artist;

    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public String getPath() {
        return path;
    }

    public boolean isPlaying() {
        return playing;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public void play(){
        MusicPlayer.music.stop();
        MusicPlayer.music = Gdx.audio.newMusic(Gdx.files.internal(path));
        MusicPlayer.music.play();
        MusicPlayer.music.setLooping(true);

    }
    public void stop(){
        MusicPlayer.music.stop();
    }
}
