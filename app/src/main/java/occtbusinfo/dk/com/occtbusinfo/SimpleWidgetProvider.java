package occtbusinfo.dk.com.occtbusinfo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Deepak Kaku on 10-10-2016.
 */
public class SimpleWidgetProvider extends AppWidgetProvider {
    private String bus;
    private String location;
    private String direction;
    private int loc;
    private int dir;
    private String weekday;
    private String hour, min, state;
    private String nextBus = "N.A";
    private int count = 0;
    private Date date2;
    private int scrollCounter = 0, scrollTempCounter = 0;
    private String filename;
    String name;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            //bus = getIntent().getExtras().getString("Bus");
            bus = "WS";
            //location = getIntent().getExtras().getString("location");
            location = "UDC";
            //direction = getIntent().getExtras().getString("direction");
            direction = "0";
            //loc = getIntent().getExtras().getInt("location_int");
            loc = 0;
            //dir = getIntent().getExtras().getInt("direction_int");
            dir = 0;
            //print weekday on the app
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());

            //get the timezone of the phone and show the time in 12hr format with Am and PM
            TimeZone tz = cal.getTimeZone();
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("hh:mm a");
            date.setTimeZone(tz);
            String localtime = date.format(currentLocalTime);

            /*TextView route = (TextView) findViewById(R.id.route);
            if(bus.equals("WS") || bus.equals("DCL")) {
                route.setText("Selected bus route: " + bus + ", " + location + ", " + direction);
            }
            else{
                route.setText("Selected bus route: " + bus + ", " + location);
            }
            */

            /*
            TextView currentTime = (TextView) findViewById(R.id.currentTime);
            currentTime.setText("Current Time: "+ localtime+", "+weekday);

            TextView complete = (TextView) findViewById(R.id.textView3);
            complete.setText("Complete list of Timings: \n"+bus+" at "+location);
            */
            filename = getFileName(bus, loc, dir);

            // final ListView listView= (ListView) findViewById(R.id.timingsList);
            try {
                InputStream inputreader = context.getAssets().open(filename);
                BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputreader));

                List<String> lines = new ArrayList<String>();
                boolean hasNextLine = true;
                while (hasNextLine) {
                    String line = buffreader.readLine();
                    if (line != null) {
                        lines.add(line);
                        String[] hourArray = line.split(":");
                        String[] minArray = hourArray[1].split(" ");
                        hour = hourArray[0];
                        min = minArray[0];
                        state = minArray[1];

                        nextBus = compareTime(line, localtime);
                    }
                    hasNextLine = line != null;
                    scrollTempCounter++;
                }

                //TextView next = (TextView) findViewById(R.id.textView_widget);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.simple_widget);
                remoteViews.setTextViewText(R.id.textView_widget, "Next bus at: " + nextBus);
                //next.setText("Next bus at: " + nextBus);
                //next.setTextSize(25);

                inputreader.close();

                Intent widget_intent = new Intent(context, SimpleWidgetProvider.class);
                widget_intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                widget_intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, widget_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.button_widget, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
                /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(SimpleWidgetProvider.this, R.layout.spinner_item, lines);

                listView.setAdapter(adapter);

                listView.setSelection(scrollCounter);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listView.setItemChecked(scrollCounter, true);
                */
                //close file reader

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private String compareTime(String readTime, String localtime) throws ParseException {

        String fileTime = convertTo24Hour(readTime);
        String currentTime = convertTo24Hour(localtime);

        String[] currentArray = currentTime.split(":");
        String[] fileArray = fileTime.split(":");

        //current timings
        int currentHour = Integer.parseInt(currentArray[0]);
        int currentMin = Integer.parseInt(currentArray[1]);

        int fileHour = Integer.parseInt(fileArray[0]);
        int fileMin = Integer.parseInt(fileArray[1]);

        if(fileHour > currentHour && count==0){
            nextBus = readTime;
            count++;
            scrollCounter = scrollTempCounter;
        }

        else if(fileHour==currentHour && fileMin>=currentMin && count==0){
            nextBus = readTime;
            count++;
            scrollCounter = scrollTempCounter;
        }

        else if(fileHour == 0){
            fileHour = fileHour + 24;
            if(fileHour > currentHour && count==0){
                nextBus = readTime;
                count++;
                scrollCounter = scrollTempCounter;
            }

            else if(fileHour==currentHour && fileMin>currentMin && count==0){
                nextBus = readTime;
                count++;
                scrollCounter = scrollTempCounter;
            }
        }

        return nextBus;
    }

    public static String convertTo24Hour(String Time) {
        DateFormat f1 = new SimpleDateFormat("hh:mm a"); //11:00 pm
        Date d = null;
        try {
            d = f1.parse(Time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("HH:mm");
        String x = f2.format(d); // "23:00"

        return x;
    }

    private String getFileName(String bus, int loc, int dir) {

        if (weekday.equals("Saturday") || weekday.equals("Sunday")) {

            switch (bus) {
                case "WS":
                    name = "ws_sat_" + dir + "_" + loc + ".txt";
                    break;
                case "DCR":
                    if (dir == 0) {
                        if (weekday.equals("Saturday")) {
                            name = "dcl_sat_" + dir + "_" + loc + ".txt";
                        } else if (weekday.equals("Sunday")) {
                            name = "dcl_sun_" + dir + "_" + loc + ".txt";
                        }
                    } else {
                        name = "dcl_sat_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "LRS":
                    if (weekday.equals("Saturday")) {
                        name = "lrs_sat_" + dir + "_" + loc + ".txt";
                    } else {
                        name = "lrs_sun_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "ITC":
                    name = "itc_sat_" + dir + "_" + loc + ".txt";
                    break;
                case "RRT":
                    name = "rrt_sat_" + dir + "_" + loc + ".txt";
                    break;
                case "CS":
                    name = "cs_" + dir + "_" + loc + ".txt";
                    break;
                case "UP":
                    name = "up_sat_" + dir + "_" + loc + ".txt";
                    break;
                case "UDC":
                    name = "udc_" + dir + "_" + loc + ".txt";
                    break;
                case "OAK":
                    name = "oak_sat_" + dir + "_" + loc + ".txt";
                    break;
                case "DTX":
                    name = "dtx_" + dir + "_" + loc + ".txt";
                    break;
            }

            return name;
        } else {
            switch (bus) {
                case "WS":
                    if (weekday.equals("Friday")) {
                        name = "ws_fri_" + dir + "_" + loc + ".txt";
                    } else {
                        name = "ws_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "DCR":
                    if (weekday.equals("Friday") && dir == 1) {
                        name = "dcl_fri_" + dir + "_" + loc + ".txt";
                    } else {
                        name = "dcl_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "LRS":
                    name = "lrs_" + dir + "_" + loc + ".txt";
                    break;
                case "ITC":
                    name = "itc_" + dir + "_" + loc + ".txt";
                    break;
                case "RRT":
                    if (weekday.equals("Friday")) {
                        name = "rrt_fri_" + dir + "_" + loc + ".txt";
                    } else {
                        name = "rrt_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "CS":
                    name = "cs_" + dir + "_" + loc + ".txt";
                    break;
                case "UP":
                    if (weekday.equals("Friday")) {
                        name = "up_fri_" + dir + "_" + loc + ".txt";
                    } else {
                        name = "up_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "UDC":
                    name = "udc_" + dir + "_" + loc + ".txt";
                    break;
                case "OAK":
                    name = "oak_" + dir + "_" + loc + ".txt";
                    break;
                case "DTX":
                    name = "dtx_" + dir + "_" + loc + ".txt";
                    break;

            }

        }

        return name;
    }
}
