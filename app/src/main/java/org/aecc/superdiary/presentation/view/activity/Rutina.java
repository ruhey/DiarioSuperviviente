package org.aecc.superdiary.presentation.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.aecc.superdiary.R;
import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Rutina extends DiaryBaseActivity  implements View.OnClickListener{

    //UI References
    private EditText fechaIniRutina;
    private EditText fechaAviso;
    private EditText horaIniRutina;
    private EditText horaAviso;

    private DatePickerDialog fIniDatePickerDialog;
    private DatePickerDialog fFinDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;
    private TimePickerDialog hFinTimePickerDialog;

    private Button botonGuardarRutina;

    private ScheduleClient scheduleClient;

    private SimpleDateFormat dateFormatter;
    private final String TIPO_NOTIFICACION = "R";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_rutina, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        findViewsById();
        setDateTimeField();

        guardar();

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this,"R");
        scheduleClient.doBindService();
    }

    private void findViewsById() {
        fechaIniRutina = (EditText) findViewById(R.id.fechaRutina);
        fechaIniRutina.setInputType(InputType.TYPE_NULL);
        fechaIniRutina.requestFocus();

        horaIniRutina = (EditText) findViewById(R.id.horaRutina);
        horaIniRutina.setInputType(InputType.TYPE_NULL);

        fechaAviso = (EditText) findViewById(R.id.fechaAvisoRutina);
        fechaAviso.setInputType(InputType.TYPE_NULL);

        horaAviso = (EditText) findViewById(R.id.horaAvisoRutina);
        horaAviso.setInputType(InputType.TYPE_NULL);

        botonGuardarRutina = (Button) findViewById(R.id.guardarRutina);

    }

    private void setDateTimeField() {
        fechaIniRutina.setOnClickListener(this);
        horaIniRutina.setOnClickListener(this);
        fechaAviso.setOnClickListener(this);
        horaAviso.setOnClickListener(this);
        botonGuardarRutina.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fIniDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaIniRutina.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fFinDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaAviso.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaIniRutina.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour , minute, true);

        hFinTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaAviso.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour, minute, true);
    }

    @Override
    public void onClick(View view) {
        if (view == fechaIniRutina) {
            fIniDatePickerDialog.show();
        } else if (view == fechaAviso) {
            fFinDatePickerDialog.show();
        } else if (view == horaIniRutina) {
            hIniTimePickerDialog.show();
        } else if (view == horaAviso) {
            hFinTimePickerDialog.show();
        } else if (view == botonGuardarRutina) {
            anadirNotificacion();
            guardar();
        }
    }

    private void guardar() {

    }


    private void anadirNotificacion() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar newCal = Calendar.getInstance();

        if(TextUtils.isEmpty(fechaAviso.getText()) || TextUtils.isEmpty(horaAviso.getText())){
            Toast.makeText(this, "No se le notificará la hora de la toma de su medicamento, debe completar la fecha y la hora del aviso", Toast.LENGTH_LONG).show();

        }else{
            try {
                newCal.setTime(dateFormat.parse(String.valueOf(fechaAviso.getText()) + " " + String.valueOf(horaAviso.getText())));
                newCal.set(Calendar.SECOND,0);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
            scheduleClient.setAlarmForNotification(newCal);
            // Notify the user what they just did
            Toast.makeText(this, "Notificacion guardada para el "+ fechaAviso.getText() + " a las "+ horaAviso.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rutina, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
