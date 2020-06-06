package net.jaumebalmes.grincon17.wannago.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import net.jaumebalmes.grincon17.wannago.R;

public class SavedPreferences {
    private SharedPreferences preferences;
    private Activity activity;

    public SavedPreferences(Activity activity) {
        this.activity = activity;
    }

    public SharedPreferences getPreferences(){
        return  preferences = this.activity.getSharedPreferences(activity.getString(R.string.my_pref), Context.MODE_PRIVATE);
    }

    public void setLoginPreferences(String userName, String pwd, String mail) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(this.activity.getString(R.string.my_username), userName);
        editor.putString(this.activity.getString(R.string.my_pwd), pwd);
        editor.putString(this.activity.getString(R.string.my_email), mail);
        editor.apply();
        Toast.makeText(this.activity, this.activity.getString(R.string.login_success), Toast.LENGTH_LONG).show();
    }
}
