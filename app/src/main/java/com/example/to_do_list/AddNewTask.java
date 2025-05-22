package com.example.to_do_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_do_list.util.Database;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    private static final String TAG = "AddNewTask";

    private EditText mEditTask;
    private Button mSaveButton;

    private Database mydb;

    @Nullable
    @Override
   public View onCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstaceState){
        View v = inflater.inflate(R.layout.add_new_task, container,false);
        return v;
    }
    @Override
    public void OnViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mEditTask =view.findViewById(R.id.editText);
        mSaveButton = view.findViewById(R.id.addButton);

        mydb = new Database(getActivity());

        boolean isUpdate = false;

        Bundle bundle= getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("Task");
            mEditTask.setText(task);

            if(task.length()>0){
                mSaveButton.setEnabled(false);
            }
        }
    }
}
