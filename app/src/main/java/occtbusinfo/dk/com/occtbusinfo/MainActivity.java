package occtbusinfo.dk.com.occtbusinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.RequestCreator;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

       // mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-7864484002418138/8940771406");

          //  mInterstitialAd.setAdListener(new AdListener() {
             //   @Override
               // public void onAdClosed() {
            //        requestNewInterstitial();
                //}
            //});
        //requestNewInterstitial();



            MaterialListView mListView = (MaterialListView) findViewById(R.id.material_listview);
            //setupWindowAnimations();

            //westside bus card
            Card ws = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("Westside")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Westside, Main street of Binghamton")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.ws)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            ws.setTag("WS");
            mListView.getAdapter().add(ws);

            //DCL bus card
            Card dcl = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("DCL")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Up-Down Leroy st & Downtown Center")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.dcl)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            dcl.setTag("DCL");
            mListView.getAdapter().add(dcl);

            //LRS bus card
            Card lrs = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("LRS")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Leroy, Riverside, University Plaza and back")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.lrs)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            lrs.setTag("LRS");
            mListView.getAdapter().add(lrs);

            //ITC bus card
            Card itc = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("ITC Shuttle")
                    .setTitleGravity(Gravity.END)
                    .setDescription("ITC Shuttle, every 20 mins")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.itc)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            itc.setTag("ITC");
            mListView.getAdapter().add(itc);

            //RRT bus card
            Card rrt = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("RRT Shuttle")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Riviera, Parkway pl, Walmart (TSM)")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.rrt)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            rrt.setTag("RRT");
            mListView.getAdapter().add(rrt);

            //Campus shuttle bus card
            Card cs = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("Campus Shuttle")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Susquehanna, Hillside, Mountain view")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.cs)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            cs.setTag("CS");
            mListView.getAdapter().add(cs);

            //UP shuttle
            Card up = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("UP Shuttle")
                    .setTitleGravity(Gravity.END)
                    .setDescription("University Plaza, Hayes, Washington Dr. ")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.up)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            up.setTag("UP");
            mListView.getAdapter().add(up);

            //udc shuttle
            Card udc = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("UDC")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Union to Downtown and back (No halts in between)")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.udc)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            udc.setTag("UDC");
            mListView.getAdapter().add(udc);

            //oakdale mall
            Card oak = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("Oakdale Mall")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Denny's, Oakdale Mall, Wegmans")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.oak)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            oak.setTag("OAK");
            mListView.getAdapter().add(oak);

            //downtown xpress
            Card dtx = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_image_buttons_card_layout)
                    .setTitle("Downtown Exp")
                    .setTitleGravity(Gravity.END)
                    .setDescription("Hayes, UP, Hawley & State")
                    .setDescriptionGravity(Gravity.END)
                    .setDrawable(R.drawable.dtx)
                    .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                        @Override
                        public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                            requestCreator.fit();
                        }
                    })
                    .endConfig()
                    .build();
            dtx.setTag("DTX");
            mListView.getAdapter().add(dtx);


            //listen to card which was pressed
            mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

                @Override
                public void onItemClick(Card card, int position) {
                   // if (mInterstitialAd.isLoaded()) {
                    //    mInterstitialAd.show();
                    //} else {
                        Log.d("CARD_TYPE", card.getTag().toString());
                        System.out.println("card pressed");
                        Intent intent = new Intent(MainActivity.this, form.class);
                        intent.putExtra("Bus", card.getTag().toString());
                        startActivity(intent);
                    //}
                }

                @Override
                public void onItemLongClick(Card card, int position) {
                    //Log.d("LONG_CLICK", card.getTag().toString());
                    System.out.print("card long pressed");
                }
            });

            mListView.scrollToPosition(0);


            //button.setEnabled(false);

            /** Uncomment to show a message at start
             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             builder.setMessage("OCCT Bus will be operational from 22nd August 2016. We will directly update the fall 2016 schedule soon. Sorry for the inconvenience.")
             .setCancelable(false)
             .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
             dialog.cancel();
             MainActivity.this.finish();
             }

             });

             AlertDialog alert = builder.create();
             alert.show();

             **/

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
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
