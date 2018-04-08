package tayler.ut.attendencemanagmentsystem.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tayler.ut.attendencemanagmentsystem.app.ApplicationContext;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class FirebaseUtility {



    static List<StudentData> listStudentsFirstYear = new ArrayList<>();
    static List<StudentData> listStudentsSecondYear = new ArrayList<>();
    static List<StudentData> listStudents3rdYear = new ArrayList<>();
    static List<StudentData> listStudents4thYear = new ArrayList<>();
    static List<TeacherData> listTeacher = new ArrayList<>();

    static List<AttendanceData> listAttendanceFirstYear = new ArrayList<>();
    static List<AttendanceData> listAttendanceSecondYear = new ArrayList<>();
    static List<AttendanceData> listAttendance3rdYear = new ArrayList<>();
    static List<AttendanceData> listAttendance4thYear = new ArrayList<>();

    static List<CourseData> listCourseFirstYear = new ArrayList<>();
    static List<CourseData> listCourseSecondYear = new ArrayList<>();
    static List<CourseData> listCourse3rdYear = new ArrayList<>();
    static List<CourseData> listCourseFourthYear = new ArrayList<>();

    static List<CourseData> listCourseAll = new ArrayList<>();




    private static final String TAG = "FirebaseUtility";

    public static void saveTeacherDetails(){

    }

    public static void saveStudentDetails(){

    }

    public static List<CourseData> getCourses( String year ){
        switch (year){
            case FirebaseConstants.FIRSTYEAR :
                return listCourseFirstYear;
            case FirebaseConstants.SECOND_YEAR :
                return listCourseSecondYear;
            case FirebaseConstants.THIRD_YEAR :
                return listCourse3rdYear;
            case FirebaseConstants.FOURTH_YEAR :
                return listCourseFourthYear;

        }
        return listCourseAll;
    }

    public static List<StudentData> getStudentListByYears(String year){

        switch (year){
            case FirebaseConstants.FIRSTYEAR :
                return listStudentsFirstYear;
            case FirebaseConstants.SECOND_YEAR :
                return listStudentsSecondYear;
            case FirebaseConstants.THIRD_YEAR :
                return listStudents3rdYear;
            case FirebaseConstants.FOURTH_YEAR :
                return listStudents4thYear;

        }
        return null;
    }

    public static List<AttendanceData> getAttendanceByYears(String year){

        switch (year){
            case FirebaseConstants.FIRSTYEAR :
                return listAttendanceFirstYear;
            case FirebaseConstants.SECOND_YEAR :
                return listAttendanceSecondYear;
            case FirebaseConstants.THIRD_YEAR :
                return listAttendance3rdYear;
            case FirebaseConstants.FOURTH_YEAR :
                return listAttendance4thYear;

        }
        return null;
    }


    public static List<TeacherData> getTeacherList(){
        return listTeacher;
    }



    /**
     * Update Student Here
     *
     * @param context
     * @param studentData
     */
    public static void updateStudent(Context context, StudentData studentData){

        if(studentData!=null){
            String userId = studentData.getStudentId();
            if(TextUtils.isEmpty(studentData.getStudentId())){
                userId = ApplicationContext.getFirebaseDatabaseReference().push().getKey();
                studentData.setStudentId(userId);
            }

            ApplicationContext.getFirebaseDatabaseReference().child(FirebaseConstants.STUDENT_TABLE+studentData.getYear()).child(userId)
                    .setValue(studentData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "onComplete: success ");
                            } else {
                                Log.i(TAG, "onComplete: fail");
                            }
                        }
                    });
        }
    }

    /**
     * Update Teachers Subjects
     *
     * @param context
     * @param
     */

    public static void updateTeacher(Context context, TeacherData teacherData){
        if(teacherData!=null){
            String userId = teacherData.getTeacherId();
            if(TextUtils.isEmpty(userId)){
                userId = ApplicationContext.getFirebaseDatabaseReference().push().getKey();
                teacherData.setTeacherId(userId);
            }

            ApplicationContext.getFirebaseDatabaseReference().child(FirebaseConstants.TEACHER_TABLE).child(userId)
                    .setValue(teacherData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "onComplete: success ");
                            } else {
                                Log.i(TAG, "onComplete: fail");
                            }
                        }
                    });
        }
    }

    public static String getYearFromSubjects(Context context, String subject){
        String year = "";

        return year;
    }

    public static List<String> getListofSubjectFromYear(String year){
        List<String> listSubjects = new ArrayList<>();
        return listSubjects;
    }

    public static void updateAttendance(List<StudentData> studentDataList ){
        if(studentDataList!=null && studentDataList.size()>0){
            for(StudentData studentData : studentDataList){
                // update code
            }
        }
    }

    /**
     * get all the student list by year
     * @param year
     */

    public static void getStudentByYear(final String year ){

        switch (year){
            case FirebaseConstants.FIRSTYEAR :
                listStudentsFirstYear.clear();
            case FirebaseConstants.SECOND_YEAR :
                listStudentsSecondYear.clear();
            case FirebaseConstants.THIRD_YEAR :
                listStudents3rdYear.clear();
            case FirebaseConstants.FOURTH_YEAR :
                listStudents4thYear.clear();

        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Map<String, StudentData> hashMapObject = (HashMap<String,StudentData>) dataSnapshot.getValue();

                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    System.out.println("Key: "+mapEntry.getKey() + " & Value: " + mapEntry.getValue());

                    Log.i(TAG, "onChildAdded: "+mapEntry.getValue().toString());

                    Gson g = new Gson();
                    StudentData studentData = g.fromJson(mapEntry.getValue().toString(), StudentData.class);


                    switch (year){
                        case FirebaseConstants.FIRSTYEAR :
                            listStudentsFirstYear.add(studentData);
                        case FirebaseConstants.SECOND_YEAR :
                             listStudentsSecondYear.add(studentData);
                        case FirebaseConstants.THIRD_YEAR :
                             listStudents3rdYear.add(studentData);
                        case FirebaseConstants.FOURTH_YEAR :
                             listStudents4thYear.add(studentData);

                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                StudentData studentData = dataSnapshot.getValue(StudentData.class);
                Log.i(TAG, "onChildChanged: "+studentData.getStudentName());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        };
        ApplicationContext.getFirebaseDatabaseReference().addChildEventListener(childEventListener);



       /*ApplicationContext.getFirebaseDatabaseReference().child(FirebaseConstants.STUDENT_TABLE+year).orderByChild("year")
                .equalTo(year).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    listStudent.add(adSnapshot.getValue(StudentData.class));
                }
                Log.d(TAG, "no of ads for search is "+listStudent.size());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Error trying to get student for " +year+ " "+databaseError);
            }
        });*/

    }

    /**
     * Get Teacher List
     */

    public static void getTotalTeacherListFromFirebase( ){

        listTeacher.clear();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Map<String, StudentData> hashMapObject = (HashMap<String,StudentData>) dataSnapshot.getValue();

                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    System.out.println("Key: "+mapEntry.getKey() + " & Value: " + mapEntry.getValue());

                    Log.i(TAG, "onChildAdded: "+mapEntry.getValue().toString());

                    Gson g = new Gson();
                    TeacherData studentData = g.fromJson(mapEntry.getValue().toString(), TeacherData.class);
                    listTeacher.add(studentData);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ApplicationContext.getFirebaseDatabaseReference().addChildEventListener(childEventListener);



       /*ApplicationContext.getFirebaseDatabaseReference().child(FirebaseConstants.STUDENT_TABLE+year).orderByChild("year")
                .equalTo(year).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    listStudent.add(adSnapshot.getValue(StudentData.class));
                }
                Log.d(TAG, "no of ads for search is "+listStudent.size());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Error trying to get student for " +year+ " "+databaseError);
            }
        });*/

    }

    public interface FirebaseConstants{
        String STUDENT_TABLE        = "Student-";
        String TEACHER_TABLE        = "Teacher";
        String FIRSTYEAR            = "1stYear";
        String SECOND_YEAR          = "2ndYear";
        String THIRD_YEAR           = "3rdYear";
        String FOURTH_YEAR          = "4thYear";

    }
    /**
     * This List will show TO Student Registration
     */
    public static List<String> getYearList(){
        List<String> listYears = new ArrayList<>();
        listYears.add(FirebaseConstants.FIRSTYEAR);
        listYears.add(FirebaseConstants.SECOND_YEAR);
        listYears.add(FirebaseConstants.THIRD_YEAR);
        listYears.add(FirebaseConstants.FOURTH_YEAR);
        return listYears;
    }

    public static void getTotalCourses(final String year){

        switch (year){
            case FirebaseConstants.FIRSTYEAR :
                listCourseFirstYear.clear();
            case FirebaseConstants.SECOND_YEAR :
                listCourseSecondYear.clear();
            case FirebaseConstants.THIRD_YEAR :
                listCourse3rdYear.clear();
            case FirebaseConstants.FOURTH_YEAR :
                listCourseFourthYear.clear();
        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Map<String, CourseData> hashMapObject = (HashMap<String,CourseData>) dataSnapshot.getValue();

                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    System.out.println("Key: "+mapEntry.getKey() + " & Value: " + mapEntry.getValue());

                    Log.i(TAG, "onChildAdded: "+mapEntry.getValue().toString());

                    Gson g = new Gson();
                    CourseData courseData = g.fromJson(mapEntry.getValue().toString(), CourseData.class);


                    switch (year){
                        case FirebaseConstants.FIRSTYEAR :
                            listCourseFirstYear.add(courseData);
                        case FirebaseConstants.SECOND_YEAR :
                            listCourseSecondYear.add(courseData);
                        case FirebaseConstants.THIRD_YEAR :
                            listCourse3rdYear.add(courseData);
                        case FirebaseConstants.FOURTH_YEAR :
                            listCourseFourthYear.add(courseData);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                StudentData studentData = dataSnapshot.getValue(StudentData.class);
                Log.i(TAG, "onChildChanged: "+studentData.getStudentName());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        };
        ApplicationContext.getFirebaseDatabaseReference().addChildEventListener(childEventListener);
    }


    public static void getTotalAttendance(final String year){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Map<String, AttendanceData> hashMapObject = (HashMap<String,AttendanceData>) dataSnapshot.getValue();

                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    System.out.println("Key: "+mapEntry.getKey() + " & Value: " + mapEntry.getValue());

                    Log.i(TAG, "onChildAdded: "+mapEntry.getValue().toString());

                    Gson g = new Gson();
                    AttendanceData attendanceData = g.fromJson(mapEntry.getValue().toString(), AttendanceData.class);


                    switch (year){
                        case FirebaseConstants.FIRSTYEAR :
                            listAttendanceFirstYear.add(attendanceData);
                        case FirebaseConstants.SECOND_YEAR :
                            listAttendanceSecondYear.add(attendanceData);
                        case FirebaseConstants.THIRD_YEAR :
                            listAttendance3rdYear.add(attendanceData);
                        case FirebaseConstants.FOURTH_YEAR :
                            listAttendance4thYear.add(attendanceData);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                StudentData studentData = dataSnapshot.getValue(StudentData.class);
                Log.i(TAG, "onChildChanged: "+studentData.getStudentName());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        };
        ApplicationContext.getFirebaseDatabaseReference().addChildEventListener(childEventListener);
    }


}
