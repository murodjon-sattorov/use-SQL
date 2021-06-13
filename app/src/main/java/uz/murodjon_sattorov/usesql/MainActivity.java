package uz.murodjon_sattorov.usesql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uz.murodjon_sattorov.usesql.adapter.GroupsAdapter;
import uz.murodjon_sattorov.usesql.core.db.GroupsDb;
import uz.murodjon_sattorov.usesql.core.models.GroupModel;
import uz.murodjon_sattorov.usesql.dialogs.GroupInsertDialog;
import uz.murodjon_sattorov.usesql.dialogs.GroupUpdateDialog;

public class MainActivity extends AppCompatActivity {

    private GroupsAdapter adapter;
    private ListView listView;
    private FloatingActionButton addNewGroup;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Groups");

        textView = findViewById(R.id.no_item);

        adapter = new GroupsAdapter();
        listView = findViewById(R.id.main_list_item);
        addNewGroup = findViewById(R.id.add_new_group);

        listView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GroupsAdapter.OnItemClick() {
            @Override
            public void onItemClick(GroupModel model) {
                Intent intent = new Intent(MainActivity.this, StudentsActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("GROUP_DATA", model);

                intent.putExtras(bundle);

                startActivity(intent);
            }

            @Override
            public void onItemDelete(GroupModel model) {
                deleteGroup(model);
            }

            @Override
            public void onItemUpdate(GroupModel model) {
                updateGroup(model);
            }
        });

        addNewGroups();
        loadData();

    }

    private void addNewGroups() {
        addNewGroup.setOnClickListener(v -> {
            GroupInsertDialog insertDialog = new GroupInsertDialog(MainActivity.this);

            insertDialog.setTitle("Insert group");

            insertDialog.show();

            insertDialog.setOnSaveClickListener(new GroupInsertDialog.OnSaveClickListener() {
                @Override
                public void onSaveClick(String s) {
                    GroupsDb.getInstance().addGroup(s);
                    loadData();
                }
            });

        });
    }

    private void deleteGroup(GroupModel model) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Leave group");
        dialog.setMessage("Are you sure you want to leave " + model.getName() + "?");

        dialog.setCancelable(false);

        dialog.setPositiveButton("LEAVING THE GROUP", (dialog12, which) -> {
            GroupsDb.getInstance().deleteGroups(model);
            loadData();
        });
        dialog.setIcon(R.drawable.ic_delete_24);

        dialog.setNegativeButton("CANCEL", (dialog1, which) -> {

        });

        dialog.create();
        dialog.show();
    }

    private void updateGroup(GroupModel model) {
        GroupUpdateDialog updateDialog = new GroupUpdateDialog(MainActivity.this);

        updateDialog.setTitle("Update group name");

        updateDialog.show();

        updateDialog.setOnSaveClickListener(s -> {
            GroupsDb.getInstance().updateGroups(model, s);
            loadData();
        });
    }

    private void loadData() {
        ArrayList<GroupModel> models = GroupsDb.getInstance().getGroups();
        adapter.setGroups(models);
        if (adapter.getCount() > 0) {
            Log.d("INVISIBLE", "loadData: " + adapter.getCount());
            textView.setVisibility(View.INVISIBLE);
        } else {
            Log.d("VISIBLE", "loadData: " + adapter.getCount());
            textView.setVisibility(View.VISIBLE);
        }
    }

}