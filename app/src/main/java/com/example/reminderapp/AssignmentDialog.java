package com.example.reminderapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class AssignmentDialog extends AppCompatDialogFragment  implements AdapterView.OnItemSelectedListener {
    private EditText editTexttopic;
    private  EditText editTextmessage;
    private TextView textViewtdate;
    private Button myButton;
    String dayBefore;
    Date original_date;
    Date reminder_date;

    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private  TimePickerDialog.OnTimeSetListener mTimePicker;

    DatabaseReference myDatabase;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        myDatabase = FirebaseDatabase.getInstance().getReference("assignments");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.assignment_layout_dialog, null);

        builder.setView(view)
                .setTitle("New Assignment")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addAssignment();

                    }
                });

        editTexttopic = view.findViewById(R.id.topic);
        editTextmessage = view.findViewById(R.id.message);
        textViewtdate = view.findViewById(R.id.date);
        myButton = view.findViewById(R.id.button);


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
    private void addAssignment(){


        String topic = editTexttopic.getText().toString().trim();
        String message = editTextmessage.getText().toString().trim();
        String date = textViewtdate.getText().toString();

        if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(message)){

            String id = myDatabase.push().getKey();

            //switch case
            Assignment assign = new Assignment(topic, message, date, id , original_date, reminder_date );

            myDatabase.child(id).setValue(assign);

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
