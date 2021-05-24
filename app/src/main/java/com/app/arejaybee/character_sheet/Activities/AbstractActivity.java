/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AbstractActivity extends AppCompatActivity {
    public static void copyFile(File src, File dst, Context con) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //allows emails to have attached files
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void makeLongToast(String text) {
        Toast t = Toast.makeText(this, text, Toast.LENGTH_LONG);
        ((TextView) ((LinearLayout) t.getView()).getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
        t.show();
    }

    public void makeShortToast(String text) {
        Toast t = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        ((TextView) ((LinearLayout) t.getView()).getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
        t.show();
    }
}
