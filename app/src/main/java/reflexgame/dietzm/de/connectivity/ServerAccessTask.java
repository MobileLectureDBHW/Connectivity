package reflexgame.dietzm.de.connectivity;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michael on 08.10.15.
 */
public class ServerAccessTask extends AsyncTask<String, Void, String>{

    private final MainActivity mainactivity;

    public ServerAccessTask(MainActivity mainActivity) {
        this.mainactivity = mainActivity;
        
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            InputStream is = connection.getInputStream();

            String content = convertStreamToString(is);
            System.out.println(content);

            is.close();
            connection.disconnect();

            return content;

        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        mainactivity.serverCallFinished(s);
    }

    public String convertStreamToString(InputStream is) throws Exception{
        String content = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";

        while((line = br.readLine()) != null){
            content += line + "\n";
        }

        return content;
    }

 }

