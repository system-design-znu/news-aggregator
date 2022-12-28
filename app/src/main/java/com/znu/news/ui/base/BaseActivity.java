package com.znu.news.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.znu.news.ui.sign.LoginActivity;
import com.znu.news.utils.SessionManager;

import javax.inject.Inject;

public abstract class BaseActivity<B extends ViewBinding> extends AppCompatActivity {

    @Inject
    public SessionManager sessionManager;

    protected B binding;

    protected abstract B initViewBinding();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = initViewBinding();
        setContentView(binding.getRoot());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                view.requestFocus();
                imm.showSoftInput(view, 0);
            }
        }
    }

    public Intent toActivity(Class<?> destination) {
        Intent intent = new Intent(this, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public void openLoginActivity() {
        startActivity(toActivity(LoginActivity.class));
        finish();
    }
}
