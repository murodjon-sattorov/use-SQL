package uz.murodjon_sattorov.usesql.core.app;

import android.app.Application;

import uz.murodjon_sattorov.usesql.core.db.GroupsDb;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GroupsDb.init(this);
    }
}
