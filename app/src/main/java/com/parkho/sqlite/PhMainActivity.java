package com.parkho.sqlite;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.parkho.sqlite.database.PhStudentEntity;
import com.parkho.sqlite.database.PhStudentViewModel;

import java.util.ArrayList;
import java.util.List;

public class PhMainActivity extends AppCompatActivity {

    private PhStudentViewModel mViewModel;

    private List mItemList = new ArrayList<PhStudentEntity>();
    private PhRecyclerAdapter mRecyclerAdapter;
    private int currentCursorId = -1;

    private EditText mEtGrade;
    private EditText mEtNumber;
    private EditText mEtName;
    private EditText mEtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(PhStudentViewModel.class);
        mViewModel.getAllStudents().observe(this, a_studentEntityList -> {
            mItemList.clear();
            mItemList.addAll(a_studentEntityList);
            mRecyclerAdapter.notifyDataSetChanged();
        });

        // List 설정
        bindList();

        // Input 설정
        bindInput();

        // 삽입 설정
        bindInsert();

        // 갱신 설정
        bindUpdate();

        // 삭제 설정
        bindDelete();
    }

    /**
     * List 설정
     */
    private void bindList() {
        mRecyclerAdapter = new PhRecyclerAdapter(mItemList);
        mRecyclerAdapter.setOnItemClickListener((a_view, a_position) -> {
            PhStudentEntity studentEntity = (PhStudentEntity) mItemList.get(a_position);
            currentCursorId = studentEntity.getId();

            mEtGrade.setText(Integer.toString(studentEntity.getGrade()));
            mEtNumber.setText(Integer.toString(studentEntity.getNumber()));
            mEtName.setText(studentEntity.getName());
            mEtAge.setText(Integer.toString(studentEntity.getAge()));
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    /**
     * Input 설정
     */
    private void bindInput() {
        mEtGrade = findViewById(R.id.et_grade);
        mEtNumber = findViewById(R.id.et_number);
        mEtName = findViewById(R.id.et_name);
        mEtAge = findViewById(R.id.et_age);
    }

    /**
     * 삽입 설정
     */
    private void bindInsert() {
        findViewById(R.id.btn_insert).setOnClickListener(view -> {
            // DB 삽입
            final int grade = Integer.parseInt(mEtGrade.getText().toString());
            final int number = Integer.parseInt(mEtNumber.getText().toString());
            final String strName = mEtName.getText().toString();
            final int age = Integer.parseInt(mEtAge.getText().toString());
            insertData(grade, number, strName, age);

            // 입력값 초기화
            clearInputs();
        });
    }

    /**
     * 갱신 설정
     */
    private void bindUpdate() {
        findViewById(R.id.btn_update).setOnClickListener(view -> {
            if (currentCursorId == -1) {
                Toast.makeText(this, R.string.err_no_selected_item, Toast.LENGTH_SHORT).show();
                return;
            }

            // DB 갱신
            final int grade = Integer.parseInt(mEtGrade.getText().toString());
            final int number = Integer.parseInt(mEtNumber.getText().toString());
            final String strName = mEtName.getText().toString();
            final int age = Integer.parseInt(mEtAge.getText().toString());
            updateData(currentCursorId, grade, number, strName, age);

            // 입력값 초기화
            clearInputs();
        });
    }

    /**
     * 삭제 설정
     */
    private void bindDelete() {
        findViewById(R.id.btn_delete).setOnClickListener(view -> {
            if (currentCursorId == -1) {
                Toast.makeText(this, R.string.err_no_selected_item, Toast.LENGTH_SHORT).show();
                return;
            }

            // DB 삭제
            deleteData(currentCursorId);

            // 입력값 초기화
            clearInputs();
        });
    }

    /**
     * 입력값 초기화
     */
    private void clearInputs() {
        mEtGrade.getText().clear();
        mEtNumber.getText().clear();
        mEtName.getText().clear();
        mEtAge.getText().clear();

        currentCursorId = -1;
    }

    /**
     * DB 삽입
     */
    private void insertData(int a_grade, int a_number, String a_strName, int a_age) {
        PhStudentEntity studentEntity = new PhStudentEntity();
        studentEntity.setId(0);
        studentEntity.setGrade(a_grade);
        studentEntity.setNumber(a_number);
        studentEntity.setName(a_strName);
        studentEntity.setAge(a_age);

        // DB 삽입
        mViewModel.insert(studentEntity);
    }

    /**
     * DB 갱신
     */
    private void updateData(int a_id, int a_grade, int a_number, String a_strName, int a_age) {
        PhStudentEntity studentEntity = new PhStudentEntity();
        studentEntity.setId(a_id);
        studentEntity.setGrade(a_grade);
        studentEntity.setNumber(a_number);
        studentEntity.setName(a_strName);
        studentEntity.setAge(a_age);

        // DB 갱신
        mViewModel.insert(studentEntity);
    }

    /**
     * DB 삭제
     */
    private void deleteData(int a_id) {
        // DB 삭제
        mViewModel.deleteById(a_id);
    }
}