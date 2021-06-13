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

public class GroupsAdapter extends BaseAdapter {

    private final ArrayList<GroupModel> models = new ArrayList<>();

    private OnItemClick onItemClick;

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setGroups(ArrayList<GroupModel> models) {
        this.models.clear();
        this.models.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public GroupModel getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position)  {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);

        ImageView shapeImg = view.findViewById(R.id.group_img);
        ImageView deleteGroup = view.findViewById(R.id.delete_group);
        ImageView updateGroup = view.findViewById(R.id.update_group);
        TextView groupName = view.findViewById(R.id.group_name);
        TextView groupLatter = view.findViewById(R.id.group_latter);

        GroupModel groupModel = getItem(position);
        groupName.setText(groupModel.getName());
        groupLatter.setText(groupModel.getName().toUpperCase().charAt(0) + "");

        int[] colors = {R.color.color1, R.color.color2, R.color.color3, R.color.color4,
                R.color.color5, R.color.color6, R.color.color7};

        Random random = new Random();

        shapeImg.setBackgroundResource(colors[random.nextInt(6)]);

        ConstraintLayout constraintLayout = view.findViewById(R.id.layout);
        constraintLayout.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onItemClick(groupModel);
            }
        });

        deleteGroup.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onItemDelete(groupModel);
            }
        });

        updateGroup.setOnClickListener(v -> {
            if (onItemClick != null){
                onItemClick.onItemUpdate(groupModel);
            }
        });

        return view;
    }

    public interface OnItemClick {
        void onItemClick(GroupModel model);

        void onItemDelete(GroupModel model);

        void onItemUpdate(GroupModel model);
    }
}
