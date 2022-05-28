package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Song {
    private final String path;
    private final String name;
    private final String artist;

    public Song(String path, boolean playing, String name, String artist){
        this.path = path;
        this.name =name;
        this.artist = artist;

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
