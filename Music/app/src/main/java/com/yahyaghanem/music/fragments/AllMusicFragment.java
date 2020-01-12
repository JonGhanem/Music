package com.yahyaghanem.music.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.adapter.MusicAdapter;
import com.yahyaghanem.music.interfaces.SelectedSongInterface;
import com.yahyaghanem.music.model.SongsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllMusicFragment extends Fragment {

    private MusicAdapter musicAdapter;
    private List<SongsInfo> songsInfos = new ArrayList<>();
    @BindView(R.id.all_products_recyclerview)
    RecyclerView recyclerView;

    public AllMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        songsInfos = getArguments().getParcelableArrayList("listofsongs");
        //ADAPTER
        Log.d("callfragment", "songs count = " + songsInfos.size());

        musicAdapter = new MusicAdapter(songsInfos);
        if (getActivity() instanceof SelectedSongInterface) {
            musicAdapter.setmSelectedItemIterface((SelectedSongInterface) getActivity());
        }

        recyclerView.setAdapter(musicAdapter);
    }
}
