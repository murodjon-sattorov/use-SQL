package uz.murodjon_sattorov.usesql.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

import uz.murodjon_sattorov.usesql.R;
import uz.murodjon_sattorov.usesql.core.models.GroupModel;
import uz.murodjon_sattorov.usesql.core.models.StudentModel;

public class StudentAdapter extends BaseAdapter {

    private final ArrayList<StudentModel> models = new ArrayList<>();

    private OnStudentItemClick onStudentItemClick;

    public void setOnStudentItemClick(OnStudentItemClick onStudentItemClick){
        this.onStudentItemClick = onStudentItemClick;
    }

    public void setStudents(ArrayList<StudentModel> models) {
        this.models.clear();
        this.models.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public StudentModel getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);

        ImageView deleteGroup = view.findViewById(R.id.delete_group);
        ImageView updateGroup = view.findViewById(R.id.update_group);
        TextView groupName = view.findViewById(R.id.group_name);

        StudentModel studentModel = getItem(position);
        groupName.setText(studentModel.getName());

        ConstraintLayout constraintLayout = view.findViewById(R.id.layout);
        constraintLayout.setOnClickListener(v -> {
            if (onStudentItemClick != null) {
                onStudentItemClick.onItemClick(studentModel);
            }
        });

        deleteGroup.setOnClickListener(v -> {
            if (onStudentItemClick != null) {
                onStudentItemClick.onItemDelete(studentModel);
            }
        });

        updateGroup.setOnClickListener(v -> {
            if (onStudentItemClick != null){
                onStudentItemClick.onItemUpdate(studentModel);
            }
        });

        return view;
    }

    public interface OnStudentItemClick {
        void onItemClick(StudentModel model);

        void onItemDelete(StudentModel model);

        void onItemUpdate(StudentModel model);
    }

}
