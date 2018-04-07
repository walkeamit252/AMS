package tayler.ut.attendencemanagmentsystem.app;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sibaprasad on 07/04/18.
 */

public class AMSApplication extends Application {
    static DatabaseReference dbRef = null;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static DatabaseReference getFirebaseReference(){
        if(dbRef==null){
            dbRef = FirebaseDatabase.getInstance().getReference();
        }
        return dbRef;
    }

}
