package com.wtechweb.v2_update;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class StudentAdapter extends FirebaseRecyclerAdapter<Student, StudentAdapter.StudentViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public StudentAdapter(@NonNull FirebaseRecyclerOptions<Student> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentViewHolder holder, final int position, @NonNull final Student model) {
        holder.tvId.setText(model.getId());
        holder.tvName.setText(model.getName());
        holder.tvCell.setText(model.getCell());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.updatedialog))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                    dialog.show();
                View v  = dialog.getHolderView();
                Button btnUpdate;
                final EditText etName, etCell, etId;
                etName = v.findViewById(R.id.etName);
                etCell = v.findViewById(R.id.etCell);
                etId = v.findViewById(R.id.etId);
                btnUpdate = v.findViewById(R.id.btnUpdate);

                etName.setText(model.getName());
                etId.setText(model.getId());
                etCell.setText(model.getCell());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("id", etId.getText().toString().trim());
                        updatedData.put("name", etName.getText().toString().trim());
                        updatedData.put("cell", etCell.getText().toString().trim());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Students")
                                .child(getRef(position).getKey())
                                .updateChildren(updatedData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Data has been updated", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.studentitem, parent, false);
        return new StudentViewHolder(view);
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvCell, tvId;
        ImageView ivEdit, ivDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvId = itemView.findViewById(R.id.tvId);
            tvCell = itemView.findViewById(R.id.tvCell);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
