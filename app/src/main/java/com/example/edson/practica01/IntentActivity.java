package com.example.edson.practica01;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txt, txt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        findViewById(R.id.Web).setOnClickListener(this);
        findViewById(R.id.openPhone).setOnClickListener(this);
        findViewById(R.id.callPhone).setOnClickListener(this);
        findViewById(R.id.sendMail).setOnClickListener(this);
        findViewById(R.id.sendSms).setOnClickListener(this);
        findViewById(R.id.Maps).setOnClickListener(this);
        findViewById(R.id.alarm).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
        txt = findViewById(R.id.etxt);
        txt2 = findViewById(R.id.etxt2);
        findViewById(R.id.Calendar).setOnClickListener(this);
        findViewById(R.id.Temporizador).setOnClickListener(this);
        findViewById(R.id.Video).setOnClickListener(this);
        findViewById(R.id.Google).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()) {
            case R.id.Web:
                if (Patterns.WEB_URL.matcher(txt.getText().toString()).matches() == true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String urlWeb = "http://" + txt.getText().toString();
                    intent.setData(Uri.parse(urlWeb));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "direccion no valida txt1", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.openPhone:
                i = new Intent(Intent.ACTION_DIAL);
                startActivity(i);
                break;
            case R.id.callPhone:
                if (Patterns.PHONE.matcher(txt.getText().toString()).matches() == true) {
                    i.setData(Uri.parse("tel:" + txt.getText().toString()));
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "escribe un numero txt1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sendMail:
                if (Patterns.EMAIL_ADDRESS.matcher(txt.getText().toString()).matches() == true) {
                    if (txt2.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "escribe un mensaje txt2", Toast.LENGTH_SHORT).show();
                    } else {
                        i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "Ejemplo Email");
                        i.putExtra(Intent.EXTRA_TEXT, txt2.getText().toString());
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{txt.getText().toString()});
                        try {
                            startActivity(Intent.createChooser(i, "Quien puede enviar un Email?"));
                        } catch (android.content.ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "email invalido txt1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sendSms:
                if (Patterns.PHONE.matcher(txt.getText().toString()).matches() == true) {
                    if (txt2.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "escribe un mensaje txt2", Toast.LENGTH_SHORT).show();
                    } else {
                        i = new Intent(Intent.ACTION_VIEW);
                        i.putExtra("sms_body", txt2.getText().toString());
                        i.putExtra("address", txt.getText().toString());
                        i.setType("vnd.android-dir/mms-sms");
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "escribe un nummero txt1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Maps:
                if ((Pattern.matches("([+-]*)([0-9]*)\\.([0-9]*)", txt.getText().toString())) == true) {
                    if ((Pattern.matches("([+-]*)([0-9]*)\\.([0-9]*)", txt2.getText().toString())) == true) {
                        i.setData(Uri.parse("geo:" + txt.getText().toString() + "," + txt2.getText().toString()));
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "no es decimal txt2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "no es decimal txt1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.alarm:
                if (txt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "escribe algo txt1", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar date = Calendar.getInstance();
                    int h = date.get(Calendar.HOUR_OF_DAY);
                    int m = date.get(Calendar.MINUTE) + 2;
                    i = new Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_MESSAGE, txt.getText().toString()).putExtra(AlarmClock.EXTRA_HOUR, h).putExtra(AlarmClock.EXTRA_MINUTES, m);
                    if (i.resolveActivity(getPackageManager()) != null) {
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "No se puede asignar la alarma", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.camera:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
                break;
            case R.id.Calendar:
                i = new Intent(Intent.ACTION_INSERT);
                i.setData(CalendarContract.Events.CONTENT_URI);
                startActivity(i);
                break;
            case R.id.Temporizador:
                if (txt.getText().toString().equals("") == true) {
                    Toast.makeText(getApplicationContext(), "escribe algo txt1", Toast.LENGTH_SHORT).show();
                } else {
                    i = new Intent(AlarmClock.ACTION_SET_TIMER).putExtra(AlarmClock.EXTRA_MESSAGE, txt.getText().toString()).putExtra(AlarmClock.EXTRA_LENGTH, 60);
                    if (i.resolveActivity(getPackageManager()) != null) {
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "no abre", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.Video:
                i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
                break;
            case R.id.Google:
                if (txt.getText().toString().equals("") == true) {
                    Toast.makeText(getApplicationContext(), "escribe algo txt1", Toast.LENGTH_SHORT).show();
                } else {
                    i = new Intent(Intent.ACTION_WEB_SEARCH);
                    String url = txt.getText().toString();
                    i.putExtra(SearchManager.QUERY, url);
                    startActivity(i);
                }
                break;
        }

    }
}
