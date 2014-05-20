package org.morphone.mpower.communication.security;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.morphone.mpower.app.Application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Exception;import java.lang.Override;import java.lang.String;import java.math.BigInteger;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by matt on 19/05/14.
 */
public final class PubKeyManager implements X509TrustManager {

    private final String TAG = "PubKeyManager";
    private X509Certificate[] trustedCerts = new X509Certificate[1];

    public PubKeyManager(){
        InputStream serverCertStream = null;
        try{
            serverCertStream = Application.getInstance().getAssets().open("server.cert");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(serverCertStream);
            trustedCerts[0] = cert;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Certificate not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "I/O exception");
            e.printStackTrace();
        } catch (CertificateException e) {
            Log.e(TAG, "Certificate exception");
            e.printStackTrace();
        } finally {
            try {
                serverCertStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Cannot close the certificate file");
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return trustedCerts;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) {
        // No client authentication
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        boolean match = false;
        try{
            for(X509Certificate c : chain){
                if(c.equals(trustedCerts[0])){
                    match = true;
                }
            }
        }catch (Exception e){
            Log.e(TAG, "Generic exception while checking server trusted");
            e.printStackTrace();
        }

        if(!match)
            throw new CertificateException("Certificate doesn't match");
    }

}