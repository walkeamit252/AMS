package tayler.ut.attendencemanagmentsystem.model.course;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class CourseData implements Parcelable {
    String courseId;
    String courseName;
    String courseYear;
    String syllabusFilePath;
    String teacherName;
    String totalStudents;

    public CourseData(String courseId, String courseName,
                      String courseYear, String syllabusFilePath,
                      String teacherName, String totalStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.syllabusFilePath = syllabusFilePath;
        this.teacherName = teacherName;
        this.totalStudents = totalStudents;
    }

    public CourseData(){

    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getSyllabusFilePath() {
        return syllabusFilePath;
    }

    public void setSyllabusFilePath(String syllabusFilePath) {
        this.syllabusFilePath = syllabusFilePath;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseId);
        dest.writeString(this.courseName);
        dest.writeString(this.courseYear);
        dest.writeString(this.syllabusFilePath);
        dest.writeString(this.teacherName);
        dest.writeString(this.totalStudents);
    }

    protected CourseData(Parcel in) {
        this.courseId = in.readString();
        this.courseName = in.readString();
        this.courseYear = in.readString();
        this.syllabusFilePath = in.readString();
        this.teacherName = in.readString();
        this.totalStudents = in.readString();
    }

    public static final Creator<CourseData> CREATOR = new Creator<CourseData>() {
        @Override
        public CourseData createFromParcel(Parcel source) {
            return new CourseData(source);
        }

        @Override
        public CourseData[] newArray(int size) {
            return new CourseData[size];
        }
    };



}
