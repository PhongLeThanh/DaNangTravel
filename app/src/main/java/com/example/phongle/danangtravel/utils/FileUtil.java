package com.example.phongle.danangtravel.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileUtil {
    private static final int MAX_IMAGE_WIDTH = 1000;

    /**
     * @param context
     * @return
     * @throws IOException
     */
    public static File createTemplateImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        loader.reset();
        Cursor cursor = loader.loadInBackground();
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            return result;
        }
        return "";
    }

    public static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static File resizeImageFile(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        int origWidth = bitmap.getWidth();
        int origHeight = bitmap.getHeight();
        File fileOutput = null;
        if (origWidth > MAX_IMAGE_WIDTH) {
            int desHeight = origHeight / (origWidth / MAX_IMAGE_WIDTH);
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, MAX_IMAGE_WIDTH, desHeight, false);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            try {
                fileOutput = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "up.jpg");
                FileOutputStream fo = new FileOutputStream(fileOutput);
                fo.write(outStream.toByteArray());
                // remember close de FileOutput
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileOutput;
    }
}
