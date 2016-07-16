
package com.oddfeel.awesomedribbble.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.ui.MainActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    TextView textview_settings_language;
    Button button_settings_language;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        button_settings_language = (Button)findViewById(R.id.button_settings_language);
        button_settings_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingsActivity.this).setTitle(R.string.ChooseLanguage)
                        .setIcon(R.drawable.language)
                        .setSingleChoiceItems(R.array.Language, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0){
                                    switchLanguage("en");
                                }else if (which == 1){
                                    switchLanguage("zh");
                                }
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(SettingsActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//使得栈顶的activity全部移除，这样就使得所有的activity都切换语言
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    protected void switchLanguage(String language) {
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        switch (language)
        {
            case "en":
                config.locale = Locale.ENGLISH;
                resources.updateConfiguration(config, dm);
                break;
            case "zh":
                config.locale = Locale.SIMPLIFIED_CHINESE;
                resources.updateConfiguration(config, dm);
                break;
            default:
                config.locale = Locale.SIMPLIFIED_CHINESE;
                resources.updateConfiguration(config, dm);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(SettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//使得栈顶的activity全部移除，这样就使得所有的activity都切换语言
            startActivity(intent);
            //finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
