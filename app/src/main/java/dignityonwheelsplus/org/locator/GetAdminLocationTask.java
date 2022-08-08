package dignityonwheelsplus.org.locator;

import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GetAdminLocationTask extends AsyncTask<Integer, Void, Location> {

    String status;

    protected Location doInBackground(Integer... args) {
        return new Location(args[0]);
    }

    protected void onPostExecute(final Location result) {
        final CheckBox checkboxes[] = new CheckBox[result.getMessage().length];
        final TextView texts[] = new TextView[result.getMessage().length];
        final Button buttons[] = new Button[result.getMessage().length];


        for (int i = 0; i < result.getMessage().length; i++) {
            status = "<font color = '#00FF00'> Status : Active </font>";
            View adminEventView = Admin.inflater.inflate(R.layout.fragment_admintext, Admin.schedule.getScheduleLayout(), false);
            final CheckBox box = (CheckBox) adminEventView.findViewById(R.id.checkBox);
            final TextView admintext = (TextView) adminEventView.findViewById(R.id.textView);
            final Button undo = (Button) adminEventView.findViewById(R.id.uncancel_button);
            admintext.setText(Html.fromHtml(status));
            admintext.append(result.getMessage()[i]);
            Admin.schedule.getScheduleLayout().addView(adminEventView);

            checkboxes[i] = box;
            texts[i] = admintext;
            buttons[i] = undo;

            boolean isActive = true;
            String msg = result.getMessage()[i];
            if (msg != null) {
                msg = msg.trim();
                if (msg.indexOf("CANCELED:") == 0) {
                    isActive = false;
                }
            }
            if (!isActive) {
                status = "<font color = '#FF0000'> Status : Inactive </font>";

                checkboxes[i].setVisibility(View.INVISIBLE);
                buttons[i].setVisibility(View.VISIBLE);
                texts[i].setText(Html.fromHtml(status));
                texts[i].append(result.getMessage()[i]);
            }

        }

        for (int i = 0; i < checkboxes.length; i++) {
            final int finalI = i;
            checkboxes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean atLeastOne = false;
                    for (int z = 0; z < checkboxes.length; z++) {
                        if (checkboxes[z].isChecked()) {
                            atLeastOne = true;
                            Admin.schedule.getCancel().setVisibility(View.VISIBLE);
                        }
                    }
                    if (!atLeastOne) {
                        Admin.schedule.getCancel().setVisibility(View.INVISIBLE);
                    }
                }
            }
            );

        }


        Admin.schedule.getCancel().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                List<Integer> deletedItemIndexes = new ArrayList<Integer>();


                for (int i = 0; i < checkboxes.length; i++) {
                    final int finalI = i;
                    if (checkboxes[i].isChecked()) {
                        deletedItemIndexes.add(i);
                        status = "<font color = '#FF0000'> Status : Inactive </font>";
                        texts[i].setText(Html.fromHtml(status));
                        texts[i].append(result.getMessage()[i]);
                        checkboxes[i].setChecked(false);
                        checkboxes[i].setVisibility(View.INVISIBLE);
                        Admin.schedule.getCancel().setVisibility(View.INVISIBLE);
                        buttons[i].setVisibility(View.VISIBLE);

                    }
                }
                result.setToBeDeleted(deletedItemIndexes);
                new CancelEventTask().execute(result);




            }
        });

        for (int i = 0; i < buttons.length; i++) {
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Integer> UndeletedItemIndexes = new ArrayList<Integer>();

                    UndeletedItemIndexes.add(finalI);
                    buttons[finalI].setVisibility(View.INVISIBLE);
                    checkboxes[finalI].setVisibility(View.VISIBLE);

                    status = "<font color = '#00FF00'> Status : Active </font>";
                    texts[finalI].setText(Html.fromHtml(status));
                    texts[finalI].append(result.getMessage()[finalI]);

                    result.setToBeUndeleted(UndeletedItemIndexes);
                    new UndoCancelTask().execute(result);

                }
            }


            );
        }
    }
}
