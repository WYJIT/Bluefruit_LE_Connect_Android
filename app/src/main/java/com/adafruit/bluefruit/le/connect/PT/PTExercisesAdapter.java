package com.adafruit.bluefruit.le.connect.PT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.Patient.Exercise;
import com.adafruit.bluefruit.le.connect.Patient.ExerciseSummaryFragment;
import com.adafruit.bluefruit.le.connect.Patient.PatientActivity;
import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

/**
 * Created by yhuang on 4/2/2017.
 */

public class PTExercisesAdapter extends ArrayAdapter<Exercise> {

    private PTActivity PTActivity;

    public PTExercisesAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
        PTActivity = (PTActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the item from this position
        final Exercise exercise = getItem(position);
        // check if a view is being used, else inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_patient, parent, false);
        }
        // Lookup the view to populate items
        TextView name = (TextView) convertView.findViewById(R.id.exercise_name);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        checkBox.setVisibility(View.GONE);
        // Populate the data into the template view using the data object
        name.setText(exercise.getName());

        // Return completed view to render on screen
        return convertView;
    }
}

