package interview.com.hiya;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.READ_CALL_LOG;

public class PermissionUtils {
    private static int PERMISSION_REQUEST_CODE = 1;

    public static void requestPermissionsIfNeeded(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{READ_CALL_LOG},
                    PERMISSION_REQUEST_CODE);
            return;
        }
    }
    public static boolean handlePermissionsResult(Activity activity,
                                                  String permissions[],
                                                  int[] grantResults) {
        return permissions.length > 0 && permissions[0].equals(READ_CALL_LOG)
                && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
