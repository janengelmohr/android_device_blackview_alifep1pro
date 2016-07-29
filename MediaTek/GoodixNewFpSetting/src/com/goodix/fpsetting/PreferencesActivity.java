package com.goodix.fpsetting;

import android.content.res.Configuration;
import com.goodix.util.Preferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CheckBox;
import android.provider.Settings;
import com.android.internal.widget.LockPatternUtils;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import com.goodix.application.FpApplication;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import com.goodix.service.FpDBContentProvider;

/* Vanzo:shangxiaopeng on: Sun, 30 Aug 2015 17:14:36 +0800
 * modify settings fun
public class PreferencesActivity extends Activity implements CompoundButton.OnCheckedChangeListener{
 */
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;


public class PreferencesActivity extends Activity implements CompoundButton.OnCheckedChangeListener,
       OnClickListener{
// End of Vanzo: shangxiaopeng
    public static final int CHECK_PASSWORD = 1;
    public static final int CHANGE_PASSWORD = 2;

    static final int MIN_PASSWORD_QUALITY = DevicePolicyManager.PASSWORD_QUALITY_SOMETHING;

    private static final int REQUEST_CODE_PASSWORD = 1;
    private static final int REQUEST_CODE_SERIALPORT = 2;
    private boolean bPasswordStatus = false;
    private Button mChangePswBtn = null;
    private ToggleButton mSettingPswBtn = null;
    private CheckBox mKeyGuardCheckBox, mCameraCheckBox, mMusicCheckBox;
/* Vanzo:shangxiaopeng on: Sun, 30 Aug 2015 17:15:10 +0800
 * modify settings fun
 */
    private RelativeLayout mKeyGuardLayout, mCameraLayout, mMusicLayout;
// End of Vanzo:shangxiaopeng
    private static final String KEY_FINGERPRINT_KEYGUARD = "vz_fingerprint_keyguard";
    private static final String KEY_FINGERPRINT_MUSIC = "vz_fingerprint_music";
    private static final String KEY_FINGERPRINT_CAMERA = "vz_fingerprint_camera";
    private LockPatternUtils mLockPatternUtils;

    private static AlertDialog sConfigureKeyGuardDialog = null;

    private Button mTitleBackBtn = null;
    private View mFpManagerPanel = null;
    private TextView mFpManagerText;
    private Button mAboutMe = null;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            setContentView(R.layout.activity_preferences);
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
            bPasswordStatus = Preferences.getPasswordIsBeUsed(this);
            loadView();
            updateScreen();
            startService();
            mLockPatternUtils = new LockPatternUtils(getApplicationContext());
        }

    @Override
        protected void onDestroy() {
            super.onDestroy();
        }

    private void startService() {

    }

    @Override
        protected void onResume() {
            // TODO Auto-generated method stub
            super.onResume();
            //  mKeyGuardCheckBox.setEnabled(isSecure());
            enableAllSettings(hasFinger());
        }

    @Override
        public void onActivityResult(int request_code, int resultcode, Intent data) {
            super.onActivityResult(request_code, resultcode, data);
            if (resultcode == RESULT_OK) {
                if (null != data && request_code == REQUEST_CODE_PASSWORD) {
                    boolean checkresult = data.getBooleanExtra(
                            PasswordActivity.CHECK_RETURN, false);
                    if (true == checkresult) {
                        this.bPasswordStatus = !Preferences
                            .getPasswordIsBeUsed(this);
                        Preferences.setPasswordIsBeUsed(this, this.bPasswordStatus);
                        updateScreen();
                    }
                }
                if (null != data && request_code == REQUEST_CODE_SERIALPORT) {
                    updateScreen();
                }
            }
        }

    @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        }
    private void loadView() {

        mChangePswBtn = (Button) findViewById(R.id.btn_change_psw);
        mChangePswBtn.setOnClickListener(new OnChangePswClickListener());
        mChangePswBtn.setEnabled(false);

        mFpManagerText = (TextView) findViewById(R.id.preferences_txt_mng);
        mFpManagerPanel = (View) findViewById(R.id.preferences_fpmanager_panel);
        mFpManagerPanel.setOnClickListener(new OnSetTouchIDClickListener());

        mTitleBackBtn = (Button) findViewById(R.id.title_back);
        mTitleBackBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                finish();
                }
                });

        mAboutMe = (Button) findViewById(R.id.aboutme);
        mAboutMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this,AboutActivity.class);
                startActivity(intent);

                }
                });
