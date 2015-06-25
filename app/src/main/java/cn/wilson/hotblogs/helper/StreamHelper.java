package cn.wilson.hotblogs.helper;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by KingFlyer on 2015/1/22.
 */
public class StreamHelper {

    /**
     * 转换为圆角矩形
     *
     * @param bitmap
     * @param corner
     * @return
     */
    public static Bitmap roundCorner(Bitmap bitmap, int corner) {
        Bitmap bitmapTemp =
                Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapTemp);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = corner;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapTemp;
    }

    public static String InputStream2String(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    public static void SaveBitmap2File(Bitmap bitmap, String fileName) {
        CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream("/sdcard/Cnblogs/image/avatar/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bitmap.compress(format, quality, outputStream);
    }

    public static void SaveBitmap2Cache(Bitmap bitmap){

    }

    public static Bitmap resizeImage(Bitmap bitmap,double scale){
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        /* 设置图片放大的比例 */
        float scaleImg = (float)scale;
        /* 产生reSize后的Bitmap对象 */
        Matrix matrix = new Matrix();
        matrix.postScale(scaleImg, scaleImg);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bmpWidth,bmpHeight,matrix,true);

        return resizeBmp;
    }

    public static Bitmap resizeFixImage(Bitmap bitmap,int xwidth,int xheight){
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        /* 产生reSize后的Bitmap对象 */
        Matrix matrix = new Matrix();
        matrix.postScale(xwidth, xheight);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bmpWidth,bmpHeight,matrix,true);

        return resizeBmp;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

