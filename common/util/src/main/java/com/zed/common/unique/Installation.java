package com.zed.common.unique;

import android.util.Log;
import com.zed.common.util.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 *
 * Date  : 2017-05-06
 *
 * Desc  :
 *
 * 应用程序安装数量标示，用来追踪应用的安装数量
 *
 * 该方式和设备唯一标识不一样，不同的应用程序会产生不同的ID，同一个程序重新安装也会不同。
 * 所以这不是设备的唯一ID，但是可以保证每个用户是不同的。
 * 可以说是用来标识每一份应用程序的唯一ID（Installation），可以用来跟踪应用的安装数量等
 */
public final class Installation {

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String getID() {
        if (sID == null) {
            File installation =
                new File(Utils.getApplicationContext().getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()) {
                    writeInstallationFile(installation);
                }
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Installation", "Method getID() occurs error: " + e.toString());
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
