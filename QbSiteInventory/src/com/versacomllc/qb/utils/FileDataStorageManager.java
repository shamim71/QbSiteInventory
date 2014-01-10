package com.versacomllc.qb.utils;

import static com.versacomllc.qb.utils.Constants.LOG_TAG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class FileDataStorageManager {

  public enum StorageFile {
    /**
     * File for Greeting store items
     */
    INVENTORY_ADJUSTMENT,
    INVENTORY_SITES,
    INVENTORY_ITEMS,
    USER_AUTHENTICATION
  }

  /**
   * Store file content specified by file name
   * 
   * @param context
   *          the Application context
   * @param name
   *          file name
   * @param content
   *          file content
   */
  public static void saveContentToFile(Context context, StorageFile name,
      String content) {

    FileOutputStream fos = null;
    try {
      if (isExternalStorageAvailable()) {
        File file = new File(context.getExternalFilesDir(null), name.toString());
        fos = new FileOutputStream(file);
      }
      else {
        fos = context.openFileOutput(name.toString(), Context.MODE_PRIVATE);
      }
      content = (content == null) ? "" : content;
      fos.write(content.getBytes());
    }
    catch (IOException e) {
      Log.e(LOG_TAG, e.getMessage(), e);
    }
    finally {
      if (fos != null) {
        try {
          fos.close();
          fos = null;
        }
        catch (IOException e) {
          Log.e(LOG_TAG, e.getMessage(), e);
        }
      }
    }
  }

  /**
   * Get file content specified by file name
   * 
   * @param context
   *          the Application context
   * @param name
   *          the file name
   * @return the file content
   */
  public static String getContentFromFile(Context context, StorageFile name) {

    FileInputStream fis = null;
    String content = null;
    try {
      if (isExternalStorageAvailable()) {
        File file = new File(context.getExternalFilesDir(null), name.toString());
        if (!file.exists())
          return null;
        fis = new FileInputStream(file);
      }
      else {
        fis = context.openFileInput(name.toString());
      }
      content = convertStreamToString(fis);
    }
    catch (IOException e) {
      Log.e(LOG_TAG, e.getMessage(), e);
    }
    finally {
      if (fis != null) {
        try {
          fis.close();
          fis = null;
        }
        catch (IOException e) {
          Log.e(LOG_TAG, e.getMessage(), e);
        }
      }
    }
    return content;

  }

  private static boolean isExternalStorageAvailable() {
    boolean isAvailable = false;

    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      // We can read and write the media
      isAvailable = true;
    }
    else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      Log.i(LOG_TAG, "External storage found but read only.");
    }
    else {
      Log.i(LOG_TAG, "External storage not ready to read/write.");
    }
    return isAvailable;
  }

  private static String convertStreamToString(FileInputStream is)
      throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuffer sb = new StringBuffer();
    String line = null;
    while ((line = reader.readLine()) != null) {
      sb.append(line).append("\n");
    }
    return sb.toString();
  }

}
