package uz.murodjon_sattorov.usesql.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import uz.murodjon_sattorov.usesql.core.lib.DbHelper;
import uz.murodjon_sattorov.usesql.core.models.GroupModel;
import uz.murodjon_sattorov.usesql.core.models.StudentModel;

public class GroupsDb extends DbHelper {

    private static GroupsDb groupsDb;

    public GroupsDb(@Nullable Context context) {
        super(context, "groups.db");
    }

    public static void init(Context context) {
        if (groupsDb == null) {
            groupsDb = new GroupsDb(context);
        }
    }

    public static GroupsDb getInstance() {
        return groupsDb;
    }



    /***
     * Add, delete and update groups a few
     *
     * **/

    public ArrayList<GroupModel> getGroups() {
        ArrayList<GroupModel> groups = new ArrayList<>();

        String s = "SELECT * FROM groups";

        Log.d("GroupsDb", "getGroups: " + s);

        Cursor cursor = mDataBase.rawQuery(s, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            groups.add(
                    new GroupModel(
                            cursor.getInt(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("name"))
                    )
            );
            cursor.moveToNext();
        }

        cursor.close();

        return groups;
    }

    public long addGroup(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        return mDataBase.insert("groups", null, contentValues);
    }

    public long deleteGroups(GroupModel groupModel) {
        return mDataBase.delete("groups", "_id=?", new String[]{groupModel.getId() + ""});
    }

    public long updateGroups(GroupModel groupModel, String text) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", text);
        return mDataBase.update("groups", contentValues, "_id=?", new String[]{groupModel.getId() + ""});
    }

    /***
     * Add, delete and update students a few
     *
     * **/

    public ArrayList<StudentModel> getStudents(long groupId) {
        ArrayList<StudentModel> students = new ArrayList<>();

        Cursor cursor = mDataBase.rawQuery("SELECT * FROM students WHERE group_id = " + groupId, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            students.add(
                    new StudentModel(
                            cursor.getInt(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getInt(cursor.getColumnIndex("group_id"))
                    )
            );
            cursor.moveToNext();
        }

        cursor.close();

        return students;
    }

    public long addStudent(String name, Long groupId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("group_id", groupId);
        return mDataBase.insert("students", null, contentValues);
    }

    public long deleteStudent(StudentModel groupModel, Long groupId) {
        return mDataBase.delete("students", "_id=?", new String[]{groupId + ""});
    }

    public long updateStudent(StudentModel groupModel, String text, Long groupId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", text);
        return mDataBase.update("students", contentValues, "_id=?", new String[]{groupId + ""});
    }

}
