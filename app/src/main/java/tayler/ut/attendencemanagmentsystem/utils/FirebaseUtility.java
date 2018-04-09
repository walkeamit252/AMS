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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Arrays;
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

    static int yearCount = 0;

    private static List<StudentData> listStudentsFirstYear = new ArrayList<>();
    private static List<StudentData> listStudentsSecondYear = new ArrayList<>();
    private static List<StudentData> listStudents3rdYear = new ArrayList<>();
    private static List<StudentData> listStudents4thYear = new ArrayList<>();
    private static List<TeacherData> listTeacher = new ArrayList<>();

    private static List<AttendanceData> listAttendanceFirstYear = new ArrayList<>();
    private static List<AttendanceData> listAttendanceSecondYear = new ArrayList<>();
    private static List<AttendanceData> listAttendance3rdYear = new ArrayList<>();
    private static List<AttendanceData> listAttendance4thYear = new ArrayList<>();

    private static List<CourseData> listCourseFirstYear = new ArrayList<>();
    private static List<CourseData> listCourseSecondYear = new ArrayList<>();
    private static List<CourseData> listCourse3rdYear = new ArrayList<>();
    private static List<CourseData> listCourseFourthYear = new ArrayList<>();

    private static List<CourseData> listCourseAll = new ArrayList<>();

    private static List<CourseData> currentTeacherCourses = new ArrayList<>();


    private static TeacherData teacherProfileData ;
    private static StudentData studentProfileData;


    private static final String TAG = "FirebaseUtility";

    public static void saveTeacherProfile(String teacherId){

        // teacherId = "-L9YZfr4aq4SLa7Uq2-a";
        Query teacherDetailsQuery = ApplicationContext.getFirebaseDatabaseReference().
                child(FirebaseConstants.TEACHER_TABLE).orderByChild(FirebaseConstants.TEACHER_ID).equalTo(teacherId);
        teacherDetailsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TeacherData teacherData = null;
                //_______________________________ check if server return null _________________________________
                if (dataSnapshot.exists()) {
                    //getting the data at specific node which is selected by input Restaurant name
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teacherData = child.getValue(TeacherData.class);
                        saveTeacherProfile(teacherData);
                        setTeachersSubjectListFromCourseName(teacherData.getSubjects());
                    }
                    String name = teacherData.getName();
                    Log.i(TAG, "onDataChange: name from id is:  "+name);
                } else {
                    Log.i(TAG, " name does'nt exists!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void saveTeacherProfile(TeacherData teacherData){
        teacherProfileData = teacherData;
    }

    public static List<CourseData> getTeachersAddedCourses(){
        return currentTeacherCourses;
    }

    public static void setTeachersSubjectList(CourseData courseData){
        currentTeacherCourses.add(courseData);
    }

    public static void setTeachersSubjectListFromCourseName(String subjectNamewithComma){

        if(!TextUtils.isEmpty(subjectNamewithComma)){

            String[] array = subjectNamewithComma.split(",");
            List<String> list = Arrays. asList(array);
            for(CourseData courseData : listCourseAll){
                if(list.contains(courseData.getCourseName())){
                    setTeachersSubjectList(courseData);
                }
            }
        }
    }

    public static void updateTeacherProfileData(String subjects){
        if(teacherProfileData!=null){
            String mySubjects = teacherProfileData.getSubjects();
            mySubjects = mySubjects+","+subjects;
            teacherProfileData.setSubjects(mySubjects);

            saveTeacherProfile(teacherProfileData);
        }
    }


    public static void saveStudentProfile(final String studentId,String year){

        Query studentDetailsQuery = ApplicationContext.getFirebaseDatabaseReference().
                child(FirebaseConstants.STUDENT_TABLE+year).orderByChild(FirebaseConstants.STUDENT_ID).equalTo(studentId);
        studentDetailsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StudentData studentData = null;
                //_______________________________ check if server return null _________________________________
                if (dataSnapshot.exists()) {
                    //getting the data at specific node which is selected by input Restaurant name
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        studentData = child.getValue(StudentData.class);
                        saveStudentProfile(studentData);

                    }
                    String name = studentData.getName();
                    Log.i(TAG, "onDataChange: name from id is:  "+name);
                } else {
                    yearCount++;
                    if(yearCount==1){
                        saveStudentProfile(studentId,FirebaseConstants.SECOND_YEAR);
                    }
                    else if(yearCount==2){
                        saveStudentProfile(studentId,FirebaseConstants.THIRD_YEAR);

                    }
                    else{
                        saveStudentProfile(studentId,FirebaseConstants.FOURTH_YEAR);
                    }

                    Log.i(TAG, " name does'nt exists!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: " + databaseError.getDetails());
            }
        });
    }

    public static void saveStudentProfile(StudentData studentData){
        studentProfileData = studentData;
    }

    public static TeacherData getTeacherProfileData(){
        return teacherProfileData;
    }

    public static StudentData getStudentProfileData(){
        return studentProfileData;
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
     * @param
     * @param studentData
     */
    public static void updateStudent(StudentData studentData){

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
     * @param
     * @param
     */

    public static void updateTeacher( TeacherData teacherData){
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

    public static void updateAttendance(AttendanceData attendanceData,
                                        String year){
        if(attendanceData!=null){
            String attendanceId = attendanceData.getAttendanceId();
            if(TextUtils.isEmpty(attendanceId)){
                attendanceId = ApplicationContext.getFirebaseDatabaseReference().push().getKey();
                attendanceData.setTeacherId(attendanceId);
            }

            ApplicationContext.getFirebaseDatabaseReference().child(FirebaseConstants.ATTENDANCE_TABLE+year).child(attendanceId)
                    .setValue(attendanceData)
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

    public static void updateCourse( CourseData courseData){
        if(courseData!=null){
            String courseId = courseData.getCourseId();
            if(TextUtils.isEmpty(courseId)){
                courseId = ApplicationContext.getFirebaseDatabaseReference().push().getKey();
                courseData.setCourseId(courseId);
            }

            setTeachersSubjectList(courseData);

            ApplicationContext.getFirebaseDatabaseReference().child(FirebaseConstants.COURSE_TABLE).child(courseId)
                    .setValue(courseData)
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

    public static List<AttendanceData> getAttendanceByYear(Context context,String year){

        List<AttendanceData> listAttendance =  new ArrayList<>();


        return listAttendance;
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

    public static void getStudentByYear(final String year,final OnFirebasseActionListener onFirebasseActionListen ){

        switch (year){
            case FirebaseConstants.FIRSTYEAR :
                listStudentsFirstYear.clear();
                break;
            case FirebaseConstants.SECOND_YEAR :
                listStudentsSecondYear.clear();
                break;
            case FirebaseConstants.THIRD_YEAR :
                listStudents3rdYear.clear();
                break;
            case FirebaseConstants.FOURTH_YEAR :
                listStudents4thYear.clear();
                break;

        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Map<String, Object> hashMapObject = (HashMap<String,Object>) dataSnapshot.getValue();
                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    Gson gson = new Gson();
                    String obj = gson.toJson(mapEntry.getValue());

                    StudentData studentData = new Gson().fromJson(obj, StudentData.class);

                    switch (year){
                        case FirebaseConstants.FIRSTYEAR :
                            listStudentsFirstYear.add(studentData);
                            break;
                        case FirebaseConstants.SECOND_YEAR :
                             listStudentsSecondYear.add(studentData);
                            break;
                        case FirebaseConstants.THIRD_YEAR :
                             listStudents3rdYear.add(studentData);
                            break;
                        case FirebaseConstants.FOURTH_YEAR :
                             listStudents4thYear.add(studentData);
                            break;
                    }
                }
                if(onFirebasseActionListen!=null){
                    onFirebasseActionListen.onSuccess();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                StudentData studentData = dataSnapshot.getValue(StudentData.class);
                Log.i(TAG, "onChildChanged: "+studentData.getName());
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

                Map<String, Object> hashMapObject = (HashMap<String,Object>) dataSnapshot.getValue();
                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    Gson gson = new Gson();
                    String obj = gson.toJson(mapEntry.getValue());
                    TeacherData studentData = gson.fromJson(obj, TeacherData.class);
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

    public static void getTotalCourses( ){

                listCourseFirstYear.clear();
                listCourseSecondYear.clear();
                listCourse3rdYear.clear();
                listCourseFourthYear.clear();
                listCourseAll.clear();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());


                Map<String, Object> hashMapObject = (HashMap<String,Object>) dataSnapshot.getValue();
                for (Map.Entry mapEntry : hashMapObject.entrySet()) {
                    Gson gson = new Gson();
                    String obj = gson.toJson(mapEntry.getValue());

                    CourseData courseData = new Gson().fromJson(obj, CourseData.class);


                    if (courseData != null && !TextUtils.isEmpty(courseData.getCourseYear())) {
                        listCourseAll.add(courseData);
                        switch (courseData.getCourseYear()) {
                            case FirebaseConstants.FIRSTYEAR:
                                listCourseFirstYear.add(courseData);
                                break;
                            case FirebaseConstants.SECOND_YEAR:
                                listCourseSecondYear.add(courseData);
                                break;
                            case FirebaseConstants.THIRD_YEAR:
                                listCourse3rdYear.add(courseData);
                                break;
                            case FirebaseConstants.FOURTH_YEAR:
                                listCourseFourthYear.add(courseData);
                                break;
                        }
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                StudentData studentData = dataSnapshot.getValue(StudentData.class);
                Log.i(TAG, "onChildChanged: "+studentData.getName());
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
                            break;
                        case FirebaseConstants.SECOND_YEAR :
                            listAttendanceSecondYear.add(attendanceData);
                            break;
                        case FirebaseConstants.THIRD_YEAR :
                            listAttendance3rdYear.add(attendanceData);
                            break;
                        case FirebaseConstants.FOURTH_YEAR :
                            listAttendance4thYear.add(attendanceData);
                            break;
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                StudentData studentData = dataSnapshot.getValue(StudentData.class);
                Log.i(TAG, "onChildChanged: "+studentData.getName());
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

    public static String getYearFromCourseName(String courseName){
        String year = "";
        if(listCourseAll!=null && listCourseAll.size()>0 && !TextUtils.isEmpty(courseName)){
            for(CourseData courseData : listCourseAll){
                if(courseName.equalsIgnoreCase(courseData.getCourseName())){
                    year = courseData.getCourseYear();
                }
            }
        }
        return year;
    }

    public interface FirebaseConstants{
        String STUDENT_TABLE        = "Student-";
        String TEACHER_TABLE        = "Teacher";
        String ATTENDANCE_TABLE     = "Attendance-";
        String COURSE_TABLE         = "Course";
        String FIRSTYEAR            = "1stYear";
        String SECOND_YEAR          = "2ndYear";
        String THIRD_YEAR           = "3rdYear";
        String FOURTH_YEAR          = "4thYear";

        // key constants in database
        String STUDENT_ID              = "studentId";
        String EMAILID                 = "emailId";
        String NAME                    = "name";
        String MOBILENUMBER            = "mobileNumber";
        String PASSWORD                = "password";
        String SUBJECT                 = "subject";
        String TEACHER_ID              = "teacherId";
    }

    /**
     * THIS IS ONLY FOR TESTING
     */

    public static void setTestCourseData(){
        int j = 0;
        for(int i = 0;i<4;i++) {
            CourseData courseData = new CourseData("", "Course "+(++j)+" "+ FirebaseUtility.FirebaseConstants.FIRSTYEAR,
                    FirebaseUtility.FirebaseConstants.FIRSTYEAR, "-",
                    "", "0");
            FirebaseUtility.updateCourse(courseData);
        }

        int k = 0;
        for(int i = 0;i<4;i++) {
            CourseData courseData = new CourseData("", "Course "+(++k)+" "+
                    FirebaseUtility.FirebaseConstants.SECOND_YEAR,
                    FirebaseUtility.FirebaseConstants.SECOND_YEAR, "-",
                    "", "0");
            FirebaseUtility.updateCourse(courseData);
        }

        int l = 0;
        for(int i = 0;i<4;i++) {
            CourseData courseData = new CourseData("", "Course "+(++l)+" "+
                    FirebaseUtility.FirebaseConstants.THIRD_YEAR,
                    FirebaseUtility.FirebaseConstants.THIRD_YEAR, "-",
                    "", "0");
            FirebaseUtility.updateCourse(courseData);
        }

        int m = 0;
        for(int i = 0;i<4;i++) {
            CourseData courseData = new CourseData("", "Course "+(++m)+" "+
                    FirebaseUtility.FirebaseConstants.FOURTH_YEAR,
                    FirebaseUtility.FirebaseConstants.FOURTH_YEAR, "-",
                    "", "0");
            FirebaseUtility.updateCourse(courseData);
        }
    }

    public static void setTestStudentsData(){
        int j = 0;
        for(int i = 0;i<10;i++) {
            StudentData studentData = new StudentData("","Student1"+(++j)+" "+
                    FirebaseUtility.FirebaseConstants.FIRSTYEAR,
                    "student"+j+"@gmail.com",
                    "1234567890",
                    FirebaseUtility.FirebaseConstants.FIRSTYEAR,
                    "-");
            FirebaseUtility.updateStudent(studentData);
        }

        int k = 0;
        for(int i = 0;i<10;i++) {
            StudentData studentData = new StudentData("","Student1"+(++k)+" "+
                    FirebaseConstants.SECOND_YEAR,
                    "student"+k+"@gmail.com",
                    "1234567890",
                    FirebaseConstants.SECOND_YEAR,
                    "-");
            FirebaseUtility.updateStudent(studentData);
        }

        int l = 0;
        for(int i = 0;i<10;i++) {
            StudentData studentData = new StudentData("","Student1"+(++l)+" "+
                    FirebaseConstants.THIRD_YEAR,
                    "student"+l+"@gmail.com",
                    "1234567890",
                    FirebaseConstants.THIRD_YEAR,
                    "-");
            FirebaseUtility.updateStudent(studentData);
        }

        int m = 0;
        for(int i = 0;i<10;i++) {
            StudentData studentData = new StudentData("","Student1"+(++m)+" "+
                    FirebaseConstants.FOURTH_YEAR,
                    "student"+m+"@gmail.com",
                    "1234567890",
                    FirebaseUtility.FirebaseConstants.FOURTH_YEAR,
                    "-");
            FirebaseUtility.updateStudent(studentData);
        }
    }

    public interface OnFirebasseActionListener{
        void onSuccess();
        void onFail();
    }

}
