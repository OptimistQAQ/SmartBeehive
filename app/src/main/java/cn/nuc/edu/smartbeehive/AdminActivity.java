 package cn.nuc.edu.smartbeehive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class AdminActivity extends Dialog {

    public AdminActivity(@NonNull Context context) {
        super(context);
    }

    public AdminActivity(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AdminActivity(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
}