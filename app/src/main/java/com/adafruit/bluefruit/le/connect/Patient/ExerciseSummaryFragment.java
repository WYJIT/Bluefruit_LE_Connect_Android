package com.adafruit.bluefruit.le.connect.Patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adafruit.bluefruit.le.connect.R;



public class ExerciseSummaryFragment extends Fragment {

    private Button startButton;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exercise_summary, container, false);

        startButton = (Button) view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientActivity activity = (PatientActivity) getActivity();
                activity.transitionToFragment(new ExerciseResultsFragment());
            }
        });


        return view;
    }

}
