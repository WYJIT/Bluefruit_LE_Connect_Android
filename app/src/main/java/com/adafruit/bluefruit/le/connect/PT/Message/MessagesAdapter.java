package com.adafruit.bluefruit.le.connect.PT.Message;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.PT.PTActivity;
import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

/**
 * Created by yhuang on 4/4/2017.
 */

public class MessagesAdapter extends ArrayAdapter<Message> {
    private PTActivity mActivity = new PTActivity();

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
        mActivity = (PTActivity) context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // get the item from this position
        final Message message = getItem(position);
        // check if a view is being used, else inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_pt, parent, false);
        }
        // Lookup the view to populate items
        TextView name = (TextView) convertView.findViewById(R.id.patient_name);
        TextView text= (TextView) convertView.findViewById(R.id.patient_message);
        // Populate the data into the template view using the data object
        name.setText(message.getPatientName());
        text.setText(message.getContent());

        if (!message.getSeen()) {
            name.setTypeface(null, Typeface.BOLD);
            text.setTypeface(null, Typeface.BOLD);
        }

        return convertView;
    }
}
