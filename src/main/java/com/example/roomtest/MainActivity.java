package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Memo> memoList = new ArrayList<>();
    private MemoDB memoDB;
    private MemoAdapter memoAdapter;

    private Button insertBtn;
    private EditText editText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        insertBtn = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler_view);
        //DB생성
        memoDB = MemoDB.getInstance(this);
        memoList = memoDB.memoDao().getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        memoAdapter =  new MemoAdapter(MainActivity.this,memoList);
        recyclerView.setAdapter(memoAdapter);

        //main thread에서 DB 접근 불가 ==> data 잃고 쓸 때 thread 사용하기
//        memoList = MemoDB.getInstance(context).memoDao().getAll();
//        memoAdapter = new MemoAdapter(memoList);
//        memoAdapter.notifyDataSetChanged();

        insertBtn.setOnClickListener(v -> {
            String inputText =  editText.getText().toString();
            if(!inputText.equals("")){
                Memo memo = new Memo();
                memo.nicName = inputText;
                memoDB.memoDao().insert(memo);

                editText.setText("");

                memoList.clear();
                memoList.addAll(memoDB.memoDao().getAll());
                memoAdapter.notifyDataSetChanged();
            }
        });
    }
}