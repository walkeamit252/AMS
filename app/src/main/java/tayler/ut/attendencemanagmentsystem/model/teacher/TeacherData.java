package tayler.ut.attendencemanagmentsystem.model.teacher;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class TeacherData implements Parcelable{
    String teacherName;
    String TeacherId;
    String subjects;
    String emailId;
    String mobileNumber;

    public TeacherData(String teacherName, String teacherId, String subjects, String emailId, String mobileNumber) {
        this.teacherName = teacherName;
        TeacherId = teacherId;
        this.subjects = subjects;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.TeacherId);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.subjects);
        dest.writeString(this.emailId);
        dest.writeString(this.teacherName);
    }

    protected TeacherData(Parcel in) {
        this.TeacherId = in.readString();
        this.mobileNumber = in.readString();
        this.subjects = in.readString();
        this.emailId = in.readString();
        this.teacherName = in.readString();
    }

    public static final Creator<TeacherData> CREATOR = new Creator<TeacherData>() {
        @Override
        public TeacherData createFromParcel(Parcel source) {
            return new TeacherData(source);
        }

        @Override
        public TeacherData[] newArray(int size) {
            return new TeacherData[size];
        }
    };
}
