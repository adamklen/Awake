package ca.coffee.coffeetimenodonuts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import java.util.Calendar;

/**
 * Created by ajklen on 3/28/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private TextClock clock;
    private Calendar calendar;

    public MainFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        rootView.findViewById(R.id.button_set).setOnClickListener(this);
        clock = (TextClock) rootView.findViewById(R.id.clock);
        clock.setOnClickListener(this);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);

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

        if (calendar != null){

            Log.d("MainFragment", "alarm set");

            calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 5);

            Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                    //the number is the alarm ID; any alarms with the same id are overwritten
                    12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);


        }
    }

    private void setTime(){

    }
}
