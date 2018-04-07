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

import tayler.ut.attendencemanagmentsystem.app.AMSApplication;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class FirebaseUtility {

    public static List<StudentData> listStudentsFirstYear = new ArrayList<>();
    public static List<StudentData> listStudentsSecondYear = new ArrayList<>();
    public static List<StudentData> listStudents3rdYear = new ArrayList<>();
    public static List<StudentData> listStudents4thYear = new ArrayList<>();
    public static List<TeacherData> listTeacher = new ArrayList<>();


    private static final String TAG = "FirebaseUtility";

    public static void saveTeacherDetails(){

    }

    public static void saveStudentDetails(){

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
                userId = AMSApplication.getFirebaseReference().push().getKey();
                studentData.setStudentId(userId);
            }
            AMSApplication.getFirebaseReference().child(FirebaseConstants.STUDENT_TABLE+studentData.getYear()).child(userId)
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

    public static void updateTeacher(Context context, TeacherData teacherData){
        if(teacherData!=null){
            String userId = teacherData.getTeacherId();
            if(TextUtils.isEmpty(userId)){
                userId = AMSApplication.getFirebaseReference().push().getKey();
                teacherData.setTeacherId(userId);
            }
            AMSApplication.getFirebaseReference().child(FirebaseConstants.TEACHER_TABLE).child(userId)
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
        final List<StudentData> listStudent = new ArrayList<>();


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
        AMSApplication.getFirebaseReference().addChildEventListener(childEventListener);



       /*AMSApplication.getFirebaseReference().child(FirebaseConstants.STUDENT_TABLE+year).orderByChild("year")
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
    public List<String> getYearList(){
        List<String> listYears = new ArrayList<>();
        listYears.add(FirebaseConstants.FIRSTYEAR);
        listYears.add(FirebaseConstants.SECOND_YEAR);
        listYears.add(FirebaseConstants.THIRD_YEAR);
        listYears.add(FirebaseConstants.FOURTH_YEAR);
        return listYears;
    }

}
