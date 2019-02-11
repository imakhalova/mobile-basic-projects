package interview.com.hiya;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.telephony.PhoneNumberUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Call {

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("h:mm a mm/dd/yy", Locale.ROOT);

    ContentValues mValues = new ContentValues();

    public Call(Cursor cursor) {
        DatabaseUtils.cursorRowToContentValues(cursor, mValues);
    }

    public String getNumber() {
        return PhoneNumberUtils.formatNumber(mValues.getAsString(CallLog.Calls.NUMBER), Locale.getDefault().getCountry());
    }

    public String getType() {
        return CallType.fromVal(mValues.getAsInteger(CallLog.Calls.TYPE)).toString();
    }

    public String getDate() {
        return DATE_FORMAT.format(new Date(mValues.getAsLong(CallLog.Calls.DATE)));
    }

    public enum CallType {
        Incoming(Calls.INCOMING_TYPE),
        Outgoing(Calls.OUTGOING_TYPE),
        Missed(Calls.MISSED_TYPE),
        Other(0);

        int val;

        CallType(int val) {
            this.val = val;
        }

        public static CallType fromVal(int val) {
            for (CallType messageStatus : values()) {
                if (messageStatus.val == val) {
                    return messageStatus;
                }
            }
            return Other;
        }
    }
}
