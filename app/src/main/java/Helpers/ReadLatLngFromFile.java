package Helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import occtbusinfo.dk.com.occtbusinfo.Main2Activity;

/**
 * Created by Deepak Kaku on 25-08-2017.
 */

public class ReadLatLngFromFile {

    private List<String> latLngList;
    private AssetManager assetManager;
    public ReadLatLngFromFile(Context myContext){
        assetManager = myContext.getAssets();
    }

    public List<String> getLatLngList(String filename) throws IOException {

        latLngList = new ArrayList<String>();
        try {
            InputStream inputreader = assetManager.open(filename);
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputreader));

            boolean hasNextLine = true;
            while (hasNextLine) {
                String line = buffreader.readLine();
                if (line != null) {
                    latLngList.add(line);
                }
                hasNextLine = line != null;
            }

            inputreader.close();
        }
        catch (IOException e){
            System.out.println("EXCEPTION OCCURED");
            e.printStackTrace();
        }
        return latLngList;
    }
}
