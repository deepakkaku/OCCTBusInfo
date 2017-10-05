package occtbusinfo.dk.com.occtbusinfo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements FavoriteInterface{

    SharedPreferences sp;
    private List<String> buses;

    private ListView busList;
    private TextView fav_bus_empty;
    boolean justCreated = true;
    boolean listEmpty = false;
    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        sp = getActivity().getSharedPreferences("key", Context.MODE_PRIVATE);
        busList = (ListView) view.findViewById(R.id.busList);
        fav_bus_empty =  (TextView) view.findViewById(R.id.empty_fav_bus);

        populateList();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!justCreated){
            populateList();
            if(listEmpty){
                busList.setVisibility(View.GONE);
                //setupIconInCenter();
                fav_bus_empty.setVisibility(View.VISIBLE);
            }
            else{
                busList.setVisibility(View.VISIBLE);
                //setupIconInCenter();
                fav_bus_empty.setVisibility(View.GONE);
            }
        }
        justCreated = false;

    }

    private void populateList(){
        Map<String,?> keys = sp.getAll();
        if(keys.isEmpty()){
            listEmpty = true;
        }
        else {
            listEmpty = false;
            buses = new ArrayList<String>();

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                // Log.d("map values",entry.getKey() + ": " +
                //       entry.getValue().toString());
                buses.add((String) entry.getKey());
            }
            Collections.sort(buses);
            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(), R.layout.listview_item, buses);
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
                        Intent intent = new Intent(getContext(), BusTimings.class);
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

    @Override
    public void fragmentBecameVisible() {
        populateList();
        if(listEmpty){
            busList.setVisibility(View.GONE);
            //setupIconInCenter();
            fav_bus_empty.setVisibility(View.VISIBLE);
        }
        else{
            busList.setVisibility(View.VISIBLE);
            //setupIconInCenter();
            fav_bus_empty.setVisibility(View.GONE);
        }

        //justCreated = false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_fav_buses,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.clear) {

            AlertDialog alertbox = new AlertDialog.Builder(getContext())
                    .setMessage("Are you sure you want to clear Favorites?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            sp.edit().clear().commit();
                            fragmentBecameVisible();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .show();


        }
        return super.onOptionsItemSelected(item);
    }

}
