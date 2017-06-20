package com.example.yokgoodchild.roadsideassistance.Map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yokgoodchild.roadsideassistance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FM_Map_Markers extends Fragment {


    public FM_Map_Markers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fm__map__markers, container, false);
    }

}
