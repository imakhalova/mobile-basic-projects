package interview.com.hiya;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class CallsFetcher implements GetCursorTask.DataFetcher<Call> {

    private Context mContext;

    private static Uri CONTENT_URI = CallLog.Calls.CONTENT_URI;
    private static int MAX_NUM_ROWS = 50;
    private static String[] LIST_PROJECTION = new String[] {
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.TYPE,
    };
    public CallsFetcher(Context context) {
        mContext = context;
    }

    @Override
    public List<Call> getData() {
        List<Call> data = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return data;
        }
        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, LIST_PROJECTION,
        null, null, CallLog.Calls.DATE + " LIMIT " + MAX_NUM_ROWS);
        if (cursor == null) {
            return data;
        }
        try {
            while (cursor.moveToNext()) {
                data.add(new Call(cursor));
            }
        } finally {
            cursor.close();
        }
        return data;
    }
}
