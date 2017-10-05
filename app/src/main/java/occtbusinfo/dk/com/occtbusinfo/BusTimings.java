package occtbusinfo.dk.com.occtbusinfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



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


public class BusTimings extends AppCompatActivity  {
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
    private String reference;
    private String referenceValue;
    private Menu mMenu;
    private boolean isFilled = false;
    String name;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_timings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        sp =getSharedPreferences("key", Context.MODE_PRIVATE);

        bus = getIntent().getExtras().getString("Bus");
        location = getIntent().getExtras().getString("location");
        direction = getIntent().getExtras().getString("direction");
        loc = getIntent().getExtras().getInt("location_int");
        dir = getIntent().getExtras().getInt("direction_int");

        reference = bus+", "+direction+", "+location;
        referenceValue = bus+":"+dir+":"+loc;
        String getFavRoute = sp.getString(reference,"");

        if(getFavRoute.equals("")){
            isFilled=false;
        }
        else if(getFavRoute.equals(referenceValue)){
            isFilled=true;
        }
       // setActionIcon(isFilled);
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
        if(bus.equals("WS") || bus.equals("DCL")) {
            route.setText("Selected bus route: " + bus + ", " + location + ", " + direction);
        }
        else{
            route.setText("Selected bus route: " + bus + ", " + location);
        }
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


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BusTimings.this,R.layout.spinner_item,lines);

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

        if(weekday.equals("Saturday") || weekday.equals("Sunday")) {

            switch (bus) {
                case "WS":
                    if(dir==1 && weekday.equals("Sunday")){
                        name = "ws_sun_" + dir + "_" + loc + ".txt";
                    }
                    else{
                        name = "ws_sat_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "DCL":

                        if (weekday.equals("Saturday")) {
                            name = "dcl_sat_" + dir + "_" + loc + ".txt";
                        }
                        else if (weekday.equals("Sunday")) {
                            name = "dcl_sun_" + dir + "_" + loc + ".txt";
                        }

                    break;
                case "LRS":
                    if(weekday.equals("Saturday")) {
                        name = "lrs_sat_" + dir + "_" + loc + ".txt";
                    }
                    else{
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
                    if(weekday.equals("Sunday")){
                        name = "up_sun_" + dir + "_" + loc + ".txt";
                    }
                    else {
                        name = "up_sat_" + dir + "_" + loc + ".txt";
                    }
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
        }
        else {
            switch (bus) {
                case "WS":
                    if(weekday.equals("Friday")){
                        name = "ws_fri_" + dir + "_" + loc + ".txt";
                    }
                    else {
                        name = "ws_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "DCL":
                    if(weekday.equals("Friday")){
                        name = "dcl_fri_" + dir + "_" + loc + ".txt";
                    }
                    else {
                        name = "dcl_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "LRS":
                    if(weekday.equals("Friday")){
                        name = "lrs_fri_" + dir + "_" + loc + ".txt";
                    }
                    else {
                        name = "lrs_" + dir + "_" + loc + ".txt";
                    }
                    break;
                case "ITC":
                    name = "itc_" + dir + "_" + loc + ".txt";
                    break;
                case "RRT":
                    /*if(weekday.equals("Friday")){
                        name = "rrt_fri_" + dir + "_" + loc + ".txt";
                    }
                    else{*/
                        name = "rrt_" + dir + "_" + loc + ".txt";
                    //}
                    break;
                case "CS":
                    name = "cs_" + dir + "_" + loc + ".txt";
                    break;
                case "UP":
                    if(weekday.equals("Friday")){
                        name = "up_fri_" + dir + "_" + loc + ".txt";
                    }
                    else {
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
                    if(weekday.equals("Friday")){
                        name = "dtx_fri_" + dir + "_" + loc + ".txt";
                    }
                    else {
                        name = "dtx_" + dir + "_" + loc + ".txt";
                    }
                        break;

            }

        }

        return name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_timings, menu);
        mMenu = menu;
        setActionIcon(isFilled);
        return true;
    }


    // For example - Call when you need to change icon
    private void setActionIcon(boolean isFilled)
    {
        MenuItem item = mMenu.findItem(R.id.action_favorite);

        if(mMenu != null)
        {
            if(isFilled)
            {
                item.setIcon(R.drawable.ic_heart_white_36dp);

            }
            else
            {
                item.setIcon(R.drawable.ic_heart_outline_white_36dp);
            }
        }
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
            setResult(form.RESULT_OK, returnIntent);
            finish();
            return true;
        }

        if(id==R.id.action_favorite){
            if(isFilled){
                isFilled = false;
                SharedPreferences.Editor ed=sp.edit();
                ed.remove(reference);
                ed.commit();
                Toast.makeText(BusTimings.this, R.string.remove_from_favs,
                        Toast.LENGTH_SHORT).show();
            }
            else{
                isFilled = true;
                SharedPreferences.Editor ed=sp.edit();
                ed.putString(reference,referenceValue);
                ed.commit();
                Toast.makeText(BusTimings.this, R.string.added_to_favs,
                        Toast.LENGTH_SHORT).show();
            }

            setActionIcon(isFilled);

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
