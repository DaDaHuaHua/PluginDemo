package example.com.plugindemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import example.com.pluginstandard.ActivityStandardInterface;

/**
 * Created by SongH on 2019/6/4.
 *
 *  占坑Activity
 */
public class ProxyActivity extends Activity {

    /**
     * 需要被加载的Activity类名
     */
    private String className;

    ActivityStandardInterface mStandardInterface;//所有的生命周期


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");

        //1.加载插件中的Activity
        try {
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});

            mStandardInterface = ((ActivityStandardInterface) instance);//创建Activity实例
            //2.传递Activity到插件
            mStandardInterface.attach(this);
            Bundle bundle = new Bundle();
            //3.调用跳转Activity的onCreate
            mStandardInterface.onCreate(bundle);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要跳转插件Activity时，改为跳转坑位Activity, 传递Activity类名
     *
     * 插件Activity里面要跳转插件Activity，也需要处理
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        String targetActivityName = intent.getStringExtra("className");
        Intent intent1 = new Intent(this,ProxyActivity.class);
        intent1.putExtra("className",targetActivityName);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
//        String serviceName = service.getStringExtra("serviceName");
//        Intent intent1 = new Intent(this, ProxyService.class);
//        intent1.putExtra("serviceName", serviceName);
//        return super.startService(intent1);
        return null; // FIXME: 2019/6/4 坑位Service没写
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getsInstance().getDexClassLoader();//将所有需要使用classLoader替换为宿主的DexClassLoader
    }

    @Override
    public Resources getResources() {
        return PluginManager.getsInstance().getResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStandardInterface.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStandardInterface.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mStandardInterface.onPause();
    }
}
