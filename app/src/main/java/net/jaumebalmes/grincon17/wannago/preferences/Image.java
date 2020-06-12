package net.jaumebalmes.grincon17.wannago.preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import net.jaumebalmes.grincon17.wannago.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Image {

    private static final int GALLERY_CODE = 0;
    private static final int CAMERA_CODE = 1;
    private Context context;
    private Fragment fragment;
    private String imageFilePath;

    public Image(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;

    }


    /**
     * Escoge entre tomar foto o acceder a las imágenes.
     */
    public void chooseCameraOrGallery() {

        final String[] chooseDialogItems = fragment.getResources().getStringArray(R.array.chooseImageDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.chooseImageDialogTittle);
        builder.setItems(chooseDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (chooseDialogItems[which].equals(fragment.getString(R.string.chooseImageDialogCamera))) {
                    openCamera();
                } else if (chooseDialogItems[which].equals(fragment.getString(R.string.chooseImageDialogImage))) {
                    openGallery();
                } else if (chooseDialogItems[which].equals(fragment.getString(R.string.chooseImageDialogCancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * Crea una imagen con la foto tomada
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    public String getFilePath() {
        return imageFilePath;
    }

    /**
     * Accede a la cámara para tomar una foto.
     */
    public void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(context.getPackageManager()) != null){

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, fragment.getString(R.string.authorities), photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);

                fragment.startActivityForResult(pictureIntent,
                        CAMERA_CODE);
            }
        }
    }

    /**
     * Accede al almacenamiento del dispositivo para escoger una imagen
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, GALLERY_CODE);
    }

}
