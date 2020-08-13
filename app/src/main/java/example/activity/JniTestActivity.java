package example.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.ndk.JniCommon;
import com.ndk.JniTest;
import com.style.framework.R;

public class JniTestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        String tt = JniCommon.stringFromJNI();
        tv.setText(tt);
        String kk = tt;
        String obj = "obj";
        short s = 1;
        long l = 20;
        byte b = 127;
        JniTest.testShort(s);

        JniTest.testBasicDataType(s, 1, l, 1.0f, 10.5, 'A', false, b);
        JniTest.testJString("中国", obj, new MyClass(), new int[] {});
    }

    public static class MyClass {
    }
}
