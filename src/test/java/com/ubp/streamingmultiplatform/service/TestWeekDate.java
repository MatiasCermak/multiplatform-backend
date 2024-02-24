package com.ubp.streamingmultiplatform.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestWeekDate {
    public static void main(String[] args) {
        // Get the current date
        Date currentDate = new Date();

        // Create a calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Set the calendar to the beginning of the current week and subtract a week
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);

        // Get the first day of last week
        Date firstDayOfLastWeek = calendar.getTime();

        // Set the calendar to the end of last week
        calendar.add(Calendar.DAY_OF_WEEK, 6);

        // Get the last day of last week
        Date lastDayOfLastWeek = calendar.getTime();

        // Format the dates as strings for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedFirstDay = dateFormat.format(firstDayOfLastWeek);
        String formattedLastDay = dateFormat.format(lastDayOfLastWeek);

        // Print the results
        System.out.println("First day of last week: " + formattedFirstDay);
        System.out.println("Last day of last week: " + formattedLastDay);
    }
}