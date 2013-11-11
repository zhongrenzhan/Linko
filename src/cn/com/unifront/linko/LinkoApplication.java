
package cn.com.unifront.linko;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LinkoApplication extends Application {
    public SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public SharedPreferences getDefaultSP() {
        return sp;
    }

}
