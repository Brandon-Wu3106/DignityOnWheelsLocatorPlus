package dignityonwheelsplus.org.locator;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class Location {
    private int day;
    private String[] message;
    private List<Event> events;
    private Calendar service;
    private List<Integer> toBeDeleted;
    private List<Integer> toBeUndeleted;


    private static String cleanDateTime(DateTime dateTime) {
        Date date = new Date(dateTime.getValue());

        DateFormat format = new SimpleDateFormat();

        return format.format(date);
    }

    public Location(int day) {
        this.day = day;

        try {
            HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = AndroidJsonFactory.getDefaultInstance();

            GoogleCredential credential = GoogleCredential.fromStream(MainActivity.assets.open("dignity-on-wheels-locator-v2-b119a36d5a67.json"))
                    .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

            service = new Calendar.Builder(httpTransport, jsonFactory, credential).build();
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Date lower = new Date(System.currentTimeMillis());
            lower.setDate(lower.getDate() + day);
            lower.setHours(1);
            lower.setMinutes(0);
            lower.setSeconds(0);

            Date upper = (Date) lower.clone();
            upper.setHours(22);

            Events list = service.events().list("dignityonwheels.org_ir9db41bmvs6s5g926c0od0guk@group.calendar.google.com")
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .setTimeMax(new DateTime(upper))
                    .setTimeMin(new DateTime(lower))
                    .execute();

            events = list.getItems();
            message = new String[events.size()];

            for(int i = 0; i < events.size(); i++) {
                System.out.println(events.get(i).getId() + "\n" + events.get(i).getSummary());

                if (events.get(i).getSummary() != null) {
                    message[i] = "\n" + events.get(i).getSummary()  + "\n" + "\n";
                }

                message[i] += cleanDateTime(events.get(i).getStart().getDateTime()) + " - " + cleanDateTime(events.get(i).getEnd().getDateTime()) + "\n";

                if (events.get(i).getLocation() != null) {
                    message[i] += "\n" + events.get(i).getLocation();
                }

//                if (events.get(i).getId().equals("2gn4suhkmfvra8ofrffbl05oj5_20200629T150000Z")) {
//                    Event ev = new Event();
//                    ev.setSummary("CANCELED: " + events.get(i).getSummary());
//                    //events.get(i).setSummary(events.get(i).getSummary());
//                    // Event updatedEvent = service.events().update("dignityonwheels.org_ir9db41bmvs6s5g926c0od0guk@group.calendar.google.com", events.get(i).getId(),events.get(i)).execute();
//                    // System.out.println(updatedEvent.getUpdated());
//
//                    service.events().patch("dignityonwheels.org_ir9db41bmvs6s5g926c0od0guk@group.calendar.google.com", events.get(i).getId(), ev).execute();
//                    // System.out.println(updatedEvent.getUpdated());// Event updatedEvent = service.events().update("dignityonwheels.org_ir9db41bmvs6s5g926c0od0guk@group.calendar.google.com", events.get(i).getId(),events.get(i)).execute();
//                    System.out.println("Event updated");
//                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Event> getEvents(){
        return events;
    }

    public Calendar getService(){
        return service;
    }

    public String[] getMessage() { return message;
    }

    public void setToBeDeleted (List<Integer> toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }

    public List<Integer> getToBeDeleted(){
        return toBeDeleted;
    }

    public void setToBeUndeleted (List<Integer> toBeUndeleted) {
        this.toBeUndeleted = toBeUndeleted;
    }

    public List<Integer> getToBeUndeleted(){
        return toBeUndeleted;
    }
}

