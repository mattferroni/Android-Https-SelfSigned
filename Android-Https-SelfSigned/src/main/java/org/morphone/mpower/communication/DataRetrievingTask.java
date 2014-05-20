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

    public DataRetrievingTask(Context context){
        super();
    }

    @Override
    protected Boolean doInBackground(String... par) {
        Log.d(TAG, "doInBackground");
        Boolean result = false;

        URL url = null;
        try {
            /**
             * Exception now: self-signed certificate!
             *
             * http://developer.android.com/training/articles/security-ssl.html#SelfSigned
             * https://www.owasp.org/index.php/Certificate_and_Public_Key_Pinning#Android
             */
            byte[] secret = null;

            TrustManager tm[] = { new PubKeyManager() };
            assert (null != tm);

            SSLContext context = SSLContext.getInstance("TLS");
            assert (null != context);
            context.init(null, tm, null);

            url = new URL("https://personal01.ferroni.me");
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        Log.d(TAG, "onPostExecute");
    }

}

