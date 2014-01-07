package com.versacomllc.qb.spice;


public interface ProgressIndicatorState {

  String getProgressMessage();

  boolean showProgress();

  boolean hideProgressOnFinished();

}
