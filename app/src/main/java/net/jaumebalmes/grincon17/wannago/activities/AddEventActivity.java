package net.jaumebalmes.grincon17.wannago.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.models.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {
    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 0;
    private ImageView eventImage;
    private EditText eventName;
    private EditText eventDescription;
    private EditText eventDate;
    private EditText eventTime;
    private EditText eventLocation;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutDate;
    private TextInputLayout textInputLayoutTime;
    private TextInputLayout textInputLayoutLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventImage = findViewById(R.id.imageViewAddEvent);
        eventName = findViewById(R.id.editTextNameEvent);
        eventDescription = findViewById(R.id.editTextDescriptionEvent);
        eventDate = findViewById(R.id.editTextDateEvent);
        eventTime = findViewById(R.id.editTextTimeEvent);
        eventLocation = findViewById(R.id.editTextAddressEvent);
        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutDate = findViewById(R.id.textInputLayoutDate);
        textInputLayoutTime = findViewById(R.id.textInputLayoutTime);
        textInputLayoutLocation = findViewById(R.id.textInputLayoutAddress);

        drawMandatoryFieldAsterisk();

        FloatingActionButton chooseImageFab = findViewById(R.id.changeImageFab);
        chooseImageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPicker(v);
            }
        });

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        verifyEmptyFields();
        FloatingActionButton viewEventActivityFab = findViewById(R.id.viewEventFab);
        viewEventActivityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEvent();
            }
        });
    }

    private void drawMandatoryFieldAsterisk() {
        textInputLayoutName.setHint(textInputLayoutName.getHint()+" "+getString(R.string.red_asterisk));
        textInputLayoutDate.setHint(textInputLayoutDate.getHint()+" "+getString(R.string.red_asterisk));
        textInputLayoutTime.setHint(textInputLayoutTime.getHint()+" "+getString(R.string.red_asterisk));
        textInputLayoutLocation.setHint(textInputLayoutLocation.getHint()+" "+getString(R.string.red_asterisk));
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                eventDate.setText(dayOfMonth + "/" + month +"/" + year);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.HOUR);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 10 && minute < 10) {
                    eventTime.setText("0" + hourOfDay + ":" + "0" + minute);
                } else if (hourOfDay < 10) {
                    eventTime.setText("0" + hourOfDay + ":" + minute);
                } else if (minute < 10) {
                    eventTime.setText(hourOfDay + ":" + "0" + minute);
                } else {
                    eventTime.setText(hourOfDay + ":" + minute);
                }

            }
        }, hour, minutes, true);
        timePickerDialog.show();
    }

    private void showDialogPicker(View v) {
        final String [] chooseDialogItems = getResources().getStringArray(R.array.chooseImageDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
        builder.setTitle(R.string.chooseImageDialogTittle);
        builder.setItems(chooseDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if(chooseDialogItems[which].equals(getResources().getString(R.string.chooseImageDialogCamera))) {
                    openCamera();
                } else if(chooseDialogItems[which].equals(getResources().getString(R.string.chooseImageDialogImage))) {
                    openGallery();
                } else if(chooseDialogItems[which].equals(getResources().getString(R.string.chooseImageDialogCancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private  void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1 :
                    Bundle bundle = data.getExtras();
                    assert bundle != null;
                    Bitmap camera = (Bitmap) bundle.get("data");
                    eventImage.setImageBitmap(camera);
                    break;
                case 0 :
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        eventImage.setImageURI(selectedImage);
                        eventImage.setTag(String.valueOf(selectedImage));
                    }
                    break;
            }
        }
    }

    private void verifyEmptyFields() {

        eventName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateName(s);
            }
        });

        eventName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateName(((EditText) v).getText());
                }
            }
        });
        eventDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateDate(s);
            }
        });

        eventDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDate(((EditText) v).getText());
                }
            }
        });
        eventTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateTime(s);
            }
        });

        eventTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateTime(((EditText) v).getText());
                }
            }
        });
        eventLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLocation(s);
            }
        });

        eventLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateLocation(((EditText) v).getText());
                }
            }
        });
    }
    private void validateName(Editable text) {
        if(!TextUtils.isEmpty(text)) {
            textInputLayoutName.setError(null);
        } else {
            textInputLayoutName.setError(getString(R.string.editText_error_msg));
        }
    }
    private void validateDate(Editable text) {
        if(!TextUtils.isEmpty(text)) {
            textInputLayoutDate.setError(null);
        } else {
            textInputLayoutDate.setError(getString(R.string.editText_error_msg));
        }
    }
    private void validateTime(Editable text) {
        if(!TextUtils.isEmpty(text)) {
            textInputLayoutTime.setError(null);
        } else {
            textInputLayoutTime.setError(getString(R.string.editText_error_msg));
        }
    }
    private void validateLocation(Editable text) {
        if(!TextUtils.isEmpty(text)) {
            textInputLayoutLocation.setError(null);
        } else {
            textInputLayoutLocation.setError(getString(R.string.editText_error_msg));
        }
    }

    public void addNewEvent()  {
        if(!TextUtils.isEmpty(eventName.getText()) && !TextUtils.isEmpty(eventDate.getText()) &&
                !TextUtils.isEmpty(eventTime.getText()) && !TextUtils.isEmpty(eventLocation.getText())) {

            Event event = new Event(String.valueOf(eventImage.getTag()), String.valueOf(eventName.getText()),String.valueOf(eventDate.getText()),
                    String.valueOf(eventTime.getText()), String.valueOf(eventLocation.getText()), String.valueOf(eventDescription.getText()));
            Gson gson = new Gson();
            String json = gson.toJson(event);
            try (Writer writer = new FileWriter("Output.json")) {
                Gson gson1 = new GsonBuilder().create();
                gson1.toJson(event, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent sendToViewEvent = new Intent(AddEventActivity.this, ViewEventDetailActivity.class);
            sendToViewEvent.putExtra("JSON_EVENT", json);
            startActivity(sendToViewEvent);
        } else  {
            Toast.makeText(getApplicationContext(), getString(R.string.field_required_toast_msg), Toast.LENGTH_LONG).show();
        }
    }

}
