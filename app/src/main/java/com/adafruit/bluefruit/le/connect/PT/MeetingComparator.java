package com.adafruit.bluefruit.le.connect.PT;

import com.adafruit.bluefruit.le.connect.PT.Meeting.Meeting;

import java.util.Comparator;

/**
 * Created by Jordan on 4/4/17.
 */

public class MeetingComparator implements Comparator<Meeting>
{
    public int compare(Meeting left, Meeting right) {
        return right.getPatientPriority() - left.getPatientPriority();
    }
}
