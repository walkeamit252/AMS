package tayler.ut.attendencemanagmentsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;


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

    public static void setTeacherLocalData(Context context, TeacherLocalData teacherData) {
        String teacherLocalDataString =  new Gson().toJson(teacherData);
        setStringPreference(context, Key.TEACHER_LOCALDATA, teacherLocalDataString);
    }

    public static TeacherLocalData getTeacherLocalData(Context context) {
        String json = getStringPreference(context, Key.TEACHER_LOCALDATA, "");
        return new Gson().fromJson(json, TeacherLocalData.class);
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

    public static String setTeacherName(Context context) {
        return getStringPreference(context, Key.TEACHER_NAME, "");
    }

    public interface Key{
        String USER_ID = "userId";
        String TEACHER_NAME = "teacherName";
        String TEACHER_Mobile = "teacherMobileNumber";
        String TABLE_NAME = "tableName";
        String SUBJECTS = "subjects";
        String TEACHER_LOCALDATA = "teacherLocalData";
    }

}
