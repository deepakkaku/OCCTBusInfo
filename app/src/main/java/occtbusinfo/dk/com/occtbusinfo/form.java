package occtbusinfo.dk.com.occtbusinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class form extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinner;
    //private com.rey.material.widget.Spinner spn;
    private int location;
    private int direction;
    private String bus;
    int pos;
    private String weekday;
    private boolean weekend = false;
    HashMap spinnerData;
    ArrayAdapter<String> dataAdapter = null;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7864484002418138~7464038206");
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        bus = getIntent().getExtras().getString("Bus");
        Log.d("Card pressed", bus);
        radioGroup = (RadioGroup) findViewById(R.id.radioRoute);
        spinner = (Spinner) findViewById(R.id.spinner);
        TextView textView = (TextView) findViewById(R.id.selectedBus);
        textView.setText("Slected Bus: " + bus);
        radioButton = (RadioButton) findViewById(R.id.radioInbound);
        RadioButton radioOutbound = (RadioButton) findViewById(R.id.radioOutbound);
        radioGroup.clearCheck();
        if(bus.equals("WS")) {
            spinnerData = new HashMap();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    pos = group.indexOfChild(findViewById(checkedId));
                    direction = pos;
                    switch (pos) {
                        case 0:
                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("UDC");
                            spinnerData.put(0, "UDC");
                            locationListInbound.add("Main & Murray");
                            spinnerData.put(1, "Main & Murray");
                            locationListInbound.add("Floral & Main");
                            spinnerData.put(2, "Floral & Main");
                            locationListInbound.add("Academic A");
                            spinnerData.put(3, "Academic A");
                            locationListInbound.add("Returns");
                            spinnerData.put(4, "Returns");
                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                        case 1:
                            List<String> loctionListOutbound = new ArrayList<String>();
                            loctionListOutbound.add("Leaves Union");
                            spinnerData.put(0, "Union");
                            loctionListOutbound.add("Floral & Main");
                            spinnerData.put(1, "Floral & Main");
                            loctionListOutbound.add("Main & Murray");
                            spinnerData.put(2, "Main & Murray");
                            loctionListOutbound.add("Downtown Center");
                            spinnerData.put(3, "Downtown Center");
                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, loctionListOutbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                    }
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });


        }

        else if(bus.equals("DCL")){
            spinnerData = new HashMap();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    pos = group.indexOfChild(findViewById(checkedId));
                    direction = pos;
                    switch (pos) {
                        case 0:
                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("UDC");
                            spinnerData.put(0, "UDC");
                            locationListInbound.add("Leroy & Murray");
                            spinnerData.put(1, "Leroy & Murray");
                            locationListInbound.add("Riverside & Columbus");
                            spinnerData.put(2, "Riverside & Columbus");
                            locationListInbound.add("Academic A");
                            spinnerData.put(3, "Academic A");
                            locationListInbound.add("Returns");
                            spinnerData.put(4, "Returns");
                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                        case 1:
                            List<String> loctionListOutbound = new ArrayList<String>();
                            loctionListOutbound.add("Leaves Union");
                            spinnerData.put(0, "Union");
                            loctionListOutbound.add("Riverside & Columbus");
                            spinnerData.put(1, "Riverside & Columbus");
                            loctionListOutbound.add("Leroy & Murray");
                            spinnerData.put(2, "Leroy & Murray");
                            loctionListOutbound.add("Downtown Center");
                            spinnerData.put(3, "Downtown Center");
                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, loctionListOutbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                    }
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }

        else if(bus.equals("LRS")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();
                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("Leaves Union");
                            spinnerData.put(0, "Union");
                            locationListInbound.add("Main & Floral");
                            spinnerData.put(1, "Main & Floral");
                            locationListInbound.add("Leroy & Murray");
                            spinnerData.put(2, "Leroy & Murray");
                            locationListInbound.add("University Plaza");
                            spinnerData.put(3, "University Plaza");
                            locationListInbound.add("Returns");
                            spinnerData.put(4, "Returns");
                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }

        else if(bus.equals("ITC")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            if(weekday.equals("Saturday") || weekday.equals("Sunday")){
                spinner.setEnabled(false);
                weekend = true;
            }
            else {
                List<String> locationListInbound = new ArrayList<String>();
                locationListInbound.add("Leaves Mohawk");
                spinnerData.put(0, "Mohawk");
                locationListInbound.add("ITC");
                spinnerData.put(1, "ITC");
                locationListInbound.add("West Gym");
                spinnerData.put(2, "West Gym");
                locationListInbound.add("Academic A");
                spinnerData.put(3, "Academic A");
                locationListInbound.add("Returns");
                spinnerData.put(4, "Returns");
                dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        location = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        location = 0;
                    }
                });
            }
        }

        else if(bus.equals("RRT")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            List<String> locationListInbound = new ArrayList<String>();
            if(weekday.equals("Friday")){

                AlertDialog.Builder builder = new AlertDialog.Builder(form.this);
                builder.setMessage("RRT Doesn't go to Walmart(Town Square Mall) on Fridays!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }

                        });

                AlertDialog alert = builder.create();
                alert.show();


                locationListInbound.add("Leaves Union");
                spinnerData.put(0, "Union");
                locationListInbound.add("Riviera Ridge");
                spinnerData.put(1, "Riviera Ridge");
                locationListInbound.add("Parkway Plaza");
                spinnerData.put(2, "Parkway Plaza");
                locationListInbound.add("Returns");
                spinnerData.put(3, "Returns");
            }
            else {

                locationListInbound.add("Leaves Union");
                spinnerData.put(0, "Union");
                locationListInbound.add("Riviera Ridge");
                spinnerData.put(1, "Riviera Ridge");
                locationListInbound.add("Parkway Plaza");
                spinnerData.put(2, "Parkway Plaza");
                locationListInbound.add("Walmart (TSM)");
                spinnerData.put(3, "Walmart (TSM)");
                locationListInbound.add("Returns");
                spinnerData.put(4, "Returns");
            }
            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }
        /*
        else if(bus.equals("CS")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            if(weekday.equals("Saturday") || weekday.equals("Sunday")){
                weekend = true;
            }

            List<String> locationListInbound = new ArrayList<String>();
            locationListInbound.add("Leaves Mohawk");
            spinnerData.put(0, "Mohawk");
            locationListInbound.add("Susquehanna");
            spinnerData.put(1, "Susquehanna");
            locationListInbound.add("Hillside");
            spinnerData.put(2, "Hillside");
            locationListInbound.add("Mountain View");
            spinnerData.put(3, "Mountain View");
            locationListInbound.add("Returns");
            spinnerData.put(4, "Returns");
            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }
        */

        else if(bus.equals("CS")){

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            if(weekday.equals("Saturday") || weekday.equals("Sunday")){
                weekend = true;
            }
            if(weekend){

                spinnerData = new HashMap();
                TextView tv = (TextView) findViewById(R.id.direction);
                tv.setVisibility(View.GONE);
                radioGroup.removeAllViews();

                List<String> locationListInbound = new ArrayList<String>();
                locationListInbound.add("Leaves Mohawk");
                spinnerData.put(0, "Mohawk");
                locationListInbound.add("Susquehanna");
                spinnerData.put(1, "Susquehanna");
                locationListInbound.add("Hillside");
                spinnerData.put(2, "Hillside");
                locationListInbound.add("Mountain View");
                spinnerData.put(3, "Mountain View");
                locationListInbound.add("Returns");
                spinnerData.put(4, "Returns");
                dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        location = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        location = 0;
                    }
                });

                LinearLayout lin = (LinearLayout)findViewById(R.id.linearLayout);
                TextView note = new TextView(form.this);
                note.setText("NOTE: On weekends, the campus shuttle is performed in a blue bus and runs on a set schedule.\nIt will leave from Mohawk and will not service student health services.");
                note.setTextSize(20);
                lin.addView(note);
            }
            else {
                TextView tv = (TextView) findViewById(R.id.direction);
                tv.setVisibility(View.GONE);
                radioGroup.removeAllViews();

                TextView tv2 = (TextView)findViewById(R.id.yourLocation);
                tv2.setVisibility(View.GONE);

                Button bt = (Button)findViewById(R.id.btnDisplay);
                bt.setVisibility(View.GONE);

                Spinner sp = (Spinner)findViewById(R.id.spinner);
                sp.setVisibility(View.GONE);

                LinearLayout lin = (LinearLayout) findViewById(R.id.linearLayout);
                TextView note = new TextView(form.this);
                note.setText("The Campus Shuttle runs Monday through Friday from 7:00am – 12:30am on a continuous loop around campus.\n The buses will run more frequently during peak times. The route is performed in a green shuttle bus.\n You may request a stop at the following locations on campus:\n" +
                        "- Engineering building\n" +
                        "- Student Health Services\n" +
                        "- Newing\n" +
                        "- Couper Administration\n" +
                        "- East Gym\n" +
                        "- West Gym\n" +
                        "- McGuire Building\n" +
                        "- Clearview hall\n" +
                        "- Susquehanna\n" +
                        "- Hillside\n" +
                        "- Mountainview\n" +
                        "- Academic A\n");
                note.setTextSize(18);
                lin.addView(note);
            }
        }

        //UP shuttle
        else if(bus.equals("UP")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();

            List<String> locationListInbound = new ArrayList<String>();
            locationListInbound.add("Leaves Mohawk");
            spinnerData.put(0, "Mohawk");
            locationListInbound.add("University Plaza");
            spinnerData.put(1, "University Plaza");
            locationListInbound.add("Hayes & Meadows");
            spinnerData.put(2, "Hayes & Meadows");
            locationListInbound.add("Washington Dr.");
            spinnerData.put(3, "Washington Dr.");
            locationListInbound.add("Returns");
            spinnerData.put(4, "Returns");
            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }

        else if(bus.equals("UDC")){
            spinnerData = new HashMap();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    pos = group.indexOfChild(findViewById(checkedId));
                    direction = pos;
                    switch (pos) {
                        case 0:
                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("Leaves UDC");
                            spinnerData.put(0, "UDC");
                            locationListInbound.add("Union");
                            spinnerData.put(1, "Union");
                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                        case 1:
                            List<String> loctionListOutbound = new ArrayList<String>();
                            loctionListOutbound.add("Leaves Union");
                            spinnerData.put(0, "Union");
                            loctionListOutbound.add("UDC");
                            spinnerData.put(1, "UDC");

                            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, loctionListOutbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                    }
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }

        else if(bus.equals("OAK")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();

            List<String> locationListInbound = new ArrayList<String>();
            locationListInbound.add("Leaves Union");
            spinnerData.put(0, "Union");
            locationListInbound.add("Denny's");
            spinnerData.put(1, "Denny's");
            locationListInbound.add("Oakdale Mall");
            spinnerData.put(2, "Oakdale Mall");
            locationListInbound.add("Wegmans");
            spinnerData.put(3, "Wegmans");
            locationListInbound.add("Returns");
            spinnerData.put(4, "Returns");
            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }

        else if(bus.equals("DTX")){
            spinnerData = new HashMap();
            TextView tv = (TextView) findViewById(R.id.direction);
            tv.setVisibility(View.GONE);
            radioGroup.removeAllViews();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            if(weekday.equals("Saturday") || weekday.equals("Friday")){
                System.out.println("Weekend");
                weekend=true;
            }
            else{
                spinner.setEnabled(false);
            }

            List<String> locationListInbound = new ArrayList<String>();
            locationListInbound.add("Leaves Union");
            spinnerData.put(0, "Union");
            locationListInbound.add("Hayes");
            spinnerData.put(1, "Hayes");
            locationListInbound.add("University Plaza");
            spinnerData.put(2, "University Plaza");
            locationListInbound.add("Hawley & State");
            spinnerData.put(3, "Hawley & State");
            locationListInbound.add("Returns");
            spinnerData.put(4, "Returns");
            dataAdapter = new ArrayAdapter<String>(form.this, R.layout.spinner_item, locationListInbound);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    location = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    location = 0;
                }
            });
        }

        // attaching data adapter to spinner
        Button button = (Button)findViewById(R.id.btnDisplay);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = radioGroup.getCheckedRadioButtonId();
                if (x == -1 && (bus.equals("WS") || bus.equals("DCL"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(form.this);
                    builder.setMessage("Please select your direction \nInbound or Outbound")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Intent intent = new Intent(form.this, BusTimings.class);
                    String loc = (String) spinnerData.get(location);
                    intent.putExtra("location", loc);
                    intent.putExtra("location_int", location);
                    intent.putExtra("direction_int", direction);
                    String dir;
                    if (direction == 0) {
                        dir = "Inbound";
                    } else {
                        dir = "Outbound";
                    }
                    intent.putExtra("direction", dir);
                    intent.putExtra("Bus", bus);
                    startActivity(intent);
                }
            }
        });

        if(weekend==false && bus.equals("DTX")){
            LinearLayout linr = (LinearLayout)findViewById(R.id.linearLayout);
            TextView note = new TextView(form.this);
            note.setText("No Downtown Express on weekdays.\nServices only on Fridays and Saturdays.\nSelect Another bus from previous screen");
            note.setTextSize(20);
            linr.addView(note);
            button.setEnabled(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Downtown Express on weekdays.\nServices only on Fridays and Saturdays.\nSelect Another bus from previous screen")
                    .setCancelable(false)
                    .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            form.this.finish();
                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

        if(weekend==true && bus.equals("ITC")){
            LinearLayout linr = (LinearLayout)findViewById(R.id.linearLayout);
            TextView note = new TextView(form.this);
            note.setText("No " + bus + " bus service on weekends.\nSelect Another bus from previous screen");
            note.setTextSize(20);
            linr.addView(note);
            button.setEnabled(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No "+bus+" bus service on weekends.\nSelect Another bus from previous screen")
                    .setCancelable(false)
                    .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            form.this.finish();
                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
        /*
        else if(weekend==true && bus.equals("CS")){
            LinearLayout lin = (LinearLayout)findViewById(R.id.linearLayout);
            TextView note = new TextView(form.this);
            note.setText("NOTE: On weekends, the campus shuttle is performed in a blue bus and runs on a set schedule.\nIt will leave from Mohawk and will not service student health services.");
            note.setTextSize(20);
            lin.addView(note);
        }
        */

    }



    private void addListenerRadioButton() {

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                bus = getIntent().getExtras().getString("Bus");
                //Update List
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//o


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
