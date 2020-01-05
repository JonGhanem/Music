package com.yahyaghanem.music.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.adapter.MusicAdapter;
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
    private ProgressDialog dialog;
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

//        dialog = new ProgressDialog(getActivity());
//        dialog.setTitle("Reading from internal storage...");
//        if (songsInfos != null){
//            dialog.dismiss();}

        songsInfos = getArguments().getParcelableArrayList("listofsongs");
        //ADAPTER
        Log.d("callfragment", "songs count = " + songsInfos.size());

        musicAdapter = new MusicAdapter(songsInfos, getActivity());

        recyclerView.setAdapter(musicAdapter);
    }
}
