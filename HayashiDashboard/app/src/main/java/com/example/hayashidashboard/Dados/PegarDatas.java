package com.example.hayashidashboard.Dados;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.hayashidashboard.Activitys.MainActivity;

import java.util.Calendar;


public class PegarDatas {

    private static DatePickerDialog picker;
    private String f;
    private String i;

    public void pegarDataFim(MainActivity mainActivity, final EditText etDataFim) {

        final Calendar cldr = Calendar.getInstance();
        int dia = cldr.get(Calendar.DAY_OF_MONTH);
        int mes = cldr.get(Calendar.MONTH);
        int ano = cldr.get(Calendar.YEAR);


        picker = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month + 1;
                String mesFormat = "" + month;
                String diaFormat = "" + dayOfMonth;


                if (month < 10) {

                    mesFormat = "0" + month;
                }
                if (dayOfMonth < 10) {

                    diaFormat = "0" + dayOfMonth;
                }

                f = (year + "-" + mesFormat + "-" + diaFormat + " 23:59:59.999");
                etDataFim.setText(diaFormat + "/" + mesFormat + "/" + year);


            }
        }, ano, mes, dia);

        picker.show();

    }

    public void pegarDataInicio(MainActivity mainActivity, final EditText etDataInicio) {

        final Calendar cldr = Calendar.getInstance();
        int dia = cldr.get(Calendar.DAY_OF_MONTH);
        int mes = cldr.get(Calendar.MONTH);
        int ano = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month + 1;
                String mesFormat = "" + month;
                String diaFormat = "" + dayOfMonth;

                if (month < 10) {

                    mesFormat = "0" + month;
                }
                if (dayOfMonth < 10) {

                    diaFormat = "0" + dayOfMonth;
                }

                i = (year + "-" + mesFormat + "-" + diaFormat + " 00:00:00.000");
                etDataInicio.setText(diaFormat + "/" + mesFormat + "/" + year);


            }
        }, ano, mes, dia);

        picker.show();
    }

    public String setarDataFim(MainActivity mainActivity) {

        final Calendar cldr = Calendar.getInstance();
        int dia = cldr.get(Calendar.DAY_OF_MONTH);
        int mes = cldr.get(Calendar.MONTH);
        int ano = cldr.get(Calendar.YEAR);


        picker = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month + 1;
                String mesFormat = "" + month;
                String diaFormat = "" + dayOfMonth;


                if (month < 10) {

                    mesFormat = "0" + month;
                }
                if (dayOfMonth < 10) {

                    diaFormat = "0" + dayOfMonth;
                }

                f = (year + "-" + mesFormat + "-" + diaFormat + " 23:59:59.999");


            }
        }, ano, mes, dia);

        return f;

    }

    public String setarDataInicio(MainActivity mainActivity) {

        final Calendar cldr = Calendar.getInstance();
        int dia = cldr.get(Calendar.DAY_OF_MONTH);
        int mes = cldr.get(Calendar.MONTH);
        int ano = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month + 1;
                String mesFormat = "" + month;
                String diaFormat = "" + dayOfMonth;

                if (month < 10) {

                    mesFormat = "0" + month;
                }
                if (dayOfMonth < 10) {

                    diaFormat = "0" + dayOfMonth;
                }

                i = (year + "-" + mesFormat + "-" + diaFormat + " 00:00:00.000");


            }
        }, ano, mes, dia);

        return i;
    }

}
