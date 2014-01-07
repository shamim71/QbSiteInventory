package com.versacomllc.qb.spice;


public class DefaultProgressIndicatorState implements ProgressIndicatorState {

  private final boolean showProgress;
  private final boolean hideProgress;
  private final String message;

  public DefaultProgressIndicatorState(String message) {
    super();
    this.showProgress = (message == null) ? false : true;
    this.hideProgress = true;
    this.message = message;
  }

  public DefaultProgressIndicatorState(String message, boolean hideProgress) {
    super();
    this.showProgress = (message == null) ? false : true;
    this.hideProgress = hideProgress;
    this.message = message;
  }

  public DefaultProgressIndicatorState() {
    super();
    this.showProgress = false;
    this.hideProgress = true;
    this.message = null;
  }

  @Override
  public String getProgressMessage() {
    return message;
  }

  @Override
  public boolean showProgress() {
    return showProgress;
  }

  @Override
  public boolean hideProgressOnFinished() {
    return hideProgress;
  }

}
