package dignityonwheelsplus.org.locator;

import java.util.Calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleFragment extends Fragment{
    private boolean isAdmin = false;
    private HorizontalCalendar horizontalCalendar;
    private LinearLayout scheduleLayout;
    private Button cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View scheduleView = inflater.inflate(R.layout.fragment_schedule, container, false);

        scheduleLayout = (LinearLayout) scheduleView.findViewById(R.id.schedule_layout);
        cancel = scheduleView.findViewById(R.id.cancel_button);

        if (!isAdmin) {
            new GetLocationTask().execute(0);
            ViewGroup parent = (ViewGroup) cancel.getParent();
            parent.removeView(cancel);
        }
        else{
            new GetAdminLocationTask().execute(0);

        }

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(scheduleView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .textSize(14f, 24f, 14f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                scheduleLayout.removeAllViews();
                cancel.setVisibility(View.INVISIBLE);
                if (!isAdmin) {
                    new GetLocationTask().execute(position - 32);
                }
                else{
                    new GetAdminLocationTask().execute(position - 32);
                }
            }
        });

        return scheduleView;
    }


    private class GetLocationTask extends AsyncTask<Integer, Void, Location> {

        protected Location doInBackground(Integer... args) {
            return new Location(args[0]);
        }

        protected void onPostExecute(final Location result) {

            for (int i = 0; i < result.getMessage().length; i++) {

                boolean isActive = true;
                String msg = result.getMessage()[i];
                if (msg != null) {
                    msg = msg.trim();
                    if (msg.indexOf("CANCELED:") == 0) {
                        isActive = false;
                    }
                }

                String status = "<font color = '#00FF00'> Status : Active </font>";
                if(!isActive) {
                    status = "<font color = '#FF0000'> Status : Inactive </font>";
                }
                View userEventView = MainActivity.inflater.inflate(R.layout.fragment_text, MainActivity.schedule.getScheduleLayout(), false);
                TextView text = (TextView) userEventView.findViewById(R.id.textview);
                Button button = (Button) userEventView.findViewById(R.id.directions);
                text.setText(Html.fromHtml(status));
                text.append(result.getMessage()[i]);
                MainActivity.schedule.getScheduleLayout().addView(userEventView);

                final int finalI = i;
                button.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        if (result.getEvents().get(finalI).getLocation() != null) {
                            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + result.getEvents().get(finalI).getLocation());
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                        else{
                            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + result.getEvents().get(finalI).getSummary());
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }

                    }
                });

            }
        }
    }

    public ViewGroup getScheduleLayout() {
        return scheduleLayout;
    }

    public View getCancel() {
        return cancel;
    }

    public void setAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

}
