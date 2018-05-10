package id.mzennis.m_health;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by meta on 10/05/18.
 */

public class AlarmSettingActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AlarmSettingActivity.class);
        context.startActivity(intent);
    }


    private Camera camera;
    private boolean isFlashOn;
    private Camera.Parameters params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Alarm Setting");

        final Switch torch = findViewById(R.id.switch_e);
        torch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isHasFlash()) {
                    if (!isChecked) {
                        turnOnFlash();
                    } else {
                        turnOffFlash();
                    }
                } else {
                    AlertDialog alert = new AlertDialog.Builder(AlarmSettingActivity.this)
                            .create();
                    alert.setTitle("Maaf");
                    alert.setMessage("Smartphone Anda tidak menduduk senter.");
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AlarmSettingActivity.this.finish();
                            System.exit(0);
                        }
                    });
                    alert.show();
                }

            }
        });


        // get the camera
        getCamera();
    }

    /*
	 * Get the camera
	 */
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Error Failed to Open", e.getMessage());
            }
        }
    }


    public boolean isHasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /*
     * Turning Off flash
     */
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    /*
     * Playing sound will play button toggle sound on flash on / off
     */
    private void playSound() {
        MediaPlayer mp;
        if (isFlashOn) {
            mp = MediaPlayer.create(this, R.raw.light_switch_off);
        } else {
            mp = MediaPlayer.create(this, R.raw.light_switch_on);
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }

    /*
     * Turning On flash
     */
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    public void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
