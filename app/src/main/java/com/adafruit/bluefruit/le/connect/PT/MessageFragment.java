package com.adafruit.bluefruit.le.connect.PT;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.adafruit.bluefruit.le.connect.PT.Message.Message;
import com.adafruit.bluefruit.le.connect.PT.Message.MessagesAdapter;
import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private View view;
    private ArrayList<Message> messages;
    private ListView messageListView;
    private MessagesAdapter messagesAdapter;
    private SearchView mSearch;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);

        messages = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(getContext(), messages);
        messageListView = (ListView) view.findViewById(R.id.message_list);
        messageListView.setAdapter(messagesAdapter);
        mSearch = (SearchView) view.findViewById(R.id.search);
        mSearch.setQueryHint("Type and search for patients");

        // add an example appointment
        messages.add(new Message(new Patient("Jordan", 62, "Female", 158, 7),  "Hello, just wanna double check my current status...", false));
        messages.add(new Message(new Patient("Issac", 55, "Male", 136, 6), "I will tell you my secret...", false));
        messages.add(new Message(new Patient("Wilson", 65, "Male", 152, 5), "Hello, I have a question about my exercises...", true));
        messages.add(new Message(new Patient("Trent", 72, "Male", 160, 6),  "Just for testing", true));
        messages.add(new Message(new Patient("Yuzhong", 61, "Male", 148, 5), "...", true));

        return view;
    }

}
