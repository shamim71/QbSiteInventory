package com.versacomllc.qb.utils;

import com.versacomllc.qb.R;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;



public class CustomProgressOverlay extends Dialog {

  private static final int NO_DIM = 0;

  public CustomProgressOverlay(Context context) {
    super(context, R.style.PlatformDialogStyle);
  }

  public static CustomProgressOverlay show(Context context,
      boolean showDefaultMessage) {
    return show(context,
        showDefaultMessage ? context.getString(R.string.processing) : null);
  }

  public static CustomProgressOverlay show(Context context, String message) {
	  CustomProgressOverlay overlay = new CustomProgressOverlay(context);
    overlay.requestWindowFeature(Window.FEATURE_NO_TITLE);
    overlay.setContentView(R.layout.progress_overlay);
    Window overlayWindow = overlay.getWindow();

    overlay.show();

    updateProgressMessage(overlay, message);

    overlayWindow.setBackgroundDrawableResource(R.color.translucent_white);
    overlayWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
      disableDim(overlayWindow);

    return overlay;
  }

  @TargetApi(14)
  private static void disableDim(Window overlayWindow) {
    overlayWindow.setDimAmount(NO_DIM);
  }


  public static CustomProgressOverlay updateProgressMessage(
		  CustomProgressOverlay overlay, String message) {

    if (!overlay.isShowing()) {
      overlay.show();
    }
    TextView messageView = (TextView) overlay
        .findViewById(R.id.global_progress_message);
    if (message != null) {
      messageView.setVisibility(View.VISIBLE);
      messageView.setText(message);
    }
    else {
      messageView.setVisibility(View.GONE);
    }
    return overlay;
  }
}
