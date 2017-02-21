package com.example.mft12.organizer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import java.util.Calendar;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class CreateProgram extends AppCompatActivity {

    private EditText taskField;
    private ImageButton addTask;
    private TextView Repetition;
    private TextView Timing;
    private TextView Priority;
    private LinearLayout taskScroll;
    String days[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Every day"};
    private boolean[] checkedItems = new boolean[days.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);
        //declare the objects
        taskField = (EditText)findViewById(R.id.TaskTextField);
        addTask = (ImageButton)findViewById(R.id.AddTaskButton);
        Repetition = (TextView)findViewById(R.id.Repetition);
        Timing = (TextView)findViewById(R.id.Timing);
        Priority = (TextView)findViewById(R.id.Priority);
        taskScroll = (LinearLayout)findViewById(R.id.TasksScroll);

        //show the dialog of time
        //The method "onTimeListener" is out the block of "onCreate"
        final Calendar calendar = Calendar.getInstance();
        Timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(CreateProgram.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true).show();

            }
        });
        //show the dialog of priority
        Priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder priorityDialog = new AlertDialog.Builder(CreateProgram.this);
                priorityDialog.setTitle("Choose priority");
                final RadioGroup group = new RadioGroup(CreateProgram.this);

                final RadioButton high = new RadioButton(CreateProgram.this);
                final RadioButton medium = new RadioButton(CreateProgram.this);
                final RadioButton low = new RadioButton(CreateProgram.this);
                high.setText("High");
                medium.setText("Medium");
                low.setText("Low");
                group.addView(high);
                group.addView(medium);
                group.addView(low);
                high.setChecked(true);
                priorityDialog.setView(group);
                priorityDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(high.isChecked()){
                            Priority.setText("High");
                        }else if(medium.isChecked()){
                            Priority.setText("Medium");
                        }else{
                            Priority.setText("Low");
                        }
                    }
                });
                priorityDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                priorityDialog.create().show();

            }
        });
        //show the dialog of repetition
        Repetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CreateProgram.this);
                dialog.setTitle("repetition");


                dialog.setMultiChoiceItems(days, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(which == 7 && isChecked == true){
                            for(int i = 0; i < 7; i++) {
                                checkedItems[i] = true;

                            }
                        }
                    }
                });

                //Show the days chosen by user in Repetition
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] daysArr = new String[7];
                        int j = 0;
                        for(int i = 0; i < 7; i++){
                            if(checkedItems[i] == true){
                                daysArr[j] = days[i].substring(0,2);
                                j++;
                            }
                        }
                        String repText = "";
                        if(j != 7) {
                            for (int i = 0; i < j; i++)
                                repText += daysArr[i] + ",";
                            Repetition.setText(repText);
                        }else
                            Repetition.setText("Every day");
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    //to change the text of timing textView
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Timing.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    };
}
