package example.com.pluginproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity,"跳转插件里面的Activity成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mActivity,SecondActivity.class));
            }
        });
    }


}
