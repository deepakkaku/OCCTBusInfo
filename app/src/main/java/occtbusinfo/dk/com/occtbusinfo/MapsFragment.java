package occtbusinfo.dk.com.occtbusinfo;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import Helpers.ReadLatLngFromFile;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements FavoriteInterface, OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private List<String> routes;
    private List<String> latLngList;
    private Spinner routeSpinner;
    private int routeNumber = 0;
    private WebView mWebView;
    private TextView emptyMapText;

    // private MapContainerView mapView;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);


        mWebView = (WebView) view.findViewById(R.id.mapWebView);
        mWebView.loadUrl("http://binghamtonupublic.etaspot.net/");

        loadMapView();

        //read latitudes and longitudes from file
       /* try {
            selectRouteOnMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        routeSpinner = (Spinner) view.findViewById(R.id.busRouteSpinner);

*/
        emptyMapText = (TextView) view.findViewById(R.id.empty_map);
        return view;
    }

    private void loadMapView() {
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onViewCreated(View view, Bundle savdedInstance) {
        super.onViewCreated(view, savdedInstance);

        /*populateSpinner();

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int route, long l) {
                routeNumber = route;
                //read latitudes and longitudes from file
                try {
                    selectRouteOnMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        */
    }

    private void selectRouteOnMap() throws IOException {
        ReadLatLngFromFile readLatLngFromFile = new ReadLatLngFromFile(getContext());

        switch (routeNumber){
            //ws
            case 0:
                latLngList = readLatLngFromFile.getLatLngList("WSOut_Stops.txt");
                break;
            //DCL
            case 1:
                latLngList = readLatLngFromFile.getLatLngList("WSIn_Stops.txt");
                break;
            //LRS
            case 2:
                latLngList = readLatLngFromFile.getLatLngList("LRS_Stops.txt");
                break;
            //ITC
            case 3:
                latLngList = readLatLngFromFile.getLatLngList("ITC_Stops.txt");
                break;
            //RRT
            case 4:
                latLngList = readLatLngFromFile.getLatLngList("RRT_Stops.txt");
                break;
            //CS
            case 5:
                latLngList = readLatLngFromFile.getLatLngList("CS_Stops.txt");
                break;
            //UP
            case 6:
                latLngList = readLatLngFromFile.getLatLngList("UP_Stops.txt");
                break;
            //UDC
            case 7:
                latLngList = readLatLngFromFile.getLatLngList("UDC_Stops.txt");
                break;
            //OAK
            case 8:
                latLngList = readLatLngFromFile.getLatLngList("OAK_Stops.txt");
                break;
            //DTX
            case 9:
                latLngList = readLatLngFromFile.getLatLngList("DTX_Stops.txt");
                break;
            default:

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        populateMap();
    }

    private void populateSpinner(){
        List<String> routes = new ArrayList<String>();
        routes.add("WS Outbound/DCL Inbound");
        /*routes.add("WS Inbound/DCL Outbound");
        routes.add("LRS");
        routes.add("ITC");
        routes.add("RRT");
        routes.add("CS");
        routes.add("UP");
        routes.add("UDC");
        routes.add("OAK");
        routes.add("DTX");*/

        ArrayAdapter<String> routeSpinnerAdapater = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,routes);
        routeSpinnerAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        routeSpinner.setAdapter(routeSpinnerAdapater);

    }
    @Override
    public void fragmentBecameVisible() {
        /*Boolean isFirstRun = getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstMapRun", true);

        if (isFirstRun) {
            //show start activity
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Map View is still in Beta Stage and will be updated with all bus routes in upcoming updates.\nThank you for your Co-operation and patience.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();

            getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstMapRun", false).commit();
        }
        */


    }



    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        populateMap();
    }

    private void populateMap() {
        if (googleMap != null && !(latLngList.isEmpty())) {

          /*  // For dropping a marker at a point on the Map
            LatLng sydney = new LatLng(42.099659,-75.921767);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("WS Outbound").snippet("9:03 AM").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); */

          for(String s : latLngList){
              String latlng[] = s.split(",");
              double lat = Double.parseDouble(latlng[0]);
              double lng = Double.parseDouble(latlng[1]);
              String title = latlng[2];
              LatLng stop = new LatLng(lat, lng);
              googleMap.addMarker(new MarkerOptions()
                                        .position(stop)
                                        .title(title)
                                        .snippet("Time coming soon")
                                        .flat(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_custom_map_pin))
              );
          }

            LatLng sydney = new LatLng(42.099659,-75.921767);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
