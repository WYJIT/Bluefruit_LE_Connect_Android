package com.adafruit.bluefruit.le.connect.PT;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.adafruit.bluefruit.le.connect.R;

/**
 * Created by Jordan on 4/3/17.
 */

public class PTExersiceSummaryFragment extends Fragment {
    private Button recordButton;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pt_exercise_summary, container, false);

        recordButton = (Button) view.findViewById(R.id.record_button);



        return view;
    }

}
