package com.developer.example.clumatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.example.clumatrix.adapter.SliderAdapter;
import com.developer.example.clumatrix.adapter.SliderData;
import com.developer.example.clumatrix.menu.ApiActivity;
import com.developer.example.clumatrix.menu.AssignmentActivity;
import com.developer.example.clumatrix.menu.AttendanceActivity;
import com.developer.example.clumatrix.menu.ExamActivity;
import com.developer.example.clumatrix.menu.HolidayActivity;
import com.developer.example.clumatrix.menu.ResultActivity;
import com.developer.example.clumatrix.menu.TeacherActivity;
import com.developer.example.clumatrix.menu.TimeTableActivity;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    ImageView imgBoy,imgGirl,imgAssignment,imgTeacher,imgAttendance,imgTimetable,imgHolidays,imgExam,imgResult,imgApi;
    SliderView sliderView;
    Dialog myDialog;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imgBoy = findViewById(R.id.imgBoy);
        imgGirl = findViewById(R.id.imgGirl);
        imgAssignment = findViewById(R.id.imgAssignment);
        imgTeacher = findViewById(R.id.imgTeacher);
        imgAttendance = findViewById(R.id.imgAttendance);
        imgTimetable = findViewById(R.id.imgTimetable);
        imgHolidays = findViewById(R.id.imgHolidays);
        imgExam = findViewById(R.id.imgExam);
        imgResult = findViewById(R.id.imgResult);
        imgApi = findViewById(R.id.imgApi);
        sliderView = findViewById(R.id.slider);

        imgBoy.setOnClickListener(this);
        imgGirl.setOnClickListener(this);
        imgAssignment.setOnClickListener(this);
        imgTeacher.setOnClickListener(this);
        imgAttendance.setOnClickListener(this);
        imgTimetable.setOnClickListener(this);
        imgHolidays.setOnClickListener(this);
        imgExam.setOnClickListener(this);
        imgResult.setOnClickListener(this);
        imgApi.setOnClickListener(this);

        setSliderData();

        myDialog = new Dialog(this);
    }

    void setSliderData(){
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        sliderDataArrayList.add(new SliderData(getDrawable(R.drawable.slide1)));
        sliderDataArrayList.add(new SliderData(getDrawable(R.drawable.slide2)));
        sliderDataArrayList.add(new SliderData(getDrawable(R.drawable.slide3)));

        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBoy:
                ShowPopup();
                break;
            case R.id.imgGirl:
                ShowPopup();
                break;
            case R.id.imgAssignment:
                startActivity(new Intent(Dashboard.this, AssignmentActivity.class));
                break;
            case R.id.imgTeacher:
                startActivity(new Intent(Dashboard.this, TeacherActivity.class));
                break;
            case R.id.imgAttendance:
                startActivity(new Intent(Dashboard.this, AttendanceActivity.class));
                break;
            case R.id.imgTimetable:
                startActivity(new Intent(Dashboard.this, TimeTableActivity.class));
                break;
            case R.id.imgHolidays:
                startActivity(new Intent(Dashboard.this, HolidayActivity.class));
                break;
            case R.id.imgExam:
                startActivity(new Intent(Dashboard.this, ExamActivity.class));
                break;
            case R.id.imgResult:
                startActivity(new Intent(Dashboard.this, ResultActivity.class));
                break;
            case R.id.imgApi:
                startActivity(new Intent(Dashboard.this, ApiActivity.class));
                break;
        }
    }

    public void ShowPopup() {
        myDialog.setContentView(R.layout.subject_popup);
        myDialog.setCancelable(false);
        TextView txtclose =myDialog.findViewById(R.id.txtclose);
        LinearLayout row1 =myDialog.findViewById(R.id.row1);
        LinearLayout row2 =myDialog.findViewById(R.id.row2);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        row1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        row2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}