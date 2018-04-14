package tayler.ut.attendencemanagmentsystem.commonui.fragments;

/**
 * Created by amitwalke on 4/13/18.
 */

public class SpinnerModel {

    String monthName;
    int monthNumber;

    public int getMonthNumber() {
        return monthNumber;
    }

    public SpinnerModel(String monthName, int monthNumber) {
        this.monthName = monthName;
        this.monthNumber = monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

}
