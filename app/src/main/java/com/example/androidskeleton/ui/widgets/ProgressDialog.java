package com.example.androidskeleton.ui.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.androidskeleton.R;

/**
 * Common progress .
 * this will use in all places for showing progress dialog
 */
public class ProgressDialog extends Dialog {


    public ProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_progress_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void dismiss() {
        try {
            if (isShowing())
                super.dismiss();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void show() {
        try {
            if (!isShowing())
                super.show();
        } catch (Exception ignored) {
        }
    }
}
