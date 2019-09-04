package com.example.musicplayer;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;


public class AllSongsFragmentRecyclerViewAdapter extends RecyclerView.Adapter<AllSongsFragmentRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Song> songs;
    private Context context;

    public AllSongsFragmentRecyclerViewAdapter(ArrayList<Song> songs, Context context){
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_all_songs_fragment,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.songTitle.setText(songs.get(position).getTitle());
        holder.songArtist.setText(songs.get(position).getArtist());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MediaPlayerActivity.class);
                intent.putExtra("URI",songs.get(position).getData());
                intent.putExtra("Name",songs.get(position).getTitle());
                intent.putExtra("Artist",songs.get(position).getArtist());
                if (StaticObjectsAndVariables.songPlaylist.get("Recently Played").contains(songs.get(position)))
                    Objects.requireNonNull(StaticObjectsAndVariables.songPlaylist.get("Recently Played")).add(songs.get(position));
                v.getContext().startActivity(intent);
            }
        });

        holder.popUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("CLICK","Done");
                PopupMenu popupMenu = new PopupMenu(v.getContext(),holder.popUpButton);
                popupMenu.inflate(R.menu.all_songs_pop_up);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (StaticObjectsAndVariables.playlistName.size() - 2 > 0) {
                            final CharSequence[] playlist = new CharSequence[StaticObjectsAndVariables.playlistName.size() - 2];
                            for (int i = 0; i < StaticObjectsAndVariables.playlistName.size() - 2; i++) {
                                playlist[i] = StaticObjectsAndVariables.playlistName.get(i + 2);
                            }

                            StaticObjectsAndVariables.indexPlaylistRadioGroupTapped = -1;

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                            alertDialog.setTitle("Select Playlist");
                            alertDialog.setSingleChoiceItems(playlist, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StaticObjectsAndVariables.indexPlaylistRadioGroupTapped = which;
                                    Log.i("indexRadioGroupTappedValue", Integer.toString(StaticObjectsAndVariables.indexPlaylistRadioGroupTapped));
                                }
                            });
                            alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.out.println("ADDED");

                                    int tapping = StaticObjectsAndVariables.indexPlaylistRadioGroupTapped + 2;
                                    Song adding = songs.get(position);
                                    Log.i("SONG",adding.toString());
                                    Log.i("TAPPING",String.valueOf(tapping));
                                    if(!Objects.requireNonNull(StaticObjectsAndVariables.songPlaylist.get(StaticObjectsAndVariables.playlistName.get(tapping))).contains(adding)) {
                                        Log.i("IF","RAN");
                                        Objects.requireNonNull(StaticObjectsAndVariables.songPlaylist.get(StaticObjectsAndVariables.playlistName.get(tapping))).add(adding);
                                    }
                                    Log.i("CONTENT", String.valueOf(StaticObjectsAndVariables.songPlaylist.get(StaticObjectsAndVariables.playlistName.get(tapping))));
                                    dialog.cancel();
                                }
                            });

                            alertDialog.setNegativeButton("NOT ADD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            return true;
                        } else {
                            Toast.makeText(v.getContext(), "No Playlist Available", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView songArtist;
        TextView songTitle;
        ImageButton popUpButton;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songArtist = itemView.findViewById(R.id.songArtistAllSongsFragment);
            songTitle = itemView.findViewById(R.id.songTitleAllSongsFragment);
            constraintLayout = itemView.findViewById(R.id.allSongsFragmentRecyclerViewConstraintLayout);
            popUpButton = itemView.findViewById(R.id.recyclerViewAllSongsFragmentImageButton);
        }
    }
}
