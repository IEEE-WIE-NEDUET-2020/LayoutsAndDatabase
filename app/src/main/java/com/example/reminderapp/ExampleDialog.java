package com.example.reminderapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import static android.view.View.resolveSizeAndState;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class ExampleDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText editTexttopic;
    private  EditText editTextmessage;
    private TextView textViewtdate;
    private Button myButton;
    private Button mytimeButton;
    private TextView textViewtime;
    private TextView category;
    private Spinner spinner_category;
    String dayBefore;
    Date original_date;
    Date reminder_date;

    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private  TimePickerDialog.OnTimeSetListener mTimePicker;

    DatabaseReference myDatabase;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        myDatabase = FirebaseDatabase.getInstance().getReference("tests");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("New Test")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addTest();

                    }
                });

        editTexttopic = view.findViewById(R.id.topic);
        editTextmessage = view.findViewById(R.id.message);
        textViewtdate = view.findViewById(R.id.date);
        myButton = view.findViewById(R.id.button);

//        textViewtime = view.findViewById(R.id.time);
//        mytimeButton = view.findViewById(R.id.buttontime);
        category = view.findViewById(R.id.category);
        spinner_category = view.findViewById(R.id.spinner_cat);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(this);


        final Calendar newCalender = Calendar.getInstance();
        myButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                                textViewtdate.setText(newDate.getTime().toString());
                                original_date = newDate.getTime();// original  date and time saved in Date variable

                                Calendar prev_date = Calendar.getInstance(); // calendar to save previuos date


                                prev_date.setTime(original_date);

                                prev_date.set(Calendar.HOUR_OF_DAY, 18);
                                prev_date.set(Calendar.MINUTE, 0);
                                prev_date.set(Calendar.SECOND, 0);
                                prev_date.add(newDate.DATE,-1); //get previous date from  original date


                                reminder_date = prev_date.getTime();


                                    }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),false);
                        time.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        time.show();

                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                //

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setGravity(Gravity.CENTER);

        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(Color.BLUE);

        Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLUE);



        return alertDialog;




    }

    private void addTest(){


        String topic = editTexttopic.getText().toString().trim();
        String message = editTextmessage.getText().toString().trim();
        String date = textViewtdate.getText().toString();
        String spinnerText = spinner_category.getSelectedItem().toString();

        if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(message)){

            String id = myDatabase.push().getKey();

            //switch case
            TestofSub1 test = new TestofSub1(topic, message, date, id, spinnerText ,original_date, reminder_date );

            myDatabase.child(id).setValue(test);

            Toast.makeText(getContext(), "New test added!", Toast.LENGTH_LONG).show();




        }
        else{
            Toast.makeText(getContext(), "Please give complete information", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cat = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
