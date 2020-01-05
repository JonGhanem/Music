package com.yahyaghanem.music.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.adapter.MusicAdapter;
import com.yahyaghanem.music.interfaces.SelectedSongInterface;
import com.yahyaghanem.music.model.SongsInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends Fragment {
    @BindView(R.id.url)
    EditText url;
    @BindView(R.id.play)
    Button play;
    private SongsInfo songsInfo = new SongsInfo();
    PlayerFragment playerFragment = new PlayerFragment();


    public OnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                songsInfo.setSongUrl(url.getText().toString());
                songsInfo.setArtistName("artist");
                songsInfo.setSongName("unknown");

                Bundle bundle = new Bundle();
                bundle.putParcelable("selected song", songsInfo);

                playerFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, playerFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

}
