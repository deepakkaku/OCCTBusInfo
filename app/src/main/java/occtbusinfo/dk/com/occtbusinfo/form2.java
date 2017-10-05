package occtbusinfo.dk.com.occtbusinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class form2 extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinner;
    private TextView sel;
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
        setContentView(R.layout.activity_form2);

        sel = (TextView)findViewById(R.id.direction);

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

        TextView tv = (TextView)findViewById(R.id.ib);
        if(bus.equals("17")){
            assert tv != null;
            tv.setText("Note: For 17 Inbound means Bus will go towards BU Union, while Outbound means bus will leave from BU Union");
        }

        if(bus.equals("16")){
            assert tv !=null;
            tv.setText("Note: For 16, Inbound(Eastbound) goes from Floral to Hawley st and then to BU. Outbound(Westbound) goes from Front street to Floral and then BU Union.");
        }

        if(bus.equals("15")) {
            spinnerData = new HashMap();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    pos = group.indexOfChild(findViewById(checkedId));
                    direction = pos;
                    switch (pos) {
                        case 0:
                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("BU Union");
                            spinnerData.put(0, "Union");
                            locationListInbound.add("Floral & Burbank");
                            spinnerData.put(1, "Floral & Burbank");
                            locationListInbound.add("B.C. Junction");
                            spinnerData.put(2, "B.C. Junction");
                            dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, locationListInbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                        case 1:
                            List<String> loctionListOutbound = new ArrayList<String>();
                            loctionListOutbound.add("B.C. Junction");
                            spinnerData.put(0, "B.C. Junction");
                            loctionListOutbound.add("Floral & Burbank");
                            spinnerData.put(1, "Floral & Burbank");
                            loctionListOutbound.add("BU Union");
                            spinnerData.put(2, "Union");

                            dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, loctionListOutbound);
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

        else if(bus.equals("16")){
            spinnerData = new HashMap();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    pos = group.indexOfChild(findViewById(checkedId));
                    direction = pos;
                    switch (pos) {
                        case 0:
                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("Main & Floral");
                            spinnerData.put(0, "Main & Floral");
                            locationListInbound.add("Hawley");
                            spinnerData.put(1, "Hawley");
                            locationListInbound.add("BU Union");
                            spinnerData.put(2, "Union");
                            dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, locationListInbound);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                            break;
                        case 1:
                            List<String> loctionListOutbound = new ArrayList<String>();
                            loctionListOutbound.add("Main & Front");
                            spinnerData.put(0, "Main & Front");
                            loctionListOutbound.add("Main & Floral");
                            spinnerData.put(1, "Main & Floral");
                            loctionListOutbound.add("BU Union");
                            spinnerData.put(2, "Union");

                            dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, loctionListOutbound);
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

        else if(bus.equals("17")){
            spinnerData = new HashMap();
            radioGroup.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
            sel.setVisibility(View.GONE);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            if(weekday.equals("Saturday") || weekday.equals("Sunday")){
                spinner.setEnabled(false);
                weekend = true;
            }
            direction = 0;

                            List<String> locationListInbound = new ArrayList<String>();
                            locationListInbound.add("BU Union");
                            spinnerData.put(0, "Union");
                            locationListInbound.add("Oakdale/Wegmans");
                            spinnerData.put(1, "Oakdale/Wegmans");
                            locationListInbound.add("Main & Willow");
                            spinnerData.put(2, "Main & Willow");
                            locationListInbound.add("BU Union");
                            spinnerData.put(3, "Union");

                            dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, locationListInbound);
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

        //form for 57 bus
        else if(bus.equals("57")){
            spinnerData = new HashMap();
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            Calendar cal = Calendar.getInstance();
            weekday = dayFormat.format(cal.getTime());
            if(weekday.equals("Saturday") || weekday.equals("Sunday")){
                //spinner.setEnabled(false);
                weekend = true;
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    pos = group.indexOfChild(findViewById(checkedId));
                    direction = pos;
                    if (weekday.equals("Saturday") || weekday.equals("Sunday") ) {
                        switch (pos) {
                            case 0:
                                List<String> locationListInbound = new ArrayList<String>();
                                locationListInbound.add("Oakdale Mall");
                                spinnerData.put(0, "Oakdale Mall");
                                locationListInbound.add("BC Junction");
                                spinnerData.put(1, "BC Junction");

                                dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, locationListInbound);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(dataAdapter);
                                break;
                            case 1:
                                List<String> loctionListOutbound = new ArrayList<String>();
                                loctionListOutbound.add("BC Junction");
                                spinnerData.put(0, "BC Junction");
                                loctionListOutbound.add("Townsquare Mall");
                                spinnerData.put(1, "Townsquare Mall");
                                loctionListOutbound.add("Oakdale Mall");
                                spinnerData.put(2, "Oakdale Mall");
                                dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, loctionListOutbound);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(dataAdapter);
                                break;
                        }
                    } else {
                        switch (pos) {
                            case 0:
                                List<String> locationListInbound = new ArrayList<String>();
                                locationListInbound.add("Townsquare Mall");
                                spinnerData.put(0, "Townsquare Mall");
                                locationListInbound.add("BU Union");
                                spinnerData.put(1, "Union");
                                locationListInbound.add("Lrds Hospital");
                                spinnerData.put(2, "Lrds Hospital");
                                locationListInbound.add("BC Junction");
                                spinnerData.put(3, "BC Junction");

                                dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, locationListInbound);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(dataAdapter);
                                break;
                            case 1:
                                List<String> loctionListOutbound = new ArrayList<String>();
                                loctionListOutbound.add("BC Junction");
                                spinnerData.put(0, "BC Junction");
                                loctionListOutbound.add("Lrds Hospital");
                                spinnerData.put(1, "Lrds Hospital");
                                loctionListOutbound.add("BU Union");
                                spinnerData.put(2, "Union");
                                loctionListOutbound.add("Townsquare Mall");
                                spinnerData.put(3, "Townsquare Mall");
                                dataAdapter = new ArrayAdapter<String>(form2.this, R.layout.spinner_item, loctionListOutbound);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(dataAdapter);
                                break;
                        }
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


        // attaching data adapter to spinner
        Button button = (Button)findViewById(R.id.btnDisplay2);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = radioGroup.getCheckedRadioButtonId();
                if (x == -1 && (bus.equals("WS") || bus.equals("DCL"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(form2.this);
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
                    Intent intent = new Intent(form2.this, BCtransitTimings.class);
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

        if(weekend==true && bus.equals("17")){
            LinearLayout linr = (LinearLayout)findViewById(R.id.linearLayout);
            TextView note = new TextView(form2.this);
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
                            form2.this.finish();
                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

        if(weekend==true && bus.equals("16")){
            LinearLayout linr = (LinearLayout)findViewById(R.id.linearLayout);
            TextView note = new TextView(form2.this);
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
                            form2.this.finish();
                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

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
