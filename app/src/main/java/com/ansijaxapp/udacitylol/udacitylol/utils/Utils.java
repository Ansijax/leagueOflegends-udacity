package com.ansijaxapp.udacitylol.udacitylol.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Massimo on 24/05/15.
 */
public class Utils {


    public static String bufferToString(InputStream inputStream){
        BufferedReader reader = null;

        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UTILS", "Error ", e);
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("UTILS", "Error closing stream", e);
                }
            }

        }

        if (buffer.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }

        return buffer.toString();


    }

}
