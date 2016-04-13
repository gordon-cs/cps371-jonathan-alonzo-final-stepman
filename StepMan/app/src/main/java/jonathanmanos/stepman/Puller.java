package jonathanmanos.stepman;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jonny and AJ on 4/1/2016.
 */
public class Puller extends AsyncTask<String, String, String> {


        private Exception exception;
        private String firstLine;
        private SharedPreferences mPrefs;

        protected String doInBackground(String... value) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(value[0]);

                urlConnection = (HttpURLConnection) url
                        .openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                int counter = 0;
                while (data != -1) {
                    counter++;
                    char current = (char) data;
                    data = isw.read();
                    System.out.println(current);
                    firstLine += current;
                    if(counter == 20)
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return firstLine;

        }

        protected void onPostExecute(String value) {
            mPrefs = ProfileActivity1.profile.getSharedPreferences("label", 0);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("http", firstLine).commit();
        }
    }

