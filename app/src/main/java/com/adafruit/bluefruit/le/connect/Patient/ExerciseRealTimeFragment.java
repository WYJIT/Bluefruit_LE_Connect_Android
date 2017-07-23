package com.adafruit.bluefruit.le.connect.Patient;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExerciseRealTimeFragment extends Fragment {

    private View view;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_real_time, container, false);

        button = (Button) view.findViewById(R.id.finish_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientActivity activity = (PatientActivity) getActivity();
                activity.transitionToFragment(new ExerciseCongratulationsFragment());
            }
        });

        return view;
    }
}
