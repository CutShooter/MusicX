package com.example.musicplayer;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticObjectsAndVariables {
    final static CharSequence[] equalizerTypes = new CharSequence[]{"Default","Vocal","Club","Live","Pop","Rock","Country","Classical"};
    static Map<String, ArrayList<Song>> songPlaylist = new HashMap<>();
    static ArrayList<String> playlistName = new ArrayList<>();
    static int indexEqualizerRadioGroupTapped;
    static int indexPlaylistRadioGroupTapped;
    static ArrayList<Drawable> drawables = new ArrayList<>();
}
