package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.Classes.Course;
import com.example.c196.Classes.Mentor;
import com.example.c196.Classes.Term;
import com.example.c196.Controller.Assessment.AssessmentAdd;
import com.example.c196.Controller.Assessment.AssessmentModify;
import com.example.c196.Controller.Note.NoteAdd;
import com.example.c196.Controller.Term.TermDetailedView;
import com.example.c196.Controller.Term.TermModify;
import com.example.c196.Controller.Term.Terms;
import com.example.c196.R;
import com.example.c196.Utility.AlarmReceiver;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseModify extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();
    AlarmReceiver ar = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_modify);
        getSupportActionBar().setTitle("Modify Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseModify.this);
        myHelper.getWritableDatabase();

        Spinner spinner = findViewById(R.id.courseModifyStatusSpn);
        String[] statusArray = new String[]{"Plan to Take", "Not Started", "In Progress", "Completed"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, statusArray);
        spinner.setAdapter(spinnerAdapter);

        int indexToSelect;
        switch (dp.getAllCourses().get(Course.getSelectedItemIndex()).isStatus()) {
            case "Plan to Take":  indexToSelect = 0;
                spinner.setSelection(indexToSelect);
                break;
            case "Not Started":  indexToSelect = 1;
                spinner.setSelection(indexToSelect);
                break;
            case "In Progress":  indexToSelect = 2;
                spinner.setSelection(indexToSelect);
                break;
            case "Completed":  indexToSelect = 3;
                spinner.setSelection(indexToSelect);
                break;
            case "":  indexToSelect = 0;
                spinner.setSelection(indexToSelect);
                break;
        }

        Course course = dp.getAllCourses().get(Course.getSelectedItemIndex());
        EditText title = findViewById(R.id.courseModifyTitleTxtFld);
        EditText startDate = findViewById(R.id.courseModifyStartDateTxtFld);
        EditText endDate = findViewById(R.id.courseModifyEndDateTxtFld);

        title.setText(course.getTitle());
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());

        int courseId = course.getId();
        int mentorId = getMentorId(courseId).get(0);
        int arraySize = getCourseMentor(mentorId).size();
        boolean arrayHasValues = arraySize > 0;

        if(!(mentorId == -1) && arrayHasValues)
        {
            Spinner spinner2 = findViewById(R.id.courseMentorSpn);
            List<String> mentorNames = populateMentorsList();
            ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, mentorNames);
            spinner2.setAdapter(spinnerAdapter2);

            String name = (getCourseMentor(mentorId).get(0)).getName();
            for(String mentorName : mentorNames)
            {
                if(mentorName.contains(name))
                {
                    int index = mentorNames.indexOf(mentorName);
                    spinner2.setSelection(index);
                }
            }
        }
        else
        {
            Spinner spinner2 = findViewById(R.id.courseMentorSpn);
            List<String> mentorNames = populateMentorsList();
            ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, mentorNames);
            spinner2.setAdapter(spinnerAdapter2);
        }
    }

    public void courseModSaveBtn(View view)
    {
        String title = ((EditText) findViewById(R.id.courseModifyTitleTxtFld)).getText().toString();
        String status = ((Spinner) findViewById(R.id.courseModifyStatusSpn)).getSelectedItem().toString();
        String startDate = ((EditText) findViewById(R.id.courseModifyStartDateTxtFld)).getText().toString();
        String endDate = ((EditText) findViewById(R.id.courseModifyEndDateTxtFld)).getText().toString();
        Boolean valuesNotNull = !title.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()
                && UtilityMethods.isValidDate(startDate) == true
                && UtilityMethods.isValidDate(endDate) == true;
        int mentorIndex = ((Spinner) findViewById(R.id.courseMentorSpn)).getSelectedItemPosition();
        Mentor mentor = dp.getAllMentors().get(mentorIndex);
        int mentorId = mentor.getId();
        try
        {
            if(valuesNotNull)
            {
                Long startDateNotif = getDateDelayMiliseconds(startDate);
                int startDateNotifId = UtilityMethods.createUniqueId();
                String startDateTitle = title + " Reminder";
                String startDateMessage = "Notification for " + title + " on date " + startDate;
                scheduleNotification(CourseModify.this, startDateNotif, startDateNotifId, startDateTitle, startDateMessage);
                Long endDateNotif = getDateDelayMiliseconds(endDate);
                int endDateNotifId = UtilityMethods.createUniqueId();
                String endDateTitle = title + " Reminder";
                String endDateMessage = "Notification for " + title + " on date " + endDate;
                scheduleNotification(CourseModify.this, endDateNotif, endDateNotifId, endDateTitle, endDateMessage);

                //isCheckBoxTicked(startDate);
                //isCheckBoxTicked(endDate);

                String sqlQuery = "update course set mentor_id = " + mentorId + ", title = \""
                        + title + "\", status = \"" + status
                        + "\", start_date = \"" + startDate + "\", end_date = \"" + endDate + "\" where course_id = "
                        + DataProvider.getAllCourses().get(Course.getSelectedItemIndex()).getId();
                myHelper.insertRecord(sqlQuery);

                Intent intent = new Intent(this, Courses.class);
                startActivity(intent);
            }
            else
            {
                UtilityMethods.displayGuiMessage(CourseModify.this, "Please make sure values are not null and date value is entered correctly.");
            }
        }
        catch(Exception e)
        {
            //
        }
    }

    public void scheduleNotification(Context context, long delay, int notificationId, String title, String message)
    {
        //delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_launcher_background)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, CourseModify.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Long getDateDelayMiliseconds(String sDate) throws ParseException
    {
        boolean checked = ((CheckBox) findViewById(R.id.courseChkBx)).isChecked();
        Long timeInMiliseconds = 0L;
        if(checked)
        {
            if(!sDate.isEmpty())
            {
                EditText tempTitle = findViewById(R.id.courseModifyTitleTxtFld);
                String newTitle = tempTitle.getText().toString();
                String title = newTitle + " Reminder";
                String message = "Notification for " + newTitle + " on date " + sDate;


                ar.setTitle(title);
                ar.setMessage(message);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date date = sdf.parse(sDate);
                long addTime = 20000;
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);
                int seconds = Calendar.getInstance().get(Calendar.SECOND);
                int miliseconds = (Calendar.getInstance().get(Calendar.MILLISECOND));
                c.set(c.HOUR_OF_DAY, hour);
                c.set(c.MINUTE, minute);
                c.set(c.SECOND, seconds);
                c.set(c.MILLISECOND, miliseconds);
                UtilityMethods.displayGuiMessage(CourseModify.this, "" + c);
                timeInMiliseconds = date.getTime();

                //alendar c = Calendar.getInstance();
                //date.setTime(Calendar.getInstance().getTimeInMillis() + addTime);
                //UtilityMethods.displayGuiMessage(CourseModify.this, "" + date);

                //onTimeSet(c, date, sDate);
            }
        }
        return timeInMiliseconds;
    }

    public void onTimeSet(Calendar c, Date date, String dateString)
    {
        String[] dateValues = dateString.split("/");
        c.set(Integer.valueOf(dateValues[2]), Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]));
        c.setTime(date);
        //UtilityMethods.displayGuiMessage(CourseModify.this, "" + c.get(Month));

        startAlarm(c);
    }

    private void startAlarm(Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void startAlarm2(Long time)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void onClickModCourseDeleteBtn(View view)
    {
        Course course = dp.getAllCourses().get(Course.getSelectedItemIndex());
        String query1 = "delete from course where title = \"" + course.getTitle() + "\";";
        myHelper.deleteRecord(query1);

        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
    }

    private List<String> populateMentorsList()
    {
        List<String> mentorList = new ArrayList<>();
        String query = "SELECT * from mentor";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        dp.getAllMentors().clear();
        while (cursor.moveToNext())
        {
            Mentor tempMentor = new Mentor(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
            dp.addMentor(tempMentor);
            mentorList.add(cursor.getString(1));
        }

        return mentorList;
    }

    private List<Mentor> getCourseMentor(int mentorId)
    {
        String query = "select * from mentor where mentor_id = " + mentorId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        List<Mentor> mentor = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Mentor tempMentor = new Mentor(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            mentor.add(tempMentor);
        }

        if(mentor.size() > 0)
        {
            String query2 = "select * from mentor";
            Cursor cursor2 = myHelper.getReadableDatabase().rawQuery(query2, null);

            dp.getAllMentors().clear();
            while (cursor2.moveToNext())
            {
                Mentor tempMentor = new Mentor(cursor2.getInt(0), cursor2.getString(1),
                        cursor2.getString(2), cursor2.getString(3));
                dp.addMentor(tempMentor);
            }
        }

        return mentor;
    }

    private List<Integer> getMentorId(int courseId)
    {
        String query = "select mentor_id from course where course_id = " + courseId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        List<Integer> ids = new ArrayList<>();
        while (cursor.moveToNext())
        {
            ids.add(cursor.getInt(0));
        }

        return ids;
    }



    public void onClickAddAssessment(View view)
    {
        Intent intent = new Intent(this, AssessmentAdd.class);
        startActivity(intent);
    }

    public void modCourseAddNoteBtn(View view)
    {
        Intent intent = new Intent(this, NoteAdd.class);
        startActivity(intent);
    }


    public void onClickModifyCoModAssBtn(View view)
    {
        Intent intent = new Intent(this, CourseManageAssessments.class);
        startActivity(intent);
    }

    public void onClickModCourseNoteBtn(View view)
    {
        Intent intent = new Intent(this, CourseDeleteNote.class);
        startActivity(intent);
    }

    public void courseModCancelBtn(View view)
    {
        Intent intent = new Intent(this, CourseDetailedView.class);
        startActivity(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        myHelper.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        myHelper = new DBConnector(CourseModify.this);
        myHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, CourseDetailedView.class);
            startActivity(intent);
        }

        return true;
    }
}
