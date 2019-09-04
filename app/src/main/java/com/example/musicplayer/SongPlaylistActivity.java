package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SongPlaylistActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Song> playlistSongs;
    ArrayAdapter<String> songArrayAdapter;
    ArrayList<String> songArtistAndTitle;
    ListView listView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playlist);
        setTitle("My Music Player");
        //60, 79, 230
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(74, 48, 219)));
        Intent intent = getIntent();

        songArtistAndTitle = new ArrayList<>();
        name = intent.getStringExtra("PlaylistName");
        playlistSongs = StaticObjectsAndVariables.songPlaylist.get(name);
        if (playlistSongs != null) {
            for (int i = 0; i < playlistSongs.size(); i++) {
                String s = playlistSongs.get(i).getTitle() + "-" + playlistSongs.get(i).getArtist();
                songArtistAndTitle.add(s);
            }
        }

        songArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, songArtistAndTitle);

        listView = findViewById(R.id.songPlaylistActivityListView);
        listView.setAdapter(songArrayAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(!StaticObjectsAndVariables.songPlaylist.get("Recently Played").contains(playlistSongs.get(position))){
                StaticObjectsAndVariables.songPlaylist.get("Recently Played").add(playlistSongs.get(position));
            }
            Intent intent = new Intent(SongPlaylistActivity.this, MediaPlayerActivity.class);
            intent.putExtra("Name", playlistSongs.get(position).getTitle());
            intent.putExtra("Artist",playlistSongs.get(position).getArtist());
            intent.putExtra("URI",playlistSongs.get(position).getData());
            intent.putExtra("Playlist Name",name);
            startActivity(intent);
    }
}
