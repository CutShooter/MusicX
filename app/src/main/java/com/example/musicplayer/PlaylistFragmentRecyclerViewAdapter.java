package com.example.musicplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class PlaylistFragmentRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistFragmentRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<String> strings;
    private ArrayList<Drawable> drawables;
    private Map<String,String> equalizers;
    private Context context;

    PlaylistFragmentRecyclerViewAdapter(Context context,ArrayList<String> strings, ArrayList<Drawable> drawables, Map<String,String> equalizers){
        this.context = context;
        this.strings = strings;
        this.drawables = drawables;
        this.equalizers = equalizers;
        StaticObjectsAndVariables.indexEqualizerRadioGroupTapped = -1;
    }

    @NonNull @Override
    public PlaylistFragmentRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_playsists_fragment_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.textView.setText(strings.get(position));
        holder.imageView.setImageDrawable(drawables.get(position));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAPPED", "AT POSITION : " + position);
                Intent intent = new Intent(v.getContext(),SongPlaylistActivity.class);
                intent.putExtra("PlaylistName", strings.get(position));
                context.startActivity(intent);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("BUTTON", "TAPPED");
                PopupMenu popupMenu = new PopupMenu(v.getContext(),holder.imageButton);
                popupMenu.inflate(R.menu.playlist_pop_up_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                if(!(holder.textView.getText().toString().matches("Recently Played")) && !(holder.textView.getText().toString().matches("Favourites"))){
                                    equalizers.remove(strings.get(position));
                                    StaticObjectsAndVariables.songPlaylist.remove(strings.get(position));
                                    strings.remove(position);
                                    PlaylistFragmentRecyclerViewAdapter.this.notifyDataSetChanged();
                                    Toast.makeText(v.getContext(),"Successfully deleted",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(v.getContext(),"Can't delete " + holder.textView.getText().toString() + " playlist.",Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.equalizer:
                                //Vocal,Club,Live,Pop,Rock,Default,Country,Classic
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                                alertDialog.setTitle("EQUALIZER");
                                alertDialog.setSingleChoiceItems(StaticObjectsAndVariables.equalizerTypes, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("INDEX TAPPED", Integer.toString(which));
                                        StaticObjectsAndVariables.indexEqualizerRadioGroupTapped = which;
                                    }
                                });
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i(strings.get(position), String.valueOf(StaticObjectsAndVariables.equalizerTypes[StaticObjectsAndVariables.indexEqualizerRadioGroupTapped]));
                                        equalizers.put(strings.get(position),String.valueOf(StaticObjectsAndVariables.equalizerTypes[StaticObjectsAndVariables.indexEqualizerRadioGroupTapped]));
                                    }
                                });

                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(strings == null) return 0;
        else return strings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout constraintLayout;
        TextView textView;
        ImageView imageView;
        ImageButton imageButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.recyclerViewFragmentPlaylistConstraintLayout);
            textView = itemView.findViewById(R.id.recyclerViewFragmentPlaylistTextView);
            imageView = itemView.findViewById(R.id.recyclerViewFragmentPlaylistImageView);
            imageButton = itemView.findViewById(R.id.receylerViewFragementPlaylistImageButton);
        }
    }
}
