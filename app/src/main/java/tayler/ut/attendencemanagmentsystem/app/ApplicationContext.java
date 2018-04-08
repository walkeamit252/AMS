package tayler.ut.attendencemanagmentsystem.app;


import android.app.Application;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplicationContext extends Application {

    public Context mContext;
    private static FirebaseDatabase database;
    private static DatabaseReference dbRef ;

    public ApplicationContext() {
        super();
    }

    public ApplicationContext(Context mContext) {
        this.mContext = mContext;
        database = FirebaseDatabase.getInstance();

    }

    public Context getmContext() {
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
