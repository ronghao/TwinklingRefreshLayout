package com.lcodecore.tkrefreshlayout.utils;

import android.widget.TextView;
import com.lcodecore.tkrefreshlayout.R;

/**
 * 文字定时变化<br>
 * 示例：1-> 1. -> 1.. -> 1...
 *
 * @author haohao on 2017/5/25 17:22
 * @version v1.0
 */
public class TextAnimUtil {

    public static void showTextLoadingAnim(final TextView textView, final String str) {
        textView.setTag(R.id.tag_text_loading, true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (textView == null || textView.getHandler() == null) {
                    return;
                }
                String textStr = textView.getText().toString();
                int index = getEndSize(textStr);
                index++;
                if (index == 4) {
                    index = 0;
                }
                String tmpStr = str;
                for (int i = 0; i < index; i++) {
                    tmpStr += ".";
                }
                synchronized (this) {
                    if (textView == null || textView.getHandler() == null) {
                        return;
                    }
                    textView.setText(tmpStr);
                    textView.getHandler().postDelayed(this, 500);
                }
            }
        };

        textView.setTag(R.id.tag_text_loading_run, runnable);
        if (textView == null || textView.getHandler() == null) {
            return;
        }
        textView.getHandler().post(runnable);
    }

    private static int getEndSize(String str) {
        if (str.endsWith("...")) {
            return 3;
        } else if (str.endsWith("..")) {
            return 2;
        } else if (str.endsWith(".")) {
            return 1;
        }
        return 0;
    }

    public static void stopTextLoadingAnim(TextView textView) {
        if (textView == null || textView.getTag(R.id.tag_text_loading) == null) {
            return;
        }
        boolean is = (boolean) textView.getTag(R.id.tag_text_loading);
        if (is) {
            Runnable runnable = (Runnable) textView.getTag(R.id.tag_text_loading_run);
            synchronized (textView) {
                if (textView.getHandler() != null) textView.getHandler().removeCallbacks(runnable);
            }

            textView.setTag(R.id.tag_text_loading, false);
        }
    }
}
