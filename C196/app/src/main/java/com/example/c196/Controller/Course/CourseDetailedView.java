package com.example.c196.Controller.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Mentor;
import com.example.c196.Classes.Term;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailedView extends AppCompatActivity
{
    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed_view);
        getSupportActionBar().setTitle("Course Detailed View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(CourseDetailedView.this);
        myHelper.getWritableDatabase();

        TextView title = findViewById(R.id.courseDVTitleTxtVw);
        TextView startDate = findViewById(R.id.courseDVStartDateTxtVw2);
        TextView endDate = findViewById(R.id.courseDVEndDateTxtVw);
        TextView status = findViewById(R.id.courseStatus);
        TextView mentorName = findViewById(R.id.mentorNameTxtVw);
        TextView mentorPhone = findViewById(R.id.mPhone);
        TextView mentorEmail = findViewById(R.id.mentorEmailTxtVw);

        Course course = dp.getAllCourses().get(Course.getSelectedItemIndex());
        title.setText(course.getTitle());
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
        status.setText(course.getStatus());
        int courseId = course.getId();
        int mentorId = getMentorId(courseId).get(0);

        if(!(mentorId == -1) && getCourseMentor(mentorId).size() > 0)
        {

            Mentor mentor = getCourseMentor(mentorId).get(0);
            mentorName.setText(mentor.getName());
            mentorPhone.setText(mentor.getPhone());
            mentorEmail.setText(mentor.getEmail());
        }


        List<String> courseAssessments = populateListView();
        Boolean isCourseAssessmentsNull = courseAssessments.isEmpty();
        if(!isCourseAssessmentsNull)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<String>
                    (
                            this, android.R.layout.simple_list_item_1, courseAssessments
                    );

            ListView listView = findViewById(R.id.courseDVAssessmentLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            changeGuiScreen();
                        }
                    }
            );
        }
    }

    private List<Mentor>  getCourseMentor(int mentorId)
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

        return mentor;
    }

    private List<Integer> getMentorId(int courseId)
    {
        String query = "select mentor_id from course where course_id = " + courseId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        List<Integer> ids = new ArrayList<>();
        int mentorId = -1;
        while (cursor.moveToNext())
        {
            ids.add(cursor.getInt(0));
        }

        return ids;
    }

    private void changeGuiScreen()
    {
        Intent intent = new Intent(this, CourseManageAssessments.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        int courseId = DataProvider.getAllCourses().get(Course.getSelectedItemIndex()).getId();
        String query = "SELECT * from assessment where course_id = " + courseId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseList.add(cursor.getString(2));
        }

        return courseList;
    }

    public void onClickModifyCourseBtn(View view)
    {
        Intent intent = new Intent(this, CourseModify.class);
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
        myHelper = new DBConnector(CourseDetailedView.this);
        myHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Courses.class);
            startActivity(intent);
        }

        return true;
    }
}
