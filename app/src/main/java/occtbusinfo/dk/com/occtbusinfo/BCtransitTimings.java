package occtbusinfo.dk.com.occtbusinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
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

public class BCtransitTimings extends AppCompatActivity {
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
    private int scrollCounter = 0, scrollTempCounter =0;
    private String filename;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bctransit_timings);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        bus = getIntent().getExtras().getString("Bus");
        location = getIntent().getExtras().getString("location");
        direction = getIntent().getExtras().getString("direction");
        loc = getIntent().getExtras().getInt("location_int");
        dir = getIntent().getExtras().getInt("direction_int");
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

        TextView route = (TextView) findViewById(R.id.route);
        route.setText("Selected bus route: " + bus + ", " + location);

        TextView currentTime = (TextView) findViewById(R.id.currentTime);
        currentTime.setText("Current Time: "+ localtime+", "+weekday);

        TextView complete = (TextView) findViewById(R.id.textView3);
        complete.setText("Complete list of Timings: \n"+bus+" at "+location);

        filename = getFileName(bus, loc, dir);
        final ListView listView= (ListView) findViewById(R.id.timingsList);
        try{
            InputStream inputreader = this.getAssets().open(filename);
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputreader));

            List<String> lines = new ArrayList<String>();
            boolean hasNextLine =true;
            while (hasNextLine){
                String line =  buffreader.readLine();
                if(line !=null) {
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

            TextView next = (TextView) findViewById(R.id.textView2);
            next.setText("Next bus at: "+nextBus);
            next.setTextSize(25);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BCtransitTimings.this,R.layout.spinner_item,lines);

            listView.setAdapter(adapter);

            listView.setSelection(scrollCounter);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setItemChecked(scrollCounter, true);

            //close file reader
            inputreader.close();

        }
        catch(java.io.FileNotFoundException e){

        }catch(java.io.IOException e){

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private String getFileName(String bus, int loc, int dir) {

        if(weekday.equals("Saturday") || weekday.equals("Sunday")) {

            switch (bus) {
                case "15":
                    if(weekday.equals("Saturday")) {
                        name = "b15_sat_" + dir + "_" + loc + ".txt";
                    }
                    else{
                        name = "b15_sun_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "57":
                    if(weekday.equals("Saturday")) {
                        name = "b57_sat_" + dir + "_" + loc + ".txt";
                    }
                    else{
                        name = "b57_sun_" + dir + "_" + loc + ".txt";
                    }
                    break;
            }

            return name;
        }

        else {
            switch (bus) {
                case "15":
                    name = "b15_" + dir + "_" + loc + ".txt";
                    break;
                case "16":
                    name = "b16_" + dir + "_" + loc + ".txt";
                    break;
                case "17":
                    name = "b17_" + dir + "_" + loc + ".txt";
                    break;
                case "57":
                    name = "b57_" + dir + "_" + loc + ".txt";
                    break;

            }

        }

        return name;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==android.R.id.home){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Bus", bus);
            setResult(form2.RESULT_OK, returnIntent);
            finish();
            return true;
        }

        if(id == R.id.action_about){
            /*LinearLayout linr = (LinearLayout)findViewById(R.id.linearLayout);
            TextView note = new TextView(MainActivity.this);
            note.setText("OCCT BUS INFO\n" +
                    "Created by Deepak Kaku.\n" +
                    "©All Rights Reserved");
            note.setTextSize(20);
            linr.addView(note);
            */

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("OCCT BUS INFO\nCreated by Deepak Kaku.\n©All Rights Reserved")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
