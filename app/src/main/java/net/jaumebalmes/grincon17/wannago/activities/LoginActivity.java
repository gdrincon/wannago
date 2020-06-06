package net.jaumebalmes.grincon17.wannago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.fragments.RegisterDialogFragment;
import net.jaumebalmes.grincon17.wannago.interfaces.OnRegisterListener;
import net.jaumebalmes.grincon17.wannago.preferences.SavedPreferences;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements OnRegisterListener {
    private EditText userText;
    private EditText pwdText;
    private Button loginButton;
    private Button registerButton;
    RegisterDialogFragment registerDialog;
    SavedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        userText = findViewById(R.id.editTextUserName);
        pwdText = findViewById(R.id.editTextPwd);
        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);
        preferences = new SavedPreferences(LoginActivity.this);
        if(preferences.getPreferences().contains(getString(R.string.my_username)) &&
                preferences.getPreferences().contains(getString(R.string.my_pwd))) {
            startActivity(new Intent(LoginActivity.this, ListEventActivity.class));
        } else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(preferences.getPreferences().contains(getString(R.string.my_username)) &&
                            preferences.getPreferences().contains(getString(R.string.my_pwd))) {
                        startActivity(new Intent(LoginActivity.this, ListEventActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.register_message), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerDialog = new RegisterDialogFragment();
                    registerDialog.show(getSupportFragmentManager(), getText(R.string.sign_up_txt).toString());
                }
            });
        }

    }

    @Override
    public void onRegisterClickListener(String userName, String pwd, String email) {
        preferences.setLoginPreferences(userName, pwd, email);
        userText.setText(R.string.my_username);
        pwdText.setText(R.string.my_pwd);

    }
}
