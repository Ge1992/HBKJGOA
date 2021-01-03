package com.example.hbkjgoa.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiana on 16-4-3.
 */
public class VideoFrameImageLoader {
	private static final String TAG = "VideoFrameImageLoader";
	/**
	 * 缓存image的类，当存储image的大小大于LruCache设定的值，系统自动释放内存
	 */
	private LruCache<String, Bitmap> mMemoryCache = null;
	private VideoFileManager mVideoFileManager;
	/**
	 * 获取视频流一帧图片的线程池
	 */
	private ExecutorService mImageThreadPool = null;

	/**
	 * 记录是否刚打开程序，用于解决进入程序不滚动不会获取图片的问题
	 */
	private boolean isFirstEnter = true;

	/**
	 * 一屏中第一个item的位置
	 */
	private int mFirstVisibleItem;

	/**
	 * 一屏中所有的item的个数
	 */
	private int mVisibleItemCount;

	public VideoFrameImageLoader(Context context) {
		// 获取系统分配给每个应用的最大内存，每个应用系统分配32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		Log.e(TAG, "maxMemory = " + maxMemory);
		// 给lruCache分配1/8 4M
		int mCacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {
			// 必须重写此方法，来测量Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
		mVideoFileManager = new VideoFileManager(context);
	}

	/**
	 * 显示当前屏幕的图片，先会去查找LruCache，LruCache没有就去手机目录查找，在没有就开启线程去下载截取帧图片
	 *
	 * @param firstVisibleItem
	 * @param visibleItemCount
	 */
	public void showImage(String videoUrl, final ImageView mImageView) {
		cutVideoFrameImage(videoUrl, new VideoFrameImageLoader.OnFrameImageLoaderListener() {
			@Override
			public void onImageLoader(Bitmap bitmap, String url) {
				if (mImageView != null && bitmap != null) {
					mImageView.setImageBitmap(bitmap);
				}
			}
		});
	}

	/**
	 * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
	 *
	 * @return
	 */
	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					mImageThreadPool = Executors.newFixedThreadPool(5);
				}
			}
		}
		return mImageThreadPool;
	}

	/**
	 * 添加Bitmap到内存缓存
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		Log.e(TAG, "key = " + key);
		if (getBitmapFormMemCache(key) == null && bitmap != null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从内存缓存中获取一个bitmap
	 *
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFormMemCache(String key) {

		return mMemoryCache.get(key);
	}

	/**
	 * 先从内存中获取Bitmap，如果没有就从本地获取
	 *
	 * @param url
	 * @param listener
	 * @return
	 */
	public Bitmap cutVideoFrameImage(final String url, final OnFrameImageLoaderListener listener) {

		final String subUrl = VideoFrameImageLoader.formatVideoUrl(url);
		Bitmap bitmap = showCacheBitmap(url);
		if (bitmap != null) {
			return bitmap;
		} else {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					listener.onImageLoader((Bitmap) msg.obj, url);
				}
			};
			getThreadPool().execute(new Runnable() {
				@Override
				public void run() {
					Bitmap bitmap = getBitmapFormUrl(url);
					if (bitmap == null) {
						return;
					}
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.sendMessage(msg);
					try {
						// 保存在手机本地存储
						mVideoFileManager.saveBitmap(subUrl, bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// 将bitmap加如内存缓存
					addBitmapToMemoryCache(subUrl, bitmap);
				}
			});
		}
		return null;
	}

	/**
	 * 获取Bitmap内存中没有就去本地存储获取
	 *
	 * @param url
	 * @return
	 */
	public Bitmap showCacheBitmap(String url) {
		if (getBitmapFormMemCache(url) != null) {
			return getBitmapFormMemCache(url);
		} else if (mVideoFileManager.isFileExists(url) && mVideoFileManager.getFileSize(url) != 0) {
			// 从本地获取bitmap
			Bitmap bitmap = mVideoFileManager.getBitmap(url);
			// 将bitmap加入内存缓存
			addBitmapToMemoryCache(url, bitmap);
			return bitmap;
		}
		return null;
	}

	/**
	 * 替换Url中非字母和非数字的字符，这里比较重要，因为我们用Url作为文件名，比如我们的Url
	 * 是Http://xiaanming/abc.jpg;用这个作为图片名称，系统会认为xiaanming为一个目录，
	 * 我们没有创建此目录保存文件就会报错
	 *
	 * @param videoUrl
	 * @return
	 */
	public static String formatVideoUrl(String videoUrl) {
		return videoUrl.replaceAll("[\\^\\W]", "") + ".jpeg";
	}

	/**
	 * 从.mp4的url视频中获取第一帧
	 *
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFormUrl(String url) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			if (Build.VERSION.SDK_INT >= 14) {
				retriever.setDataSource(url, new HashMap<String, String>());
			} else {
				retriever.setDataSource(url);
			}
			bitmap = retriever.getFrameAtTime();
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}
		return bitmap;
	}

	/**
	 * 取消正在下载的任务
	 */
	public synchronized void cancelTask() {
		if (mImageThreadPool != null) {
			mImageThreadPool.shutdownNow();
			mImageThreadPool = null;
		}
	}

	public VideoFileManager getVideoFileManager() {
		return mVideoFileManager;
	}

	public interface OnFrameImageLoaderListener {
		void onImageLoader(Bitmap bitmap, String url);
	}

}
