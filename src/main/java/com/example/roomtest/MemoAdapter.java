package com.example.roomtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private List<Memo> memoList;
    private Activity context;
    private MemoDB memoDB;

    public MemoAdapter(Activity context,List<Memo> list){
        this.context = context;
        this.memoList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView memo_tv_no,memo_tv_nicname;
        ImageView memo_iv_edit,memo_iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            memo_tv_no = itemView.findViewById(R.id.memo_tv_no);
            memo_tv_nicname = itemView.findViewById(R.id.memo_tv_nicname);
            memo_iv_edit = itemView.findViewById(R.id.memo_iv_edit);
            memo_iv_delete = itemView.findViewById(R.id.memo_iv_delete);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoList.get(position);
        memoDB = MemoDB.getInstance(context);

        holder.memo_tv_no.setText(memo.no+"번");
        holder.memo_tv_nicname.setText(memo.nicName);

        holder.memo_iv_edit.setOnClickListener(v->{

            Memo editMemo = memoList.get(holder.getAdapterPosition());
            int no = editMemo.no;
            String text = editMemo.nicName;

            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_update);
            int w = WindowManager.LayoutParams.MATCH_PARENT;
            int h = WindowManager.LayoutParams.WRAP_CONTENT;

            dialog.getWindow().setLayout(w,h);
            dialog.show();

            EditText dialog_edt = dialog.findViewById(R.id.dialog_edt);
            Button dialog_update = dialog.findViewById(R.id.dialog_update);

            dialog_edt.setText(text);
            dialog_update.setOnClickListener(v1 -> {
                dialog.dismiss();
                String updateText = dialog_edt.getText().toString();
                memoDB.memoDao().update(no,updateText);
                memoList.clear();
                memoList.addAll(memoDB.memoDao().getAll());
                notifyDataSetChanged();
            });
        });
        holder.memo_iv_delete.setOnClickListener(v -> {
            Memo memo1 = memoList.get(holder.getAdapterPosition());
            memoDB.memoDao().delete(memo1);

            int pos = holder.getAdapterPosition();
            memoList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,memoList.size());
        });
    }
}
