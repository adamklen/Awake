package ca.coffee.coffeetimenodonuts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

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
            case R.id.fab:
                setTime();
                return;
            default:

        }
    }

    private void setAlarm(){
        if (alarmTime != null){

            Intent alarmIntent = new Intent(getActivity(), AlarmReceiverActivity.class);
            Intent coffeeIntent = new Intent(getActivity(), CoffeeReceiverActivity.class);

            PendingIntent alarmPendingIntent = PendingIntent.getActivity(getActivity(),
                    //the number is the alarm ID; any alarms with the same id are overwritten
                    12345, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            PendingIntent coffeePendingIntent = PendingIntent.getActivity(getActivity(),
                    //the number is the alarm ID; any alarms with the same id are overwritten
                    54321, coffeeIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);

            am.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(),
                    alarmPendingIntent);

            Calendar coffeeTime = (Calendar) alarmTime.clone();
            coffeeTime.add(Calendar.SECOND , -60);

            am.set(AlarmManager.RTC_WAKEUP, coffeeTime.getTimeInMillis(), coffeePendingIntent);

            Toast.makeText(getActivity(), "Alarm set", Toast.LENGTH_SHORT).show();
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
        alarmTime.set(Calendar.SECOND, 0);
        alarmTime.set(Calendar.MILLISECOND, 0);

        if (alarmTime.before(current)){
            alarmTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        clock.setText(format.format(alarmTime.getTime()));

    }

}
