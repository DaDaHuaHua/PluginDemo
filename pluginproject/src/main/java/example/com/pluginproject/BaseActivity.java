package example.com.pluginproject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import example.com.pluginstandard.ActivityStandardInterface;

/**
 * Created by SongH on 2019/6/5.
 */
public class BaseActivity extends Activity implements ActivityStandardInterface {

    protected Activity mActivity;

    @Override
    public void attach(Activity proxyActivity) {
        this.mActivity = proxyActivity;
    }


    @Override
    public void setContentView(View view) {
        if (mActivity != null) {
            mActivity.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mActivity != null) {
            mActivity.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intent = new Intent();
        intent.putExtra("serviceName", service.getComponent().getClassName());
        return mActivity.startService(intent);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (mActivity != null) return mActivity.findViewById(id);
        else return super.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        if (mActivity != null) {
            return mActivity.getIntent();
        } else {
            return super.getIntent();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (mActivity != null) return mActivity.getClassLoader();
        return super.getClassLoader();
    }


    @Override
    public void startActivity(Intent intent) {
        Intent intent1 = new Intent();
        intent1.putExtra("className", intent.getComponent().getClassName());
        mActivity.startActivity(intent1);
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if (mActivity != null) return mActivity.getLayoutInflater();
        return super.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if(mActivity!= null) return mActivity.getApplicationInfo();
        return super.getApplicationInfo();
    }


    @Override
    public Window getWindow() {
        if (mActivity != null) {
            return mActivity.getWindow();
        }
        return super.getWindow();
    }

    @Override
    public WindowManager getWindowManager() {
        if (mActivity != null) {
            return mActivity.getWindowManager();
        }
        return super.getWindowManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onBackPressed() {

    }
}