/* Vanzo:shangxiaopeng on: Sun, 30 Aug 2015 17:15:39 +0800
 * modify settings fun
 */
        mKeyGuardLayout = (RelativeLayout) findViewById(R.id.layout_keyguard);
        mCameraLayout = (RelativeLayout) findViewById(R.id.layout_camera);
        mMusicLayout = (RelativeLayout) findViewById(R.id.layout_music);
        mKeyGuardLayout.setOnClickListener(this);
        mCameraLayout.setOnClickListener(this);
        mMusicLayout.setOnClickListener(this);
// End of Vanzo:shangxiaopeng
        mKeyGuardCheckBox = (CheckBox) findViewById(R.id.switch_keyguard);
        mCameraCheckBox = (CheckBox) findViewById(R.id.switch_camera);
        mMusicCheckBox = (CheckBox) findViewById(R.id.switch_music);
        mKeyGuardCheckBox.setOnCheckedChangeListener(this);
        mCameraCheckBox.setOnCheckedChangeListener(this);
        mMusicCheckBox.setOnCheckedChangeListener(this);
        mKeyGuardCheckBox.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(!checkKeyGuardQuality() && mKeyGuardCheckBox.isChecked()){
                new ConfigureKeyGuardDialog();
                mKeyGuardCheckBox.setChecked(false);
                Settings.System.putInt(PreferencesActivity.this.getContentResolver(),KEY_FINGERPRINT_KEYGUARD, 0);
                }
                }
                });

    }

    private void updateScreen() {
        if (false == bPasswordStatus) {
            mChangePswBtn.setEnabled(false);
            mChangePswBtn.setTextColor(getResources().getColor(
                        R.color.gray_level_three));

            mFpManagerPanel.setClickable(false);
            mFpManagerText.setTextColor(getResources().getColor(
                        R.color.gray_level_three));
        } else {

            mChangePswBtn.setEnabled(true);
            mChangePswBtn.setTextColor(getResources().getColor( R.color.apple_blue));

            mFpManagerPanel.setClickable(true);
            mFpManagerText.setTextColor(getResources().getColor(
                        R.color.apple_blue));
        }

        mKeyGuardCheckBox.setChecked(isFunctionEnabled(KEY_FINGERPRINT_KEYGUARD));
        mCameraCheckBox.setChecked(isFunctionEnabled(KEY_FINGERPRINT_CAMERA));
        mMusicCheckBox.setChecked(isFunctionEnabled(KEY_FINGERPRINT_MUSIC));
    }
    private class OnChangePswClickListener implements View.OnClickListener {
        @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this,
                        PasswordActivity.class);
                intent.putExtra(PasswordActivity.START_TYPE_KEY,
                        PasswordActivity.CHANGE_PASSWORD);
                startActivityForResult(intent, 2);
            }
    }
    private class OnSetTouchIDClickListener implements View.OnClickListener {
        @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this,
                        TouchIDActivity.class);
                startActivity(intent);
            }
    }
    private boolean isFunctionEnabled(String key) {
        if (null == key) {
            return false;
        }
        return Settings.System.getInt(getContentResolver(), key, 0) == 1;
    }
    @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
            if (compoundButton == mKeyGuardCheckBox) {
                Settings.System.putInt(this.getContentResolver(),KEY_FINGERPRINT_KEYGUARD, check ? 1 : 0);

            } else if (compoundButton == mCameraCheckBox) {
                Settings.System.putInt(this.getContentResolver(),KEY_FINGERPRINT_CAMERA, check ? 1 : 0);

            } else if (compoundButton == mMusicCheckBox) {
                Settings.System.putInt(this.getContentResolver(),KEY_FINGERPRINT_MUSIC, check ? 1 : 0);
            }
        }
    private boolean isSecure() {
        if (mLockPatternUtils == null) {
            mLockPatternUtils = new LockPatternUtils(getApplicationContext());
        }
        return mLockPatternUtils.isSecure();
    }

    private boolean checkKeyGuardQuality() {
        int quality = new LockPatternUtils(this).getActivePasswordQuality();
        return (quality >= MIN_PASSWORD_QUALITY);
    }

    private class ConfigureKeyGuardDialog
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener
        {
            private boolean mConfigureConfirmed;

            private ConfigureKeyGuardDialog() {
                ///M:
                if (sConfigureKeyGuardDialog == null) {
                    AlertDialog dialog = new AlertDialog.Builder(PreferencesActivity.this)
                        .setTitle(R.string.set_keyguard_dialog_title)
                        .setMessage(R.string.set_keyguard_dialog_message)
                        .setPositiveButton(android.R.string.ok, this)
                        .setNegativeButton(android.R.string.cancel, this)
                        .create();
                    sConfigureKeyGuardDialog = dialog;
                    dialog.setOnDismissListener(this);
                    dialog.show();
                }

            }

            @Override public void onClick(DialogInterface dialog, int button) {
                mConfigureConfirmed = (button == DialogInterface.BUTTON_POSITIVE);
            }

            @Override
                public void onDismiss(DialogInterface dialog) {
                    sConfigureKeyGuardDialog = null;
                    if (mConfigureConfirmed) {
                        mConfigureConfirmed = false;
                        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
                        intent.putExtra("minimum_quality", DevicePolicyManager.PASSWORD_QUALITY_SOMETHING);
                        startActivity(intent);
                        return;
                    }
                    //  finish();
                }
        }
