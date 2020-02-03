package com.haiyangrpdev.apptmasterdetail.utility;

import java.io.File;
import java.io.FileOutputStream;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;

public class ExtStorageHelper {

    public static void saveData(String folderName, String fileName, String dataJson, Context context) {
        try {
            File dataFile = createFilePath(folderName, fileName, context);
            Boolean hasSavedData = dataFile.length() > 0;

            if (!(dataFile == null || hasSavedData)) {
                FileOutputStream mOutput = new FileOutputStream(dataFile, true);
                mOutput.write(dataJson.getBytes());

                //Clear the stream buffers
                mOutput.flush();
                //Sync all data to the filesystem
                mOutput.getFD().sync();
                //Close the stream
                mOutput.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readData(String folderName, String fileName, Context context) {
        try {
            File dataFile = createFilePath(folderName, fileName, context);
            int length = (int)dataFile.length();

            if (dataFile != null) {
                FileInputStream mInput = new FileInputStream(dataFile);
                byte[] data = new byte[length];
                mInput.read(data);
                mInput.close();

                String display = new String(data);
                String textData = display.trim();
                return textData;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static File createFilePath(String folderName, String fileName, Context context) {
        //Create a new directory on external storage
        File rootPath = new File(Environment.getExternalStorageDirectory(), folderName);

        if (!rootPath.exists()) {
            if(rootPath.mkdirs()) {
                Log.d("ANDROID_TEST","Directory create success :"+ rootPath.getAbsolutePath());
            }
            else  {
                Log.d("ANDROID_TEST","FAILED TO CREATE DIRECTORY :"+ rootPath.getAbsolutePath());
            }
        }

        //Create the file reference
        File dataFile = new File(rootPath, fileName);

        //Check if external storage is usable
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showMessageAlert("Error", "Cannot use storage", context);
            return null;
        }
        else {
            Log.d("ANDROID_TEST"," Found external dir :" + dataFile.getAbsolutePath());
            return dataFile;
        }
    }

    private static void showMessageAlert(String title, String message, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
