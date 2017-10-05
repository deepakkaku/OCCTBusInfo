package occtbusinfo.dk.com.occtbusinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.squareup.picasso.RequestCreator;

public class BCTransitActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bctransit);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MaterialListView mListView = (MaterialListView) findViewById(R.id.material_listview);
        //setupWindowAnimations();

        //15 bus card
        Card bus_15 = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("15")
                .setTitleGravity(Gravity.END)
                .setDescription("Leroy Street, Schubert st, Binghamton University ")
                .setDescriptionGravity(Gravity.END)
                .setDrawable(R.drawable.b15)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .endConfig()
                .build();
        bus_15.setTag("15");
        mListView.getAdapter().add(bus_15);

        //16 BU express
        Card bus_16 = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("16")
                .setTitleGravity(Gravity.END)
                .setDescription("BU Express - Binghamton University ")
                .setDescriptionGravity(Gravity.END)
                .setDrawable(R.drawable.b16)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .endConfig()
                .build();
        bus_16.setTag("16");
        mListView.getAdapter().add(bus_16);

        //17 bus card
        Card bus_17 = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("17")
                .setTitleGravity(Gravity.END)
                .setDescription("Johnson City, Binghamton University ")
                .setDescriptionGravity(Gravity.END)
                .setDrawable(R.drawable.b17)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .endConfig()
                .build();
        bus_17.setTag("17");
        mListView.getAdapter().add(bus_17);

        /*//35 bus card
        Card bus_35 = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("35")
                .setTitleGravity(Gravity.END)
                .setDescription("Main Street, Oakdale mall, Endicott")
                .setDescriptionGravity(Gravity.END)
                .setDrawable(R.drawable.b35)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .endConfig()
                .build();
        bus_35.setTag("35");
        mListView.getAdapter().add(bus_35);
        */
        //57 bus card
        Card bus_57 = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("57")
                .setTitleGravity(Gravity.END)
                .setDescription("Shopper's special, BU Union, Townsquare Mall")
                .setDescriptionGravity(Gravity.END)
                .setDrawable(R.drawable.b57)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .endConfig()
                .build();
        bus_57.setTag("57");
        mListView.getAdapter().add(bus_57);

        //listen to card which was pressed
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(Card card, int position) {

                    Log.d("CARD_TYPE", card.getTag().toString());
                    System.out.println("card pressed");
                    Intent intent = new Intent(BCTransitActivity.this, form2.class);
                    intent.putExtra("Bus", card.getTag().toString());
                    startActivity(intent);
            }

            @Override
            public void onItemLongClick(Card card, int position) {
                //Log.d("LONG_CLICK", card.getTag().toString());
                System.out.print("card long pressed");
            }
        });

        mListView.scrollToPosition(0);
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
