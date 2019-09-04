package com.example.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllSongsFragment extends Fragment{

    private ArrayList<Song> allSongs;
    private RecyclerView recyclerView;
    private AllSongsFragmentRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_songs,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allSongs = new ArrayList<>();
        adapter = new AllSongsFragmentRecyclerViewAdapter(allSongs,view.getContext());

        recyclerView = view.findViewById(R.id.recyclerViewAllSongsFragmentImageButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        getMusic(view.getContext());
    }

    private void getMusic(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        Uri songResolver = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor songCursor = contentResolver.query(songResolver,null,null,null);
        Log.i("INFO : ", "Found Search");
        if(songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            Log.i("INFO : ", "Start Search");
            do{
                Song song = new Song();
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentData = songCursor.getString(songData);
                int currentDuration = songCursor.getInt(songDuration);
                song.setTitle(currentTitle);
                song.setArtist(currentArtist);
                song.setDuration(currentDuration);
                song.setData(currentData);
                allSongs.add(song);
                adapter.notifyDataSetChanged();
            }while (songCursor.moveToNext());
            songCursor.close();
        }
    }
}
