package occtbusinfo.dk.com.occtbusinfo;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdView;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment  implements FavoriteInterface{

    //private AdView mAdView;
    private MaterialRadioGroup radioGroup;
    private MaterialRadioButton radioButton;
    int selected;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view,savedInstance);

        radioGroup = (MaterialRadioGroup) view.findViewById(R.id.bus_provider);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                selected = group.indexOfChild(getActivity().findViewById(checkedId));
            }
        });

        Button button = (Button) view.findViewById(R.id.bus_provider_btn);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = radioGroup.getCheckedRadioButtonId();
                if (x == -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                            Intent intent1 = new Intent(getContext(), BCTransitActivity.class);
                            //intent.putExtra("Bus", card.getTag().toString());
                            startActivity(intent1);
                            break;
                        case 1:
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            //intent.putExtra("Bus", card.getTag().toString());
                            startActivity(intent);
                            break;
                    }
                }
            }
        });


    }

    @Override
    public void fragmentBecameVisible() {

    }
}
