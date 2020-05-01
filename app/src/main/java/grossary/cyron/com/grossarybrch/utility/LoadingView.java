package grossary.cyron.com.grossarybrch.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import grossary.cyron.com.grossarybrch.R;


public class LoadingView {

    private Context mcontext;
    private Dialog dialog;
    private ProgressBar load;
    private boolean cancalabe = true;

    public LoadingView(Context context) {

        this.mcontext = context;
        if (context != null) {
            dialog = new Dialog(mcontext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.custom_loading);
            load = dialog.findViewById(R.id.progress);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            //This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

        }
    }

    public void setCancalabe(boolean cancalabe) {

        this.cancalabe = cancalabe;
    }

    public void showLoading() {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
        if (load != null)
            load.setVisibility(View.VISIBLE);

        if (dialog != null)
            dialog.setCancelable(cancalabe);

    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        if (dialog != null) {
            if (dialog.isShowing()) { //check if dialog is showing.

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

                //if the Context used here was an activity AND it hasn't been finished or destroyed
                //then dismiss it
                if (context instanceof Activity) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                            dialog.dismiss();
                    }
                } else {//if the Context used wasnt an Activity, then dismiss it too
                    dialog.dismiss();
                }
            }
            dialog = null;
        }


        if (load != null)
            load.setVisibility(View.GONE);
    }
}
