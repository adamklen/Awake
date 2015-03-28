package ca.coffee.coffeetimenodonuts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by ajklen on 3/28/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener{

    private TextView clock;
    private Calendar alarmTime;
    private DateFormat format;

    public MainFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        rootView.findViewById(R.id.button_set).setOnClickListener(this);
        clock = (TextView) rootView.findViewById(R.id.clock);
        clock.setOnClickListener(this);

        alarmTime = (Calendar) Calendar.getInstance().clone();
        format = android.text.format.DateFormat.getTimeFormat(getActivity().getApplicationContext());

        clock.setText(format.format(alarmTime.getTime()));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_set:
                setAlarm();
                return;
            case R.id.clock:
                setTime();
                return;
            default:
                return;
        }
    }

    private void setAlarm(){
        if (alarmTime != null){

            Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                    //the number is the alarm ID; any alarms with the same id are overwritten
                    12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(),
                    pendingIntent);


        }
    }

    private void setTime(){
        new TimePickerDialog(getActivity(), this, alarmTime.get(Calendar.HOUR_OF_DAY), alarmTime.get(Calendar.MINUTE), false).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar current = Calendar.getInstance();
        alarmTime = (Calendar)current.clone();

        alarmTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        alarmTime.set(Calendar.MINUTE, minute);

        if (alarmTime.before(current)){
            alarmTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        clock.setText(format.format(alarmTime.getTime()));

    }
}
