package tayler.ut.attendencemanagmentsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherLocalData;

public class AppPreferences {


    public static void setBooleanPreference(Context context, String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void setStringPreference(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String getStringPreference(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    private static int getIntegerPreference(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    private static void setIntegerPreference(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }

    private static boolean setIntegerPrefrenceBlocking(Context context, String key, int value) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).commit();
    }

    private static long getLongPreference(Context context, String key, long defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue);
    }

    private static void setLongPreference(Context context, String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).apply();
    }

    private static void setObjectPreference(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static void clearSharedPreferences(Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
    }

    public static void setTeacherLocalData(Context context, TeacherLocalData teacherLocalData) {
        String teacherLocalDataAsString = null;
        try {
            teacherLocalDataAsString = new ObjectMapper().writeValueAsString(teacherLocalData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(teacherLocalDataAsString!=null) {
            setStringPreference(context, Key.TEACHER_LOCALDATA, teacherLocalDataAsString);
        }
    }

    public static String getTeacherLocalData(Context context) {
        return getStringPreference(context, Key.TEACHER_LOCALDATA, "");
    }

    public static String getStudentLocalData(Context context) {
        return getStringPreference(context, Key.STUDENT_LOCALDATA, "");
    }

    public static void setStudentLocalData(Context context, StudentData teacherLocalData) {
        String teacherLocalDataAsString = null;
        try {
            teacherLocalDataAsString = new ObjectMapper().writeValueAsString(teacherLocalData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(teacherLocalDataAsString!=null) {
            setStringPreference(context, Key.STUDENT_LOCALDATA, teacherLocalDataAsString);
        }
    }

    public static void setTeacherId(Context context, String teacherId) {
        setStringPreference(context, Key.USER_ID, teacherId);
    }

    public static String teacherId(Context context) {
        return getStringPreference(context, Key.USER_ID, "");
    }

    public static void setTeacherMobileNumber(Context context, String teacherId) {
        setStringPreference(context, Key.TEACHER_Mobile, teacherId);
    }

    public static String getTeacherMobileNumberContext(Context context) {
        return getStringPreference(context, Key.TEACHER_Mobile, "");
    }

    public static void setTeacherName(Context context, String teacherId) {
        setStringPreference(context, Key.TEACHER_NAME, teacherId);
    }

    public static String getTeacherName(Context context) {
        return getStringPreference(context, Key.TEACHER_NAME, "");
    }

    public static void setStudentId(Context context, String teacherId) {
        setStringPreference(context, Key.STUDENT_ID, teacherId);
    }

    public static String getStudentId(Context context) {
        return getStringPreference(context, Key.STUDENT_ID, "");
    }

    public interface Key{
        String USER_ID = "userId";
        String TEACHER_NAME = "teacherName";
        String TEACHER_Mobile = "teacherMobileNumber";
        String TABLE_NAME = "tableName";
        String STUDENT_ID = "studentId";
        String SUBJECTS = "subjects";
        String TEACHER_LOCALDATA = "teacherLocalData";
        String STUDENT_LOCALDATA = "studentLocalData";
    }

}
