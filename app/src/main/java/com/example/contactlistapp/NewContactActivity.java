package com.example.contactlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewContactActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.contactlistsql.REPLY";

    private EditText editNameView;
    private EditText editEmailView;
    private EditText editPhoneView;

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
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