/* Vanzo:shangxiaopeng on: Sun, 30 Aug 2015 17:16:03 +0800
 * modify settings fun
 */
    public void onClick(View v) {
        if (!hasFinger()) {
            android.util.Log.i("zhangjingzhi", " no Finger Return");
            // Toast.makeText(getApplicationContext(),getString(R.string.set_finger_first), Toast.LENGTH_LONG).show();
            AlertDialog dialog = new AlertDialog.Builder(PreferencesActivity.this)
                .setTitle(R.string.set_finger_dialog_title)
                .setMessage(R.string.set_finger_dialog_msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(PreferencesActivity.this,
                            TouchIDActivity.class);
                        startActivity(intent);
                        }
                        })
            .setNegativeButton(android.R.string.cancel, null)
                .create();
            dialog.show();
            return;
        }
        switch (v.getId()) {
            case R.id.layout_keyguard:
                mKeyGuardCheckBox.performClick();
                break;

            case R.id.layout_camera:
                mCameraCheckBox.performClick();
                break;

            case R.id.layout_music:
                mMusicCheckBox.performClick();
                break;
        }
    }
// End of Vanzo:shangxiaopeng
/* Vanzo:zhangjingzhi on: Wed, 02 Sep 2015 16:50:05 +0800
 * SystemUI:cm systemui support
 */
    private boolean hasFinger() {
        return getFingerCount() > 0;
    }
    private void enableAllSettings(boolean enable) {

        mKeyGuardCheckBox.setEnabled(enable);
        mMusicCheckBox.setEnabled(enable);
        mCameraCheckBox.setEnabled(enable);
        if (! enable) {
            Settings.System.putInt(this.getContentResolver(),KEY_FINGERPRINT_KEYGUARD, 0);
            Settings.System.putInt(this.getContentResolver(),KEY_FINGERPRINT_MUSIC, 0);
            Settings.System.putInt(this.getContentResolver(),KEY_FINGERPRINT_CAMERA, 0);

            android.util.Log.i("zhangjingzhi", "no Finger Unclick All");
            mKeyGuardCheckBox.setChecked(false);
            mCameraCheckBox.setChecked(false);
            mMusicCheckBox.setChecked(false);
        }
    }
    private int getFingerCount() {
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(FpDBContentProvider.CONTENT_URI, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
// End of Vanzo:zhangjingzhi
}
