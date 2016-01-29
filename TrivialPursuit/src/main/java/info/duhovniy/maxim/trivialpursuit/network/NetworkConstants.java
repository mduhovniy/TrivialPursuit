package info.duhovniy.maxim.trivialpursuit.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import info.duhovniy.maxim.trivialpursuit.data.DataConstant;
import info.duhovniy.maxim.trivialpursuit.data.Question;

/**
 * Created by maxduhovniy on 19/01/2016.
 */
public class NetworkConstants {
    private static int counter = 0;

    public static final String LOG_TAG = "Network.LOG";

    public static final String SERVER = "http://54.218.33.83:8080/TrivialPursuit/game/";
    public static final String LIST_QUERY = "query";
    public static final String SIMPLE_PLAY_QUERY = "query/play";
    public static final String SUBJECT_PLAY_QUERY = "query/play-subject?subject=";
    public static final String POST_QUERY = "query/add";
    public static final String DELETE_QUERY = "query/delete?id=";

    public static final String SIMPLE_QUERY_SERVICE = "info.duhovniy.maxim.trivialpursuit.SIMPLE_QUERY_SERVICE";
    public static final String LIST_QUERY_SERVISE = "info.duhovniy.maxim.trivialpursuit.LIST_QUERY_SERVISE";
    public static final String SUBJECT_QUERY_SERVICE = "info.duhovniy.maxim.trivialpursuit.SUBJECT_QUERY_SERVICE";
    public static final String ADD_QUERY_SERVICE = "info.duhovniy.maxim.trivialpursuit.ADD_QUERY_SERVICE";

    public static final String RESPONSE = "info.duhovniy.maxim.trivialpursuit.RESPONSE";
    public static final String RESPONSE_MESSAGE = "info.duhovniy.maxim.trivialpursuit.RESPONSE_MESSAGE";

    public static String sendGetHttpRequest(String queryType, String subject) {

        String urlString = SERVER;
        BufferedReader input = null;
        HttpURLConnection httpCon = null;
        InputStream input_stream = null;
        InputStreamReader input_stream_reader = null;
        StringBuilder response = new StringBuilder();

        switch (queryType) {
            case LIST_QUERY:
                urlString += LIST_QUERY;
                break;
            case SIMPLE_PLAY_QUERY:
                urlString += SIMPLE_PLAY_QUERY;
                break;
            case SUBJECT_PLAY_QUERY:
                urlString = urlString + SUBJECT_PLAY_QUERY + subject;
                break;
        }

        try {
            URL url = new URL(urlString);
            httpCon = (HttpURLConnection) url.openConnection();

            if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(NetworkConstants.LOG_TAG, "Cannot Connect to : " + urlString);
                return null;
            }

            input_stream = httpCon.getInputStream();
            input_stream_reader = new InputStreamReader(input_stream);
            input = new BufferedReader(input_stream_reader);
            String line;

            while ((line = input.readLine()) != null) {
                response.append(line).append("\n");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input_stream_reader.close();
                    input_stream.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                httpCon.disconnect();
            }
        }
        return response.toString();
    }

    public static String sendPostHttpRequest(Question question) {
        StringBuilder bufferStringBuilder;
        try {

            URL url = new URL(SERVER + POST_QUERY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            JSONObject json = question.toJSONObject();

            json.put(DataConstant.ID, ++counter);

            String input = json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                String s = conn.getResponseMessage();
                return "Fail";
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bufferStringBuilder = new StringBuilder();

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                bufferStringBuilder.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            return "ConnectionError";
        } catch (IOException e) {

            return "ConnectionError";
        } catch (JSONException e) {

            return "JSON Error";
        }
        return bufferStringBuilder.toString();
    }

    public static String sendDeleteHttpRequest(int id) {
        StringBuilder bufferStringBuilder;
        try {

            URL url = new URL(SERVER + DELETE_QUERY + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
/*
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);


            JSONObject json = question.toJSONObject();

            String input =  json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                String s = conn.getResponseMessage();
                return "Fail";
            }
*/

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bufferStringBuilder = new StringBuilder();

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                bufferStringBuilder.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            return "ConnectionError";
        } catch (IOException e) {

            return "ConnectionError";
        }
        return bufferStringBuilder.toString();
    }

}
