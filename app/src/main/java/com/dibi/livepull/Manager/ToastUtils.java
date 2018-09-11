package com.dibi.livepull.Manager;


import android.content.Context;
import android.widget.Toast;

import com.dibi.livepull.GlobalAppliaction;


/*
 * @创建者     Administrator
 * @创建时间   2015/8/7 10:49
 * @描述	      ${TODO}
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class ToastUtils {
    /**
     * 可以在子线程中弹出toast
     *
     * @param context
     * @param text
     */
    public static void showToastSafe(final Context context, final String text) {
        ThreadUtils.runInUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast( final String text) {
        Toast.makeText(GlobalAppliaction.getCurrentContext(), text, Toast.LENGTH_SHORT).show();
    }
}
