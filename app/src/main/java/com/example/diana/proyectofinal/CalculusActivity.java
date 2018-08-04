package com.example.diana.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalculusActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvBornDate, tvActualAge, tvPenultimeAge, tvDifferenceAge;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat, simpleYearFormat, simpleMonthFormat, simpleDayFormat;

    int penultimateAge;
    int ultimateAge = 0;
    int diffAges;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        simpleYearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        simpleMonthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        simpleDayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        datePickerDialog = new DatePickerDialog(this, mDatePickerListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));

        tvBornDate = (TextView) findViewById(R.id.tv_born_date);
        tvActualAge = (TextView) findViewById(R.id.tv_actual_age);
        tvPenultimeAge = (TextView) findViewById(R.id.tv_penultimate_age);
        tvDifferenceAge = (TextView) findViewById(R.id.tv_difference_age);

        initial();

        findViewById(R.id.btn_choose_date).setOnClickListener(this);
    }

    public void initial(){
        tvBornDate = (TextView) findViewById(R.id.tv_born_date);
        tvActualAge = (TextView) findViewById(R.id.tv_actual_age);
        tvPenultimeAge = (TextView) findViewById(R.id.tv_penultimate_age);
        tvDifferenceAge = (TextView) findViewById(R.id.tv_difference_age);
    }

    @Override
    public void onClick(View v) {
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener mDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, dayOfMonth);

            refreshDisplays();
        }
    };

    private void refreshDisplays() {
        tvBornDate.setText(simpleDateFormat.format(calendar.getTime()));

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DATE);

        int bornYear = Integer.parseInt(simpleYearFormat.format(calendar.getTime()));
        int bornMonth = Integer.parseInt(simpleMonthFormat.format(calendar.getTime()));
        int bornDay = Integer.parseInt(simpleDayFormat.format(calendar.getTime()));



        if (bornYear > currentYear){
            showDialog();
        }else if(( bornYear == currentYear) && (bornMonth > currentMonth)){
            showDialog();
        }else{
            int currentAgeYear = currentYear - bornYear;
            if (currentAgeYear == 0){
                tvActualAge.setText("You still aren't one year old.");
            }else
                calculateAges(currentAgeYear);
        }


        /*if ((bornYear > currentYear)) {
            showDialog();
        } else if ( (bornYear <= currentAgeYear && bornMonth > currentMonth) || (bornMonth == currentMonth && bornDay > currentDay)) {
            currentAgeYear -= 1;
            calculateAges(currentAgeYear);
        } else {
            calculateAges(currentAgeYear);
        }*/
    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(CalculusActivity.this).create();
        alertDialog.setMessage("You haven't been born yet.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void calculateAges(int currentAgeYear){
        if (currentAgeYear == 0){
            ultimateAge = currentAgeYear;
            tvActualAge.setText("Current calculus " + ultimateAge);
        }else{
            penultimateAge = ultimateAge;
            ultimateAge = currentAgeYear;

            tvPenultimeAge.setText("Previous calculus " + penultimateAge);
            tvActualAge.setText("Current calculus " + ultimateAge);

            if (ultimateAge > penultimateAge && penultimateAge > 0){
                diffAges = ultimateAge - penultimateAge;
                tvDifferenceAge.setText("You are " + diffAges + " years older than previous user.");

            }else{
                if (penultimateAge > ultimateAge){
                    diffAges = penultimateAge - ultimateAge;
                    tvDifferenceAge.setText("The previous user is " + diffAges + " years older than you.");
                }else {
                    if (ultimateAge == penultimateAge){
                        tvDifferenceAge.setText("The previous user and you are the same age.");
                    }
                }
            }
        }
    }
}

