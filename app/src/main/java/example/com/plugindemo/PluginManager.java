package example.com.plugindemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by SongH on 2019/6/4.
 *  插件Manager
 */
public class PluginManager {

    private Context mContext;
    private static final PluginManager sInstance = new PluginManager();
    private PackageInfo mPackageInfo;
    private Resources mResources;
    private DexClassLoader mDexClassLoader;

    public static PluginManager getsInstance() {
        return sInstance;
    }

    private PluginManager (){

    }

    public void setContext(Context context){
        this.mContext = context;
    }

    /**
     * 1. 创建packageInfo,方便获取Activity
     * 2. 创建ClassLoader ，方便加载Activity
     * 3. 获得resource对象，方便加载资源
     * @param context
     */
    public void loadPath(Context context){
        File filesDir = context.getDir("plugin",Context.MODE_PRIVATE);
        String name = "plugin.apk";
        String path = new File(filesDir,name).getAbsolutePath();

        PackageManager packageManager = context.getPackageManager();
        mPackageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);


        File dexOutFile = context.getDir("dex",Context.MODE_PRIVATE);
        mDexClassLoader = new DexClassLoader(path,dexOutFile.getAbsolutePath()
                ,null,context.getClassLoader());

        try {
            AssetManager assetManager   = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath",String.class);
            addAssetPath.invoke(assetManager,path);
            mResources = new Resources(assetManager,context.getResources().getDisplayMetrics()
                    ,context.getResources().getConfiguration());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public Resources getResources() {
        return mResources;
    }

    public DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }
}
