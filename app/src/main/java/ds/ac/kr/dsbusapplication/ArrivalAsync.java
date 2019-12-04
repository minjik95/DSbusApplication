package ds.ac.kr.dsbusapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArrivalAsync extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadByUrl(urls[0]);
        } catch (Exception e) {
            Log.d("에러", "" + e.getMessage());
            return "Download failed";
        }
    }

    public String downloadByUrl(String apiUrl) throws IOException {
        HttpURLConnection conn = null;
        BufferedReader bufferedReader;
        String data;

        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection)url.openConnection();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
            String line = null;
            data = "";
            while((line = bufferedReader.readLine()) != null) {
                data += line;
            }

            return data;

        } finally {
            conn.disconnect();
        }
    }

}
