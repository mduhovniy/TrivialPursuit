package info.duhovniy.maxim.trivialpursuit.network;

import android.app.IntentService;
import android.content.Intent;

import info.duhovniy.maxim.trivialpursuit.data.Question;

/**
 * Created by maxduhovniy on 26/01/2016.
 */
public class AddQueryService extends IntentService {

    public AddQueryService() {
        super("AddQueryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Question q = intent.getBundleExtra("Bundle Question").getParcelable("New Question");
        String addResult = NetworkConstants.sendPostHttpRequest(q);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(NetworkConstants.ADD_QUERY_SERVICE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);

        broadcastIntent.putExtra(NetworkConstants.RESPONSE_MESSAGE, addResult);
        sendBroadcast(broadcastIntent);
    }
}
