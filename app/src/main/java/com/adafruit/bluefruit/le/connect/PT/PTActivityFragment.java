package com.adafruit.bluefruit.le.connect.PT;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.R;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A placeholder fragment containing a simple view.
 */
public class PTActivityFragment extends Fragment {

    private View view;
    private MeetingsAdapter meetingsAdapter;
    private ListView meetingListView;
    private ArrayList<Meeting> meetings;
    private SearchView mSearch;
    private TextView meetingSortButton;
    private TextView prioritySortButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pt, container, false);

        meetings = new ArrayList<>();
        meetingsAdapter = new MeetingsAdapter(getContext(), meetings);
        meetingListView = (ListView) view.findViewById(R.id.meeting_list);
        mSearch = (SearchView) view.findViewById(R.id.search);
        mSearch.setQueryHint("Type and search for patients");
        meetingListView.setAdapter(meetingsAdapter);

        meetingSortButton = (TextView) view.findViewById(R.id.meeting_text);
        prioritySortButton = (TextView) view.findViewById(R.id.priority_text);

        meetingSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(meetings, new MeetingTimeComparator());
                meetingsAdapter.notifyDataSetChanged();
            }
        });

        prioritySortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(meetings, new MeetingComparator());
                meetingsAdapter.notifyDataSetChanged();
            }
        });



        // add an example appointment
        meetings.add(new Meeting(new Patient("Wilson", 65, "Male", 152, 5, 3), new Date(2017,4,20), new Time(8,0,0), new Time(9,0,0)));
        meetings.add(new Meeting(new Patient("Trent", 72, "Male", 160, 6, 2), new Date(2017,4,22), new Time(9,0,0), new Time(10,0,0)));
        meetings.add(new Meeting(new Patient("Jordan", 62, "Female", 158, 7, 1), new Date(2017,4,25), new Time(12,0,0), new Time(13,0,0)));
        meetings.add(new Meeting(new Patient("Yuzhong", 61, "Male", 148, 5, 2), new Date(2017,4,28), new Time(14,0,0), new Time(15,0,0)));
        meetings.add(new Meeting(new Patient("Issac", 55, "Male", 136, 6, 3), new Date(2017,4,30), new Time(15,0,0), new Time(16,0,0)));
        meetingsAdapter.notifyDataSetChanged();

        return view;
    }
}
