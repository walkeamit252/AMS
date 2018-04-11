package tayler.ut.attendencemanagmentsystem.utils;


public class Constants {

    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";

    //this is the pic pdf code used in file chooser
    public static final  int PICK_PDF_CODE = 2342;

    public static final String USER_MODEL = "user_model";
    public static final String STUDENT_LOGIN_FLAG = "studentlogin";
    public static final String TEACHER_LOGIN_FLAG = "teacherlogin";

    public static final int TAB_TAKE_ATTENDANCE = 1;
    public static final int TAB_VIEW_ATTENDANCE = 2;

    public interface BundelKays{
        String COURSE_NAME = "courseName";
        String TAB_TYPE = "tabType";
        String COURSE_DATA = "COURSE_DATA";
        String ATTENDANCE_LIST = "attendanceList";
    }

}
