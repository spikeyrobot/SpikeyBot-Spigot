package com.CampbellCrowley.SpikeyBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsRequest implements Runnable {
  private String https_url;
  private Callback cb;

  public HttpsRequest(final String https_url, Callback cb) {
    this.https_url = https_url;
    this.cb = cb;
  }

  public void run() {
    String ret = "";
    URL url;
    try {
      HttpsURLConnection con;
      url = new URL(https_url);
      con = (HttpsURLConnection) url.openConnection();
      InputStreamReader in = new InputStreamReader(con.getInputStream());
      BufferedReader buff = new BufferedReader(in);
      String line;
      while ((line = buff.readLine()) != null) {
        ret += line;
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.cb.callback(ret);
  }
}
