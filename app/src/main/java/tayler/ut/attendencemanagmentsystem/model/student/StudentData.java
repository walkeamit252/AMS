package tayler.ut.attendencemanagmentsystem.model.student;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class StudentData implements Parcelable{
    String studentId;
    String studentName;

    public StudentData(String studentId, String studentName, String year, String emailId, String mobileNumber) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.year = year;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    String year;
    String emailId;
    String mobileNumber;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.studentName);
        dest.writeString(this.year);
        dest.writeString(this.emailId);
        dest.writeString(this.mobileNumber);
    }

    protected StudentData(Parcel in) {
        this.studentId = in.readString();
        this.studentName = in.readString();
        this.year = in.readString();
        this.emailId = in.readString();
        this.mobileNumber = in.readString();
    }

    public static final Creator<StudentData> CREATOR = new Creator<StudentData>() {
        @Override
        public StudentData createFromParcel(Parcel source) {
            return new StudentData(source);
        }

        @Override
        public StudentData[] newArray(int size) {
            return new StudentData[size];
        }
    };
}
