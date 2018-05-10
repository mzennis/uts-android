package id.mzennis.m_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by meta on 10/05/18.
 */

public class AlarmSettingActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AlarmSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Alarm Setting");


    }
}
