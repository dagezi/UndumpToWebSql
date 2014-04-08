package com.example.sqliteinjection.app;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Just Util :)
 */
public class Util {
    static void extractAsset(Context context, String src, File dest) {
        try {
            File parentFile = dest.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            InputStream inputStream = context.getAssets().open(src);
            int size = inputStream.available();
            ReadableByteChannel input = Channels.newChannel(inputStream);
            FileChannel output = new FileOutputStream(dest).getChannel();
            output.transferFrom(input, 0, size);

            output.close();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
