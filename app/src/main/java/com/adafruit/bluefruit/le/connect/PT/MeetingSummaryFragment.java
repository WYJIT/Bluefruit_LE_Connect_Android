package com.adafruit.bluefruit.le.connect.PT;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.Patient.Exercise;
import com.adafruit.bluefruit.le.connect.Patient.ExercisesAdapter;
import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

public class MeetingSummaryFragment extends Fragment {
    private View view;

    private String name;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String gender;

    private PTExercisesAdapter PTexercisesAdapter;
    private ListView exercisesListView;
    private ArrayList<Exercise> exercises;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        name = args.getString("name");
        age = args.getInt("age");
        gender = args.getString("gender");
        weight = args.getInt("weight");
        height = args.getInt("height");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_meeting_summary, container, false);

        exercises = new ArrayList<>();
        PTexercisesAdapter = new PTExercisesAdapter(getContext(), exercises);
        exercisesListView = (ListView) view.findViewById(R.id.patient_exercise_list);
        exercisesListView.setAdapter(PTexercisesAdapter);
        exercises.add(new Exercise("Bicep curls", false));
        exercises.add(new Exercise("Arm circles", true));
        exercises.add(new Exercise("Side raises", true));
        exercises.add(new Exercise("Bent over reverse fly", true));
        setListViewHeightBasedOnChildren(exercisesListView);
        PTexercisesAdapter.notifyDataSetChanged();

        final EditText noteTage = (EditText) view.findViewById(R.id.edit_note);
        final Button b = (Button) view.findViewById(R.id.enter_note);
        b.setVisibility(View.INVISIBLE);

        TextView nameTag = (TextView) view.findViewById(R.id.patient_name);
        TextView ageTag = (TextView) view.findViewById(R.id.age);
        TextView genderTag = (TextView) view.findViewById(R.id.gender);
        TextView weightTag = (TextView) view.findViewById(R.id.weight);
        TextView heightTag = (TextView) view.findViewById(R.id.height);

        nameTag.setText(name);
        ageTag.setText(Integer.toString(age) + " years old");
        genderTag.setText(gender);
        weightTag.setText(Integer.toString(weight) + " lbs");
        heightTag.setText(Integer.toString(height) + " feet");

        noteTage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                noteTage.setCursorVisible(true);
                b.setVisibility(View.VISIBLE);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                noteTage.setCursorVisible(false);
                noteTage.setInputType(InputType.TYPE_NULL);
                b.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}