package tayler.ut.attendencemanagmentsystem.model.attendance;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class AttendanceData implements Parcelable{

    String attendanceId;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    String subjectName;

    String studentId;
    String teacherId;

    public AttendanceData() {
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public static Creator<AttendanceData> getCREATOR() {
        return CREATOR;
    }

    public AttendanceData(String attendanceid, String teacherId,String studentId,
                          String studentName, String teacherName,
                          String year, String emailId,
                          String mobileNumber, String attandanceDate,
                          boolean isPresent,String subjectname
    ) {
        this.attendanceId = attendanceid;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.year = year;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.attandanceDate = attandanceDate;
        this.isPresent = isPresent;
        this.subjectName = subjectname;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAttandanceDate() {
        return attandanceDate;
    }

    public void setAttandanceDate(String attandanceDate) {
        this.attandanceDate = attandanceDate;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    String studentName;
    String teacherName;
    String year;
    String emailId;
    String mobileNumber;
    String attandanceDate;
    boolean isPresent;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attendanceId);
        dest.writeString(this.subjectName);
        dest.writeString(this.studentId);
        dest.writeString(this.teacherId);
        dest.writeString(this.studentName);
        dest.writeString(this.teacherName);
        dest.writeString(this.year);
        dest.writeString(this.emailId);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.attandanceDate);
        dest.writeByte(this.isPresent ? (byte) 1 : (byte) 0);
    }

    protected AttendanceData(Parcel in) {
        this.attendanceId = in.readString();
        this.subjectName = in.readString();
        this.studentId = in.readString();
        this.teacherId = in.readString();
        this.studentName = in.readString();
        this.teacherName = in.readString();
        this.year = in.readString();
        this.emailId = in.readString();
        this.mobileNumber = in.readString();
        this.attandanceDate = in.readString();
        this.isPresent = in.readByte() != 0;
    }

    public static final Creator<AttendanceData> CREATOR = new Creator<AttendanceData>() {
        @Override
        public AttendanceData createFromParcel(Parcel source) {
            return new AttendanceData(source);
        }

        @Override
        public AttendanceData[] newArray(int size) {
            return new AttendanceData[size];
        }
    };
}
