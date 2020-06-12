package net.jaumebalmes.grincon17.wannago.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.fragments.RegisterDialogFragment;
import net.jaumebalmes.grincon17.wannago.interfaces.OnRegisterListener;
import net.jaumebalmes.grincon17.wannago.preferences.SavedPreferences;

public class LoginActivity extends AppCompatActivity implements OnRegisterListener {
    private static final int PERMISSION_REQUEST_CODE = 200;
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
        userText = findViewById(R.id.editTextUserName);
        pwdText = findViewById(R.id.editTextPwd);
        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);
        preferences = new SavedPreferences(LoginActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!cameraPermissionGranted() || !storagePermissionGranted()) {
            requestPermission();
        } else {
            if(preferences.getPreferences().contains(getString(R.string.my_username)) &&
                    preferences.getPreferences().contains(getString(R.string.my_pwd))) {
                startActivity(new Intent(LoginActivity.this, BottomNavActivity.class));
            } else {
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preferences.getPreferences().contains(getString(R.string.my_username)) &&
                                preferences.getPreferences().contains(getString(R.string.my_pwd))) {
                            startActivity(new Intent(LoginActivity.this, BottomNavActivity.class));
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


    }

    @Override
    public void onRegisterClickListener(String userName, String pwd, String email) {
        preferences.setLoginPreferences(userName, pwd, email);
        userText.setText(userName);
        pwdText.setText(pwd);

    }


    /**
     * permiso concedido para acceder a la cámara
     * @return true
     */
    private Boolean cameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * permiso concedido para acceder al almacenamiento
     * @return true
     */
    private Boolean storagePermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Array de los permisos a pedir
     */

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    /**
     * Mensaje que informa que la app necesita permiso en caso de que el ususario lo deniegue
     * @param message a mostrar
     * @param okListener el Dialog listener
     */
    private void showMessagePermission(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();

    }

    /**
     * Maneja el resultado de los permisos
     * @param requestCode el código de petición
     * @param permissions los permisos pedidos
     * @param grantResults los resultados con los permisos concedidos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            }  else if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            } else {
                showMessagePermission(getString(R.string.need_permission), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission();
                    }
                });
            }
        }
    }
}
