package com.niwj.graduationproject.control;

import android.content.Context;
import android.widget.ImageView;

import com.niwj.graduationproject.R;
import com.niwj.graduationproject.imageselector.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by prince70 on 2017/8/22.
 */

public class ImageUniversalLoader implements ImageLoader {

    private static ImageUniversalLoader imageUniversalLoader;

    public static ImageUniversalLoader getInstance() {
        if (imageUniversalLoader == null) {
            imageUniversalLoader = new ImageUniversalLoader();
        }
        return imageUniversalLoader;
    }

    /**
     * 从本地path加载图片资源
     *
     * @param context
     * @param path
     * @param imageView
     */
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        File file = new File(path);
        Picasso.with(context)
                .load(file)
                .transform(new CropSquareTransformation())
                .error(R.mipmap.app_logo)
                .into(imageView);

    }

    /**
     * 从网络url加载图片资源
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void displayImageWeb(Context context, String url, ImageView imageView) {

        Picasso.with(context)
                .load(url).error(R.mipmap.app_logo)
                .into(imageView);
    }
}
