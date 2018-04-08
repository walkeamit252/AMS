package tayler.ut.attendencemanagmentsystem.app;


import android.app.Application;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

public class ApplicationContext extends Application {

    static Context mContext;
    private static FirebaseDatabase database;
    private static DatabaseReference dbRef ;

    public ApplicationContext() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseUtility.getTotalCourses();
    }

    public ApplicationContext(Context mContext) {
        this.mContext = mContext;
        database = FirebaseDatabase.getInstance();
        FirebaseUtility.getTotalCourses();

    }

    public static Context getContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public static FirebaseDatabase getDatabase() {
        return database;
    }
    public static DatabaseReference getFirebaseDatabaseReference() {
        if(dbRef==null) {
            dbRef = FirebaseDatabase.getInstance().getReference();
        }
        return dbRef;
    }

}
