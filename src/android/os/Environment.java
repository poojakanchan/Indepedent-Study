package android.os;

import java.io.*;

public class Environment {

  public static String MEDIA_MOUNTED ="1";
  
  public static String getExternalStorageState() {
  return MEDIA_MOUNTED;
  }

  public static File getExternalStorageDirectory(){
   File file = new File("card");
    return file;

  }

}

