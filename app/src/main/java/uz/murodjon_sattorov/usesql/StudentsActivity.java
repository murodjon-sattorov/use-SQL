package uz.murodjon_sattorov.usesql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uz.murodjon_sattorov.usesql.adapter.StudentAdapter;
import uz.murodjon_sattorov.usesql.core.db.GroupsDb;
import uz.murodjon_sattorov.usesql.core.models.GroupModel;
import uz.murodjon_sattorov.usesql.core.models.StudentModel;
import uz.murodjon_sattorov.usesql.dialogs.GroupInsertDialog;
import uz.murodjon_sattorov.usesql.dialogs.GroupUpdateDialog;

public class StudentsActivity extends AppCompatActivity {

    private StudentAdapter adapter;
    private ListView listView;
    private FloatingActionButton addNewGroup;
    private TextView textView;
    GroupModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        model = (GroupModel) getIntent().getExtras().getSerializable("GROUP_DATA");

        textView = findViewById(R.id.no_item);

        adapter = new StudentAdapter();
        listView = findViewById(R.id.student_list_item);
        addNewGroup = findViewById(R.id.add_new_group);

        listView.setAdapter(adapter);

        adapter.setOnStudentItemClick(new StudentAdapter.OnStudentItemClick() {
            @Override
            public void onItemClick(StudentModel model) {
                Toast.makeText(StudentsActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDelete(StudentModel model) {
                deleteStudent(model);
            }

            @Override
            public void onItemUpdate(StudentModel model) {
                updateStudent(model);
            }
        });

        addNewStudents();
        loadData();

    }

    private void addNewStudents() {
        addNewGroup.setOnClickListener(v -> {
            GroupInsertDialog insertDialog = new GroupInsertDialog(StudentsActivity.this);

            insertDialog.setTitle("Insert student");

            insertDialog.show();

            insertDialog.setOnSaveClickListener(new GroupInsertDialog.OnSaveClickListener() {
                @Override
                public void onSaveClick(String s) {
                    GroupsDb.getInstance().addStudent(s, model.getId());
                    loadData();
                }
            });

        });
    }

    private void deleteStudent(StudentModel models) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Leave group");
        dialog.setMessage("Are you sure you want to leave " + model.getName() + "?");

        dialog.setCancelable(false);

        dialog.setPositiveButton("LEAVING THE GROUP", (dialog12, which) -> {
            GroupsDb.getInstance().deleteStudent(models, model.getId());
            loadData();
        });
        dialog.setIcon(R.drawable.ic_delete_24);

        dialog.setNegativeButton("CANCEL", (dialog1, which) -> {

        });

        dialog.create();
        dialog.show();
    }

    private void updateStudent(StudentModel models) {
        GroupUpdateDialog updateDialog = new GroupUpdateDialog(StudentsActivity.this);

        updateDialog.setTitle("Update group name");

        updateDialog.show();

        updateDialog.setOnSaveClickListener(s -> {
            GroupsDb.getInstance().updateStudent(models, s, model.getId());
            loadData();
        });
    }

    private void loadData() {
        ArrayList<StudentModel> models = GroupsDb.getInstance().getStudents(model.getId());
        adapter.setStudents(models);
        if (adapter.getCount() > 0) {
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }


}