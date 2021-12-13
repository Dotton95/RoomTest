package com.example.roomtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private List<Memo> memoList;
    private Activity context;
    private MemoDB database;

    public MemoAdapter(Activity context,List<Memo> list){
        this.context = context;
        this.memoList = list;
        notifyDataSetChanged();
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
        database = MemoDB.getInstance(context);
        holder.tv_memo_no.setText(memo.no+"번 유저");
        holder.tv_memo_nicname.setText(memo.nicName);
        holder.btEdit.setOnClickListener(v->{
            Memo editMemo = memoList.get(holder.getAdapterPosition());
            int no = editMemo.no;
            String text = editMemo.nicName;

            Dialog dialog = new Dialog(context);
            dialog.show();

            EditText editText = dialog.findViewById(R.id.dialog_edit_text);
            Button bt_update = dialog.findViewById(R.id.bt_update);

            editText.setText(text);
            bt_update.setOnClickListener(v1 -> {
                dialog.dismiss();
                String updateText = editText.getText().toString();
                database.memoDao().update(no,updateText);
                memoList.clear();
                memoList.addAll(database.memoDao().getAll());
                notifyDataSetChanged();
            });
        });
        holder.btDelete.setOnClickListener(v -> {
            Memo memo1 = memoList.get(holder.getAdapterPosition());
            database.memoDao().delete(memo1);

            int pos = holder.getAdapterPosition();
            memoList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,memoList.size());

        });
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_memo_no,tv_memo_nicname;
        ImageView btEdit,btDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_memo_no = itemView.findViewById(R.id.tv_no);
            tv_memo_nicname = itemView.findViewById(R.id.tv_nicname);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }

}
