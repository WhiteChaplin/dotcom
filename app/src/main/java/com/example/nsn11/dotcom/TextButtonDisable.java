package com.example.nsn11.dotcom;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;

import java.util.concurrent.Executor;

public class TextButtonDisable extends AppCompatActivity {
    Button btnTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_button_disable);

        btnTest = (Button)findViewById(R.id.btnTest);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTest.setClickable(false);
                btnTest.setText("비활성화");
            }
        });

        Task<DataSnapshot> task = new Task() {
            @Override
            public boolean isComplete() {
                return false;
            }

            @Override
            public boolean isSuccessful() {
                return false;
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Nullable
            @Override
            public Object getResult() {
                return null;
            }

            @Nullable
            @Override
            public Object getResult(@NonNull Class aClass) throws Throwable {
                return null;
            }

            @Nullable
            @Override
            public Exception getException() {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                return null;
            }
        };
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        btnTest.setClickable(false);
        btnTest.setText("수리가 진행 중 입니다");
    }

    @Override
    protected void onPause() {
        super.onPause();
        btnTest.setClickable(false);
        btnTest.setText("수리가 진행 중 입니다");
    }

    @Override
    protected void onStop() {
        super.onStop();
        btnTest.setClickable(false);
        btnTest.setText("수리가 진행 중 입니다");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnTest.setClickable(false);
        btnTest.setText("수리가 진행 중 입니다");
    }

}
