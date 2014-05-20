package org.morphone.mpower.communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.Boolean;import java.lang.Override;import java.lang.String;import java.lang.StringBuilder;import java.lang.Void;import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import org.morphone.mpower.communication.security.PubKeyManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * Temporary data retrieving task, used by the model retrieval test button
 *
 */
public class DataRetrievingTask extends AsyncTask<String, Void, Boolean>{

    private final String TAG = "DataRetrievingTask";
    private final String YOUR_HTTPS_URL = "https://ferroni.me";

    public DataRetrievingTask(Context context){
        super();
    }

    @Override
    protected Boolean doInBackground(String... par) {
        Log.d(TAG, "doInBackground launched");
        Boolean result = false;
        URL url = null;
        try {
            TrustManager tm[] = { new PubKeyManager() };
            assert (null != tm);

            SSLContext context = SSLContext.getInstance("TLS");
            assert (null != context);
            context.init(null, tm, null);

            url = new URL(YOUR_HTTPS_URL);
            assert (null != url);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            assert (null != connection);

            connection.setSSLSocketFactory(context.getSocketFactory());
            InputStreamReader stream= new InputStreamReader(connection.getInputStream());
            assert (null != stream);

            BufferedReader r = new BufferedReader(stream);
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            Log.d(TAG, total.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException exception while checking trusted server");
        } catch (IOException e) {
            Log.e(TAG, "IOException exception while checking trusted server");
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException exception while checking trusted server");
        } catch (KeyManagementException e) {
            Log.e(TAG, "KeyManagementException exception while checking trusted server");
        }
        return result;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        Log.d(TAG, "onPostExecute launched");
    }
    
}

