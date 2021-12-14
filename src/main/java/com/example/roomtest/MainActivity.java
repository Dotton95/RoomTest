package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Memo> memoList = new ArrayList<>();
    private MemoDB memoDB;
    private MemoAdapter memoAdapter;

    private Button main_btn_add;
    private EditText main_edt;
    private RecyclerView main_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_edt = findViewById(R.id.main_edt);
        main_btn_add = findViewById(R.id.main_btn_add);
        main_rv = findViewById(R.id.main_rv);

        memoDB = MemoDB.getInstance(this);
        memoList = memoDB.memoDao().getAll();

        main_rv.setLayoutManager(new LinearLayoutManager(this));
        memoAdapter =  new MemoAdapter(MainActivity.this,memoList);
        main_rv.setAdapter(memoAdapter);

        main_btn_add.setOnClickListener(v -> {
            String input =  main_edt.getText().toString();
            if(!input.equals("")){
                Memo memo = new Memo();
                memo.nicName = input;
                memoDB.memoDao().insert(memo);

                main_edt.setText("");

                memoList.clear();
                memoList.addAll(memoDB.memoDao().getAll());
                memoAdapter.notifyDataSetChanged();
            }
        });
    }
}