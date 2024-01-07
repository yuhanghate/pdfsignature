package com.example.pdf_signature.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class UuidUtils {
    private static final String TAG = "UuidUtils";
    private static final String UUID_FILE_NAME = "uuid.txt";
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1001;

    public static String getOrCreateUuid(Context context) {
        String uuid = readUuidFromSdCard();

        if (uuid == null) {
            uuid = generateUuid();
            saveUuidToSdCard(uuid);
        }

        return uuid;
    }

    private static String readUuidFromSdCard() {
        if (!isExternalStorageAvailable()) {
            return null;
        }

        File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(sdCard.getAbsolutePath() + "/" + UUID_FILE_NAME);

        if (!file.exists()) {
            return null;
        }

        StringBuilder uuidBuilder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                uuidBuilder.append(line);
            }

            br.close();
        } catch (IOException e) {
            Log.e(TAG, "Error reading UUID from SD card: " + e.getMessage());
            return null;
        }

        return uuidBuilder.toString();
    }

    private static void saveUuidToSdCard(String uuid) {
        if (!isExternalStorageAvailable() || !isExternalStorageWritable()) {
            return;
        }

        File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(sdCard.getAbsolutePath() + "/" + UUID_FILE_NAME);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            fos.write(uuid.getBytes());
            fos.flush();
            Log.d(TAG, "UUID saved to SD card: " + uuid);
        } catch (IOException e) {
            Log.e(TAG, "Error saving UUID to SD card: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    private static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}