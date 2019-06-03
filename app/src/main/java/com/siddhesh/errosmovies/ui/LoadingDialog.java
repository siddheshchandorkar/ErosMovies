package com.siddhesh.errosmovies.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import com.siddhesh.errosmovies.R;

public class LoadingDialog {
    private static LoadingDialog instances;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    private LoadingDialog() {
    }

    public static LoadingDialog with() {
        if (instances == null)
            instances = new LoadingDialog();
        return instances;
    }

    public void showLoadingDialog(Context context, String message) {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            if (context != null) {
                progressDialog = new ProgressDialog(context,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        }

    }

    public void dialogDismiss() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            try {
                if (progressDialog != null)
                    progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void showLoadingDialogFullScreen(Context context, String message) {
//        if (context != null) {
//           /* progressDialog = new ProgressDialog(context,
//                    R.style.full_screen_dialog);*/
//            progressDialog.setContentView(R.layout.show_dialogue_details);
//            progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.MATCH_PARENT);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//            //  progressDialog.setMessage(message);
//            progressDialog.show();
//        }
//    }

//    public void showLoadingDialogFullScreen2(Context context, String message) {
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.show_dialogue_details);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(false);
//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        window.setAttributes(wlp);
//        dialog.show();
//
//    }
//
//
//    public void dismissDialogue() {
//        if (dialog != null)
//            dialog.dismiss();
//    }
}

