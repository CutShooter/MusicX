package com.example.musicplayer;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaylistFragment extends Fragment implements View.OnClickListener {

    private RecyclerView playlistRecyclerView;

    private Map<String,String> equalizers;
    private FloatingActionButton fab;
    private PlaylistFragmentRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlists,container,false);
        playlistRecyclerView = rootView.findViewById(R.id.recyclerViewFragmentPlaylists);

        equalizers = new HashMap<>();

        adapter = new PlaylistFragmentRecyclerViewAdapter(rootView.getContext(),StaticObjectsAndVariables.playlistName,StaticObjectsAndVariables.drawables,equalizers);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        playlistRecyclerView.setAdapter(adapter);

        if (!StaticObjectsAndVariables.playlistName.contains("Favourites")) {
            StaticObjectsAndVariables.playlistName.add("Favourites");
            StaticObjectsAndVariables.songPlaylist.put("Favourites", new ArrayList<Song>());
            StaticObjectsAndVariables.drawables.add(getResources().getDrawable(R.drawable.favorite_heart_button,rootView.getContext().getTheme()));
        }

        if (!StaticObjectsAndVariables.playlistName.contains("Recently Played")) {
            StaticObjectsAndVariables.playlistName.add("Recently Played");
            StaticObjectsAndVariables.songPlaylist.put("Recently Played", new ArrayList<Song>());
            StaticObjectsAndVariables.drawables.add(getResources().getDrawable(R.drawable.play_button,rootView.getContext().getTheme()));
        }

        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);
   }

    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.floatingActionButton){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
            alertDialog.setTitle("PlayList Name :");
            alertDialog.setIcon(android.R.drawable.ic_input_add);
            final EditText editText = new EditText(v.getContext());
            alertDialog.setView(editText);
            alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StaticObjectsAndVariables.playlistName.add(editText.getText().toString());
                    StaticObjectsAndVariables.songPlaylist.put(editText.getText().toString(), new ArrayList<Song>());
                    StaticObjectsAndVariables.drawables.add(getResources().getDrawable(R.drawable.playlist,v.getContext().getTheme()));
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                }
            });
            alertDialog.setNegativeButton("DON'T ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }
}
