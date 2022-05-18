package sg.edu.rp.c346.id21025290.l04_reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText name, number, pax;
    Switch smokingEnabled;
    DatePicker regDate;
    TimePicker regTime;
    Button btnRsv, btnReset;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editTextName);
        number = findViewById(R.id.editTextNumber);
        pax = findViewById(R.id.editTextPax);
        smokingEnabled = findViewById(R.id.switchSmoking);
        regDate = findViewById(R.id.datePickerReg);
        regTime = findViewById(R.id.timePickerReg);
        btnRsv = findViewById(R.id.buttonReserve);
        btnReset = findViewById(R.id.buttonReset);

        btnRsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                // Check for default values
                if(name.getText().toString().trim().length() == 0){
                    Toast.makeText(MainActivity.this,"Enter your Name",Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
                if(number.getText().toString().trim().length() == 0){
                    Toast.makeText(MainActivity.this,"Enter your Contact Number",Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
                if(pax.getText().toString().trim().length() == 0){
                    Toast.makeText(MainActivity.this,"Enter the No. of pax",Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if(isValid) {
                    String displayMessage = "";
                    displayMessage += "Name: " + name.getText().toString();
                    displayMessage += "\nContact No: " + number.getText().toString();
                    displayMessage += "\nNo. of pax: " + pax.getText().toString();
                    displayMessage += "\nRegistration Date: " +regDate.getDayOfMonth()+"/"+regDate.getMonth()+"/"+regDate.getYear();
                    displayMessage += "\nRegistration Time: " + convertTimeString(regTime);
                    if (smokingEnabled.isChecked()){
                        displayMessage += "\nTable in smoking area";
                    } else{
                        displayMessage += "\nTable in non-smoking area";
                    }
                    Toast.makeText(MainActivity.this,displayMessage,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                number.setText("");
                pax.setText("");
                smokingEnabled.setChecked(false);
                regDate.updateDate(2020,5,1);
                regTime.setCurrentHour(19);
                regTime.setCurrentMinute(30);
            }
        });

        regTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker v, int hourOfDay, int minute) {
                if (regDate.getYear()==2020 && regDate.getMonth()==5 && regDate.getDayOfMonth()==1){
                    if (hourOfDay <= 19) {
                        v.setCurrentHour(19);
                        if (minute <= 30) {
                            v.setCurrentMinute(30);
                        }
                    }

                }
                if (hourOfDay < 8) {
                    v.setCurrentHour(8);
                }
                if (hourOfDay > 20){
                    v.setCurrentHour(20);
                }
            }
        });

        regDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
                if (year <= 2020) {
                    v.updateDate(2020, 5, 1);
                    if (monthOfYear <= 5) {
                        v.updateDate(2020, 5, 1);
                    }
                }
            }
        });
    }
    private String convertTimeString(TimePicker time){
        String timeString;
        if(time.getCurrentMinute() < 10){
            timeString = time.getCurrentHour().toString()+":0"+time.getCurrentMinute().toString();
        } else{
            timeString = time.getCurrentHour().toString()+":"+time.getCurrentMinute().toString();
        }
        return timeString;
    }
}