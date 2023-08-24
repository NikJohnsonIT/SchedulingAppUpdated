package com.example.schedulerv8.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schedulerv8.Adapters.AssessmentAdapter;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    Repository repository;
    Spinner termSpinner;
    //should autogenerate

    EditText courseIDControl;
    Spinner courseTypeSpinner;
    EditText courseNameControl;
    EditText courseStartDate;
    EditText courseEndDate;
    DatePickerDialog.OnDateSetListener courseStartListener;
    DatePickerDialog.OnDateSetListener courseEndListener;
    final Calendar courseStartCalendar = Calendar.getInstance();
    final Calendar courseEndCalendar = Calendar.getInstance();
    Spinner courseStatus;
    EditText instructorNameControl;
    EditText instructorPhoneControl;
    EditText instructorEmailControl;
    RecyclerView assessmentsRV;
    EditText courseNotesControl;
    Button saveCourseButton;

    Button addAssessButton;

    Spinner meetingTimesSpinner;
    Spinner supportAvailable;
    Spinner supportDaysTimes;

    int termId;
    int courseIdValue;
    String courseNameValue;
    String startDateValue;
    String endDateValue;
    String statusValue;
    String instructorNameVal;
    String instructorPhoneVal;
    String instructorEmailVal;
    String courseNotesVal;
    Course currentCourse;
    ArrayAdapter<CharSequence> statusAdapter;
    ArrayAdapter<Term> termArrayAdapter;

    ArrayAdapter<Integer> termIdAdapter;
    Term selectedTerm;
    String currentUser;
    String createdBy;
    String meetingDaysTimes;
    String supportAvailability;
    String supportTimes;
    String courseType;
    //Asynchronous asynchronousCourse;
    //Synchronous synchronousCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        createdBy = intent.getStringExtra("userId");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        repository = new Repository(getApplication());
        courseStatus = findViewById(R.id.courseStatusSpinner);
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.course_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseStatus.setAdapter(statusAdapter);
        courseIDControl = findViewById(R.id.courseDetailsCourseIdField);

        courseNameControl = findViewById(R.id.courseDetailsCourseNameField);
        courseStartDate = findViewById(R.id.courseStartDatePicker);
        courseEndDate = findViewById(R.id.courseEndDatePicker);
        instructorNameControl = findViewById(R.id.courseInstructorNameField);
        instructorPhoneControl = findViewById(R.id.courseInstructorPhoneField);
        instructorEmailControl = findViewById(R.id.courseInstructorEmailField);
        assessmentsRV = findViewById(R.id.assocAssessRV);
        courseNotesControl = findViewById(R.id.courseNoteField);
        saveCourseButton = findViewById(R.id.saveCourseBtn);
        addAssessButton = findViewById(R.id.addAssessBtn);
        termId = getIntent().getIntExtra("course_term_id", -1);
        courseIdValue = getIntent().getIntExtra("course_id", -1);
        instructorNameVal = getIntent().getStringExtra("instructor_name");
        instructorPhoneVal = getIntent().getStringExtra("instructor_phone");
        instructorEmailVal = getIntent().getStringExtra("instructor_email");
        courseNotesVal = getIntent().getStringExtra("course_note");
        statusValue = getIntent().getStringExtra("status");
        courseNameValue = getIntent().getStringExtra("course_title");
        startDateValue = getIntent().getStringExtra("course_start_date");
        endDateValue = getIntent().getStringExtra("course_end_date");
        meetingTimesSpinner = findViewById(R.id.courseMeetingTimesSpinner);
        ArrayAdapter<CharSequence> meetingTimesAdapter = ArrayAdapter.createFromResource(this, R.array.sync_meeting_times_array, android.R.layout.simple_spinner_item);
        meetingTimesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingTimesSpinner.setAdapter(meetingTimesAdapter);
        meetingTimesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                meetingDaysTimes = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        supportAvailable = findViewById(R.id.liveSupportSpinner);
        ArrayAdapter<CharSequence> supportAvailableAdapter = ArrayAdapter.createFromResource(this, R.array.live_support_available_array, android.R.layout.simple_spinner_item);
        supportAvailableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supportAvailable.setAdapter(supportAvailableAdapter);
        supportAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                supportAvailability = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        supportDaysTimes = findViewById(R.id.courseSupportTimeSpinner);
        ArrayAdapter<CharSequence> supportTimesAdapter = ArrayAdapter.createFromResource(this, R.array.live_support_times_array, android.R.layout.simple_spinner_item);
        supportTimesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supportDaysTimes.setAdapter(supportTimesAdapter);
        supportDaysTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                supportTimes = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        termSpinner = findViewById(R.id.courseDetailsTermIdSpinner);

        repository.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termArrayAdapter = new ArrayAdapter<>(CourseDetails.this, android.R.layout.simple_spinner_item, terms);
                termSpinner.setAdapter(termArrayAdapter);
            }
        });

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Term selectedTerm = (Term) adapterView.getSelectedItem();
                if (selectedTerm != null) {
                    termId = selectedTerm.getTermId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        courseTypeSpinner = findViewById(R.id.courseTypeSpinner);
        ArrayAdapter<CharSequence> courseTypeAdapter = ArrayAdapter.createFromResource(this, R.array.course_type_array, android.R.layout.simple_spinner_item);
        courseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseTypeSpinner.setAdapter(courseTypeAdapter);
        courseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedType = adapterView.getItemAtPosition(position).toString();
                if (selectedType.equals("Select Value")) {
                    findViewById(R.id.courseMeetingTimesLabel).setVisibility(View.GONE);
                    findViewById(R.id.courseMeetingTimesSpinner).setVisibility(View.GONE);
                    findViewById(R.id.courseLiveSupportLabel).setVisibility(View.GONE);
                    findViewById(R.id.liveSupportSpinner).setVisibility(View.GONE);
                    findViewById(R.id.courseSupportTimeLabel).setVisibility(View.GONE);
                    findViewById(R.id.courseSupportTimeSpinner).setVisibility(View.GONE);
                    saveCourseButton.setEnabled(false);
                } else if (selectedType.equals("SYNCHRONOUS")) {
                    findViewById(R.id.courseMeetingTimesLabel).setVisibility(View.VISIBLE);
                    findViewById(R.id.courseMeetingTimesSpinner).setVisibility(View.VISIBLE);
                    findViewById(R.id.courseLiveSupportLabel).setVisibility(View.GONE);
                    findViewById(R.id.liveSupportSpinner).setVisibility(View.GONE);
                    findViewById(R.id.courseSupportTimeLabel).setVisibility(View.GONE);
                    findViewById(R.id.courseSupportTimeSpinner).setVisibility(View.GONE);
                    saveCourseButton.setEnabled(true);
                } else if (selectedType.equals("ASYNCHRONOUS")) {
                    findViewById(R.id.courseMeetingTimesLabel).setVisibility(View.GONE);
                    findViewById(R.id.courseMeetingTimesSpinner).setVisibility(View.GONE);
                    findViewById(R.id.courseLiveSupportLabel).setVisibility(View.VISIBLE);
                    findViewById(R.id.liveSupportSpinner).setVisibility(View.VISIBLE);
                    findViewById(R.id.courseSupportTimeLabel).setVisibility(View.VISIBLE);
                    findViewById(R.id.courseSupportTimeSpinner).setVisibility(View.VISIBLE);
                    saveCourseButton.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        courseStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusValue = courseStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //statusValue = "PLAN TO TAKE";
            }
        });

        courseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = courseStartDate.getText().toString();
                try {
                    courseStartCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, courseStartListener, courseStartCalendar.get(Calendar.YEAR),
                        courseStartCalendar.get(Calendar.MONTH), courseStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseStartListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                courseStartCalendar.set(Calendar.YEAR, year);
                courseStartCalendar.set(Calendar.MONTH, monthOfYear);
                courseStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        courseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = courseEndDate.getText().toString();
                try {
                    courseEndCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, courseEndListener, courseEndCalendar.get(Calendar.YEAR),
                        courseEndCalendar.get(Calendar.MONTH), courseEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseEndListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                courseEndCalendar.set(Calendar.YEAR, year);
                courseEndCalendar.set(Calendar.MONTH, monthOfYear);
                courseEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        addAssessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });

        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedType = courseTypeSpinner.getSelectedItem().toString();
                if (selectedType.equals("Select Value")) {
                    showCourseTypeSelectionDialog();
                    return;
                }
                String courseName = courseNameControl.getText().toString();
                String startDate = courseStartDate.getText().toString();
                String endDate = courseEndDate.getText().toString();
                String status = courseStatus.getSelectedItem().toString();
                String instructorName = instructorNameControl.getText().toString();
                String instructorPhone = instructorPhoneControl.getText().toString();
                String instructorEmail = instructorEmailControl.getText().toString();
                String courseNotes = courseNotesControl.getText().toString();

                if (selectedType.equals("ASYNCHRONOUS")) {
                    Course.AsynchronousCourse asyncCourse = new Course.AsynchronousCourse(
                            termId,
                            courseIdValue,
                            courseName,
                            startDate,
                            endDate,
                            status,
                            instructorName,
                            instructorPhone,
                            instructorEmail,
                            courseNotes,
                            createdBy,
                            supportAvailability,
                            supportTimes
                    );
                    if (courseIdValue == -1) {
                        repository.insertAsyncCourse(asyncCourse);
                    } else {
                        repository.updateAsyncCourse(asyncCourse);
                    }
                } else if (selectedType.equals("SYNCHRONOUS")) {
                    Course.SynchronousCourse syncCourse = new Course.SynchronousCourse(
                            termId,
                            courseIdValue,
                            courseName,
                            startDate,
                            endDate,
                            status,
                            instructorName,
                            instructorPhone,
                            instructorEmail,
                            courseNotes,
                            createdBy,
                            meetingDaysTimes
                    );
                    if (courseIdValue == -1) {
                        repository.insertSyncCourse(syncCourse);
                    } else {
                        repository.updateSyncCourse(syncCourse);
                    }
                }

                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                intent.putExtra("userId", createdBy);
                startActivity(intent);
            }
        });
    }

        private void showCourseTypeSelectionDialog () {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Course Type Selection");
            builder.setMessage("Select Course Type before saving.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        @Override
        public void onBackPressed () {
            Intent backIntent = new Intent(this, CourseList.class);
            backIntent.putExtra("userId", createdBy);
            startActivity(backIntent);
            finish();
        }

        @Override
        protected void onResume () {
            super.onResume();
            if (courseIdValue > 0) {

                String schedulerDateFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(schedulerDateFormat, Locale.US);
                RecyclerView assessmentsRV = findViewById(R.id.assocAssessRV);
                final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
                assessmentsRV.setAdapter(assessmentAdapter);
                assessmentsRV.setLayoutManager(new LinearLayoutManager(this));

                repository.getAssocAssess(courseIdValue).observe(this, new Observer<List<Assessment>>() {
                    @Override
                    public void onChanged(List<Assessment> assocAssess) {
                        assessmentAdapter.setAssessments(assocAssess);
                        assessmentAdapter.notifyDataSetChanged();
                    }
                });

                termId = getIntent().getIntExtra("course_term_id", -1);
                termSpinner = findViewById(R.id.courseDetailsTermIdSpinner);

                repository.getAllTerms().observe(this, new Observer<List<Term>>() {
                    @Override
                    public void onChanged(List<Term> terms) {
                        termArrayAdapter = new ArrayAdapter<>(CourseDetails.this, android.R.layout.simple_spinner_item, terms);
                        termSpinner.setAdapter(termArrayAdapter);

                        int termPosition = getTermPosition(termId);
                        termSpinner.setSelection(termPosition);
                    }
                });


                termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Term selectedTerm = (Term) adapterView.getSelectedItem();
                        if (selectedTerm != null) {
                            termId = selectedTerm.getTermId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                courseIdValue = getIntent().getIntExtra("course_id", -1);
                courseIDControl.setText(String.valueOf(courseIdValue));
                courseNameControl.setText(courseNameValue);
                if (startDateValue != "") {
                    courseStartDate.setText(startDateValue);
                } else {
                    courseStartDate.setText(sdf.format(new Date()));
                }
                if (endDateValue != "") {
                    courseEndDate.setText(endDateValue);
                } else {
                    courseEndDate.setText(sdf.format(new Date()));
                }
                instructorNameControl.setText(instructorNameVal);
                instructorPhoneControl.setText(instructorPhoneVal);
                instructorEmailControl.setText(instructorEmailVal);
                courseNotesControl.setText(courseNotesVal);

                if (statusValue == null) {
                    statusValue = "PLAN TO TAKE";
                }
                //find position of status value, set spinner to that.
                int spinnerPosition = statusAdapter.getPosition(statusValue);
                courseStatus.setSelection(spinnerPosition);
            }
        }

        private int getTermPosition ( int termId){
            for (int i = 0; i < termArrayAdapter.getCount(); i++) {
                Term term = termArrayAdapter.getItem(i);
                if (term != null && term.getTermId() == termId) {
                    return i;
                }
            }
            return 0;
        }

        private void updateLabel () {
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            courseStartDate.setText(sdf.format(courseStartCalendar.getTime()));
        }

        private void updateEndLabel () {
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            courseEndDate.setText(sdf.format(courseEndCalendar.getTime()));
        }

        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_course_details, menu);
            return true;
        }

        @SuppressLint("NonConstantResourceId")
        public boolean onOptionsItemSelected (MenuItem item){
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            switch (item.getItemId()) {
                case android.R.id.home:
                    Intent backIntent = new Intent(this, CourseList.class);
                    backIntent.putExtra("userId", createdBy);
                    startActivity(backIntent);
                    this.finish();
                    return true;
                case R.id.share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotesControl.getText().toString());
                    sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                    return true;
                case R.id.notifyStart:
                    String startDateFromScreen = courseStartDate.getText().toString();
                    String courseName = courseNameControl.getText().toString();
                    Date myDate = null;
                    try {
                        myDate = sdf.parse(startDateFromScreen);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                    Toast.makeText(CourseDetails.this, currentCourse.getCourseTitle() + " begins " + currentCourse.getCourseStartDate(), Toast.LENGTH_LONG).show();
                    intent.putExtra("key", courseName + " begins on " + startDateFromScreen + "!");
                    PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    return true;
                case R.id.notifyEnd:
                    String endDateFromScreen = courseEndDate.getText().toString();
                    courseName = courseNameControl.getText().toString();
                    try {
                        myDate = sdf.parse(endDateFromScreen);
                        trigger = myDate.getTime();
                        intent = new Intent(CourseDetails.this, MyReceiver.class);
                        Toast.makeText(CourseDetails.this, currentCourse.getCourseTitle() + " ends " + currentCourse.getCourseEndDate(), Toast.LENGTH_LONG).show();
                        intent.putExtra("key", courseName + " ends on " + endDateFromScreen + "!");
                        sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.removeCourse:
                    repository.getCourseById(courseIdValue).observe(this, new Observer<Course>() {
                        @Override
                        public void onChanged(Course retrievedCourse) {
                            if (retrievedCourse != null) {
                                currentCourse = retrievedCourse;
                                if (currentCourse.getCourseType().equals("ASYNCHRONOUS")) {
                                    repository.deleteAsyncCourse((Course.AsynchronousCourse) currentCourse);
                                } else if (currentCourse.getCourseType().equals("SYNCHRONOUS")) {
                                    repository.deleteSyncCourse((Course.SynchronousCourse) currentCourse);
                                }
                                Toast.makeText(CourseDetails.this, currentCourse.getCourseTitle() + " was removed", Toast.LENGTH_LONG).show();
                                Intent intentForDelete = new Intent(CourseDetails.this, CourseList.class);
                                intentForDelete.putExtra("userId", createdBy);
                                startActivity(intentForDelete);
                            }
                        }
                    });
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }