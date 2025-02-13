package com.example.c196.Controller.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c196.Classes.Assessment;
import com.example.c196.Classes.Course;
import com.example.c196.Classes.Note;
import com.example.c196.Classes.Term;
import com.example.c196.Controller.Assessment.AssessmentAdd;
import com.example.c196.Controller.Course.CourseDetailedView;
import com.example.c196.R;
import com.example.c196.Utility.DBConnector;
import com.example.c196.Utility.DataProvider;
import com.example.c196.Utility.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class TermDetailedView extends AppCompatActivity
{

    DBConnector myHelper;
    DataProvider dp = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detailed_view);
        getSupportActionBar().setTitle("Term Detailed View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new DBConnector(TermDetailedView.this);
        myHelper.getWritableDatabase();

        TextView title = findViewById(R.id.termDVTitleTxtVw);
        TextView startDate = findViewById(R.id.termDVStartDateTxtVw);
        TextView endDate = findViewById(R.id.termDVEndDateTxtVw);

        Term term = dp.getAllTerms().get(Term.getSelectedItemIndex());
        title.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());

        final List<String> termCourses = populateListView();
        Boolean isTermCoursesEmpty = termCourses.isEmpty();
        if(!isTermCoursesEmpty)
        {
            ArrayAdapter<String> termCoursesAdapter = new ArrayAdapter<>
                    (
                            this, android.R.layout.simple_list_item_1, termCourses
                    );

            ListView listView = findViewById(R.id.termDVCoursesLstVw);
            listView.setAdapter(termCoursesAdapter);
            listView.setChoiceMode(2);

            listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        saveCourseState(termCourses, i);
                        //changeGuiScreen(view);
                    }
                }
            );
        }
    }

    private void saveCourseState(List<String> courses, int index)
    {
        populateCourses();
        ArrayList<Course> allCourses = dp.getAllCourses();
        String selectedTermCourseTitle = courses.get(index);
        for(Course course : allCourses)
        {
            boolean foundTitleMatch = course.getTitle().contains(selectedTermCourseTitle) ? true : false;
            if(foundTitleMatch)
            {
                Course.setSelectedItemIndex(allCourses.indexOf(course));
                Intent intent = new Intent(this, CourseDetailedView.class);
                startActivity(intent);
                break;
            }
        }

    }

    private void populateCourses()
    {
        String query = "SELECT * from course";
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        ArrayList<Assessment> a = new ArrayList<>();
        ArrayList<Note> n = new ArrayList<>();

        dp.getAllCourses().clear();
        while (cursor.moveToNext())
        {
            Course tempCourse = new Course(cursor.getInt(0), cursor.getString(3), cursor.getString(4),
                    a, n, cursor.getString(5), cursor.getString(6));
            dp.addCourse(tempCourse);
        }
    }

    public void onCLickTermDVModifyBtn(View view)
    {
        Intent intent = new Intent(this, TermModify.class);
        startActivity(intent);
    }

    public void changeGuiScreen(View view)
    {
        Intent intent = new Intent(this, CourseDetailedView.class);
        startActivity(intent);
    }

    private List<String> populateListView()
    {
        List<String> courseList = new ArrayList<>();
        int termId = DataProvider.getAllTerms().get(Term.getSelectedItemIndex()).getId();
        String query = "SELECT * from course where term_id = " + termId;
        Cursor cursor = myHelper.getReadableDatabase().rawQuery(query,null);

        while (cursor.moveToNext())
        {
            courseList.add(cursor.getString(3));
        }

        return courseList;
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
        myHelper = new DBConnector(TermDetailedView.this);
        myHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
        }

        return true;
    }
}
