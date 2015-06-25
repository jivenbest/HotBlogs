package cn.wilson.hotblogs;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.wilson.hotblogs.helper.AppCrashHelper;

/**
 * Created by KingFlyer on 2015/6/25.
 */
public class AppContext extends Application {

    private static AppContext sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;  // initialize the singleton
        initImageLoader(getApplicationContext());  //initialize images settings
        AppCrashHelper.getInstance().init(getApplicationContext());  //�������ص����ô�����־
    }

    //��������,���ұ�֤�̰߳�ȫ
    public static synchronized AppContext getInstance() {

        return sInstance;
    }

    //��ȡRequest����
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    //�����Tag��ʶ�Ķ���
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); //����18�볬ʱʱ�䣬���Դ���1��
        req.setTag(TextUtils.isEmpty(tag) ? "volleyData" : tag); // set the default tag if tag is empty
        getRequestQueue().add(req);
    }

    //ȡ��ָ����������
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    //ͼƬ�����
    private void initImageLoader(Context context) {
        //�����ļ���Ŀ¼
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "HotBlogs/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height���������ÿ�������ļ�����󳤿�
                .threadPoolSize(3) //�̳߳����̵߳�����
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //�������ʱ���URI������MD5 ����
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024) // �ڴ滺������ֵ
                .diskCacheSize(50 * 1024 * 1024)  // SD����������ֵ
                .tasksProcessingOrder(QueueProcessingType.LIFO) // ��ԭ�ȵ�discCache -> diskCache
                .diskCache(new UnlimitedDiskCache(cacheDir))//�Զ��建��·��
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)��ʱʱ
                // .writeDebugLogs()    // Remove for release app
                .build();

        //ȫ�ֳ�ʼ��������
        ImageLoader.getInstance().init(config);
    }

}
