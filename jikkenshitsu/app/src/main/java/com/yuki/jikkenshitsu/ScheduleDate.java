package com.yuki.jikkenshitsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleDate extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_date);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);

        addEvent = findViewById(R.id.saveDateBtn);
        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScheduleDate.this, "Scheduling your date!",
                        Toast.LENGTH_SHORT).show();

                Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
                //calendarIntent.putExtra(CalendarContract.Events.TITLE, Activity1.str);
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Pu-Tien");
                //calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, Activity1.strd);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.ALL_DAY, "true");
                calendarIntent.putExtra(Intent.EXTRA_EMAIL, "yiwei.mospace@gmail.com");

                startActivity(calendarIntent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                myDate.setText(date);

                beginCal.set(year, month, dayOfMonth);
            }
        });
    }
}