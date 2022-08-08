package dignityonwheelsplus.org.locator;

import android.os.AsyncTask;

import com.google.api.services.calendar.model.Event;

import java.io.IOException;

import java.util.List;

public class CancelEventTask extends AsyncTask<Location, Void, Location> {

    @Override
    protected Location doInBackground(Location... locations) {
        Location currLoc = locations[0];
        if (currLoc == null){
            return null;
        }
        List<Integer> toBeDeleted = currLoc.getToBeDeleted();
        for (int i = 0; i < toBeDeleted.size(); i++) {
            int index = toBeDeleted.get(i);
            Event ev = new Event();
            String msg = currLoc.getMessage()[index];
            if (msg != null) {
                msg = msg.trim();
                if (msg.indexOf("CANCELED:") == 0) {
                    ev.setSummary(currLoc.getEvents().get(index).getSummary());
                } else {
                    ev.setSummary("CANCELED: " + currLoc.getEvents().get(index).getSummary());
                }
            }
            if (toBeDeleted.get(i) == null) {
                continue;
            }

            try {
                currLoc.getService().events().patch("dignityonwheels.org_ir9db41bmvs6s5g926c0od0guk@group.calendar.google.com", currLoc.getEvents().get(index).getId(), ev).execute();
                System.out.println("Event Cancelled: " + currLoc.getEvents().get(index).getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currLoc;
    }


}




