package com.ceshi.ha.utils.file;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class MiscUtils {
    private final static String TAG = MiscUtils.class.getSimpleName();
    public static final String APP_FOLDER_ON_SD =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/TianXiang/TianXiang";


    public static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.

        // Logger.d(TAG, "checkFsWritable directoryName ==   "
        // + PathCommonDefines.APP_FOLDER_ON_SD);

        File directory = new File(APP_FOLDER_ON_SD);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        File f = new File(APP_FOLDER_ON_SD, ".probe");
        try {
            // Remove stale file if any
            if (f.exists()) {
                f.delete();
            }
            if (!f.createNewFile()) {
                return false;
            }
            f.delete();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


    public static boolean hasStorage() {
        boolean hasStorage = false;
        String str = Environment.getExternalStorageState();

        if (str.equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            hasStorage = checkFsWritable();
        }

        return hasStorage;
    }


    /**
     * @param dir      目标文件
     * @param fileName 文件名
     */
    public static void updateFileTime(String dir, String fileName) {

        File file = new File(dir, fileName);
        long newModifiedTime = System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
    }



    /**
     * SDCard
     *
     * @return SDCard
     */
    public boolean isSDCardSizeOverflow() {

        boolean result = false;

        String sDcString = Environment.getExternalStorageState();

        if (sDcString.equals(Environment.MEDIA_MOUNTED)) {

            File pathFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(pathFile.getPath());

            long nTotalBlocks = statfs.getBlockCount();

            long nBlocSize = statfs.getBlockSize();

            long nAvailaBlock = statfs.getAvailableBlocks();

            long nFreeBlock = statfs.getFreeBlocks();

            long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

            long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
            if (nSDFreeSize <= 1) {
                result = true;
            }
        }// end of if
        // end of func
        return result;
    }
}
