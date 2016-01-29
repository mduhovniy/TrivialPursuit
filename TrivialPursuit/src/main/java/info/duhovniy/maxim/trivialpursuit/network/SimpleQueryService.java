package info.duhovniy.maxim.trivialpursuit.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import info.duhovniy.maxim.trivialpursuit.data.DataConstant;
import info.duhovniy.maxim.trivialpursuit.data.Question;

/**
 * Created by maxduhovniy on 21/01/2016.
 */
public class SimpleQueryService extends IntentService {

    private Question question;

    public SimpleQueryService() {
        super("SimpleQueryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String searchResult = NetworkConstants.sendGetHttpRequest(NetworkConstants.SIMPLE_PLAY_QUERY, "");

        try {
            JSONObject object = new JSONObject(searchResult);

            question = new Question(object.getString(DataConstant.SUBJECT),
                    object.getString(DataConstant.QUERY),
                    object.getString(DataConstant.RIGHT),
                    object.getString(DataConstant.WRONG1),
                    object.getString(DataConstant.WRONG2),
                    object.getString(DataConstant.WRONG3));
        } catch (JSONException e) {
            Log.e(NetworkConstants.LOG_TAG, e.getMessage());
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(NetworkConstants.SIMPLE_QUERY_SERVICE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Bundle newBundle = new Bundle();
        newBundle.putParcelable(NetworkConstants.RESPONSE, question);

        broadcastIntent.putExtra(NetworkConstants.RESPONSE_MESSAGE, newBundle);
        sendBroadcast(broadcastIntent);

    }
}
