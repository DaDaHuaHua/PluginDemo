package example.com.pluginstandard;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by SongH on 2019/6/4.
 * 接口标准Activity
 * <p>
 * 1. 坑位Activity需要实现
 * 2. 插件里面所有的Activity都要实现
 */
public interface ActivityStandardInterface {

    public void attach(Activity proxyActivity);

    /**
     * 生命周期
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public boolean onTouchEvent(MotionEvent event);

    public void onBackPressed();
}
