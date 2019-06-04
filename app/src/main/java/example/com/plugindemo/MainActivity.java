package example.com.plugindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });

        findViewById(R.id.bt_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
            }
        });

    }

    /**
     * 1. 加载插件apk
     *
     */
    private void loadPlugin() {
        //1. 创建插件保存路径
        File filesDir = this.getDir("plugin", Context.MODE_PRIVATE);
        String name = "plugin.apk";
        String filePath = new File(filesDir,name).getAbsolutePath();
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }

        //2.复制文件到插件保存路径
        InputStream is = null;
        FileOutputStream fos = null;
        Log.i(TAG, "加载插件 " + new File(Environment.getExternalStorageDirectory(), name).getAbsolutePath());
        try {
             is = new FileInputStream(new File(Environment.getExternalStorageDirectory(),name));
            fos = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len= is.read(buffer)) != -1){
                fos.write(buffer,0,len);
            }
            File f = new File(filePath);
            if(f.exists()){
                Toast.makeText(this,"dex 已经复制完成！！！",Toast.LENGTH_SHORT).show();
            }

            //3.加载插件
            PluginManager.getsInstance().loadPath(this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void jump() {
        Intent intent = new Intent(this,ProxyActivity.class);
        intent.putExtra("className",PluginManager.getsInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }

}
