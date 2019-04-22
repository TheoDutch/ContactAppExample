package com.example.contactlistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NewContactActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.contactlistsql.REPLY";
    public static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private EditText editNameView;
    private EditText editEmailView;
    private EditText editPhoneView;
    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        editNameView = findViewById(R.id.edit_name);
        editEmailView = findViewById(R.id.edit_email);
        editPhoneView = findViewById(R.id.edit_phone);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editNameView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = editNameView.getText().toString();
                    String email = editEmailView.getText().toString();
                    String phone = editPhoneView.getText().toString();
                    replyIntent.putExtra("NAME_REPLY", name);
                    replyIntent.putExtra("EMAIL_REPLY", email);
                    replyIntent.putExtra("PHONE_REPLY", phone);
                    replyIntent.putExtra("PATH_REPLY", picPath);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        final Button pfbutton = findViewById(R.id.button_pf);
        pfbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!checkPermission()) {
                    openActivity();
                } else {
                    if (checkPermission()) {
                        requestPermissionAndContinue();
                    } else {
                        openActivity();
                    }
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                    null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            this.picPath = picturePath;
            Toast.makeText(getApplicationContext(), R.string.pic_success,
                    Toast.LENGTH_LONG).show();
        }
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), R.string.pic_cancel,
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_necessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(NewContactActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(NewContactActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void openActivity() {
        Intent pfIntent = new Intent();
        pfIntent.setType("image/*");
        pfIntent.setAction(Intent.ACTION_PICK);
        startActivityForResult(pfIntent, PICK_IMAGE);
    }

}