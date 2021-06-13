package uz.murodjon_sattorov.usesql.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import uz.murodjon_sattorov.usesql.R;

public class GroupInsertDialog extends AlertDialog {

    private Button button;
    private EditText editText;

    private OnSaveClickListener onSaveClickListener;

    public void setOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    public GroupInsertDialog(@NonNull Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.insert_dialog, null, false);

        button = view.findViewById(R.id.save);
        editText = view.findViewById(R.id.name_insert);

        editText.setHint("Enter group name");

        button.setOnClickListener(v -> {

            String s = editText.getText().toString();

            if (s.length() != 0) {
                if (onSaveClickListener != null) {

                    onSaveClickListener.onSaveClick(s);

                }
                dismiss();
            }


        });


        setView(view);

    }

    public interface OnSaveClickListener {
        void onSaveClick(String s);
    }
}
