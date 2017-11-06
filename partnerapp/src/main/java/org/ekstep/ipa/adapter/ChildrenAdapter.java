package org.ekstep.ipa.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.ekstep.ipa.R;
import org.ekstep.ipa.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vinayagasundar
 */

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildViewHolder> {

    private List<Student> mStudentList;

    private OnSelectedChildListener mListener;

    private List<String> mSelectedChildIds = new ArrayList<>();

    private boolean mIsMultiSelect = false;

    public ChildrenAdapter(List<Student> studentList) {
        mStudentList = studentList;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_genie_child_item, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChildViewHolder holder, int position) {
        final Student student = mStudentList.get(position);
        holder.bind(student);

        if (isStudentSelected(student.getStudentId())) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStudentSelected(student.getStudentId())) {
                    mSelectedChildIds.remove(student.getStudentId());

                    if (mListener != null) {
                        mListener.onChildUnSelected(student);
                    }
                } else {

                    if (mIsMultiSelect) {
                        mSelectedChildIds.add(student.getStudentId());
                    } else {
                        if (mSelectedChildIds.size() > 0) {
                            mSelectedChildIds.set(0, student.getStudentId());
                        } else {
                            mSelectedChildIds.add(student.getStudentId());
                        }
                    }

                    if (mListener != null) {
                        mListener.onChildSelected(student);
                    }
                }

                if (mIsMultiSelect) {
                    notifyItemChanged(holder.getAdapterPosition());
                } else {
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mStudentList != null ? mStudentList.size() : 0);
    }

    public void setListener(OnSelectedChildListener listener) {
        mListener = listener;
    }

    public void enableMultiSelect(boolean isMultiSelect) {
        mIsMultiSelect = isMultiSelect;
    }

    public void updateStudentList(List<Student> studentList) {
        mStudentList.clear();
        mSelectedChildIds.clear();
        mStudentList.addAll(studentList);

        notifyDataSetChanged();
    }

    private boolean isStudentSelected(String studentId) {
        return (mSelectedChildIds.size() > 0 && mSelectedChildIds.contains(studentId));
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTxt;
        TextView mClassTxt;
        TextView mIdTxt;


        ChildViewHolder(View itemView) {
            super(itemView);

            mNameTxt = (TextView) itemView.findViewById(R.id.txt_name);
            mClassTxt = (TextView) itemView.findViewById(R.id.txt_class);
            mIdTxt = (TextView) itemView.findViewById(R.id.txt_child_id);
        }

        public void bind(Student student) {
            mNameTxt.setText("Name : " + student.getName());
            mClassTxt.setText("Class : " + student.getStudentClass());
            mIdTxt.setText("ID : " + student.getStudentId());
        }
    }

    public interface OnSelectedChildListener {
        void onChildSelected(Student student);

        void onChildUnSelected(Student student);
    }
}
