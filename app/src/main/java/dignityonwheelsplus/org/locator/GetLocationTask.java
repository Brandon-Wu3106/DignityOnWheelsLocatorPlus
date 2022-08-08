//package dignityonwheels.org.locator;
//
//import android.os.AsyncTask;
//import android.text.Html;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.CheckBox;
//
//public class GetLocationTask extends AsyncTask<Integer, Void, Location> {
//
//    protected Location doInBackground(Integer... args) {
//        return new Location(args[0]);
//    }
//
//    protected void onPostExecute(Location result) {
//        TextView text;
//
//        for (int i = 0; i < result.getMessage().length; i++) {
//
//            String status = "<font color = '#00FF00'> Status : Active </font>";
//            text = (TextView) MainActivity.inflater.inflate(R.layout.fragment_text, MainActivity.schedule.getScheduleLayout(), false);
//            text.setText(Html.fromHtml(status));
//            text.append(result.getMessage()[i]);
//            MainActivity.schedule.getScheduleLayout().addView(text);
//
//        }
//    }
//}
