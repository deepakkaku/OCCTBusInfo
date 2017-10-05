package occtbusinfo.dk.com.occtbusinfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;



public class FavoriteBuses extends AppCompatActivity {

    SharedPreferences sp;
    private List<String> buses;

    private ListView busList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_buses);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        sp =getSharedPreferences("key", Context.MODE_PRIVATE);
        busList = (ListView) findViewById(R.id.busList);
        Map<String,?> keys = sp.getAll();
        if(keys.isEmpty()){
            busList.setVisibility(View.GONE);
            setupIconInCenter();
        }
        else {

            buses = new ArrayList<String>();

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                // Log.d("map values",entry.getKey() + ": " +
                //       entry.getValue().toString());
                buses.add((String) entry.getKey());
            }
            Collections.sort(buses);
            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.listview_item, buses);
            busList.setAdapter(listAdapter);

            busList.setSelection(0);
            busList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            busList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedFromList = busList.getItemAtPosition(position).toString().trim();
                    String preffered = sp.getString(selectedFromList,"");
                    if(preffered!=null){
                        String[] tokenStrings = selectedFromList.split(", ");
                        String[] tokens = preffered.split(":");
                        Intent intent = new Intent(FavoriteBuses.this, BusTimings.class);
                        intent.putExtra("location", tokenStrings[2]);
                        intent.putExtra("location_int", Integer.parseInt(tokens[2]));
                        intent.putExtra("direction_int", Integer.parseInt(tokens[1]));
                        String dir;
                        if (Integer.parseInt(tokens[1]) == 0) {
                            dir = "Inbound";
                        } else {
                            dir = "Outbound";
                        }
                        intent.putExtra("direction", dir);
                        intent.putExtra("Bus", tokenStrings[0]);
                        startActivity(intent);
                    }

                }
            });

        }

    }

    private void setupIconInCenter() {
        RelativeLayout rl=new RelativeLayout(this);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

       // lp.addRule(LinearLayout.CENTER_IN_PARENT);

        TextView textView = new TextView(this);
        textView.setText("You don't have any favorite buses yet!\nGo back and add few buses to your favorite list.");
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER);

        rl.addView(textView);
        setContentView(rl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fav_buses, menu);
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

        if(id==R.id.clear){

            AlertDialog alertbox = new AlertDialog.Builder(FavoriteBuses.this)
                    .setMessage("Are you sure you want to clear Favorites?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            sp.edit().clear().commit();
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .show();



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
