package occtbusinfo.dk.com.occtbusinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import Adapters.ViewPagerAdapter;
import Helpers.NonSwipeableViewPager;

public class Main2Activity extends AppCompatActivity {
    /*private AdView mAdView;
    private MaterialRadioGroup radioGroup;
    private MaterialRadioButton radioButton;
    */
    //private ViewPager viewPager;

    private NonSwipeableViewPager nsViewPager;
    private SearchFragment searchFragment;
    private FavoritesFragment favoritesFragment;
    private BottomNavigationView mBottomNav;
    private MenuItem prevMenuItem;
    private ViewPagerAdapter adapter;
    private Menu mainMenu;
    int selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*MobileAds.initialize(getApplicationContext(), "ca-app-pub-7864484002418138~7464038206");
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
*/
        // Start loading the ad in the background.
        /*mAdView.loadAd(adRequest);

        radioGroup = (MaterialRadioGroup) findViewById(R.id.bus_provider);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                selected = group.indexOfChild(findViewById(checkedId));
            }
        });


        Button button = (Button) findViewById(R.id.bus_provider_btn);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = radioGroup.getCheckedRadioButtonId();
                if (x == -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setMessage("Please select a Bus Provider")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    switch (selected) {
                        case 0:
                            Intent intent1 = new Intent(Main2Activity.this, BCTransitActivity.class);
                            //intent.putExtra("Bus", card.getTag().toString());
                            startActivity(intent1);
                            break;
                        case 1:
                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            //intent.putExtra("Bus", card.getTag().toString());
                            startActivity(intent);
                            break;
                    }
                }
            }
        });
*/
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(Main2Activity.this, MainIntroActivity.class));
            Toast.makeText(Main2Activity.this, "©Deepak Kaku", Toast.LENGTH_LONG)
                    .show();
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();


        nsViewPager = (NonSwipeableViewPager) findViewById(R.id.viewPager);
        nsViewPager.setOffscreenPageLimit(2);
        nsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    mBottomNav.getMenu().getItem(0).setChecked(false);
                }

                mBottomNav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mBottomNav.getMenu().getItem(position);

                FavoriteInterface fragment = (FavoriteInterface) adapter.instantiateItem(nsViewPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(nsViewPager);
        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_search:
                        nsViewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_favorites:
                        nsViewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_map_view:
                        nsViewPager.setCurrentItem(2);
                        break;
                }

                return false;
            }
        });


    }


    private void setupViewPager(ViewPager viewPager)
    {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        searchFragment=new SearchFragment();
        adapter.addFragment(searchFragment);
        favoritesFragment = new FavoritesFragment();
        adapter.addFragment(favoritesFragment);
        MapsFragment mapsFragment = new MapsFragment();
        adapter.addFragment(mapsFragment);
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mainMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main_2, mainMenu);
        return true;
    }


    public Menu getMenu(){
        return mainMenu;
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

        if(id == R.id.action_favorite){
            Intent favIntent = new Intent(Main2Activity.this, FavoriteBuses.class);
            startActivity(favIntent);
        }

        if(id==R.id.clear){

            return false;

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



    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Activity onresume");

    }
}
