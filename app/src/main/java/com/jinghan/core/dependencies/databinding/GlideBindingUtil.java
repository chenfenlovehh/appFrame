package com.jinghan.core.dependencies.databinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.jinghan.core.dependencies.glide.transformation.CropCircleTransformation;
import com.jinghan.core.dependencies.glide.transformation.RoundedCornersTransformation;

/**
 * @author liuzeren
 * @time 2017/11/9    下午6:55
 * @mail lzr319@163.com
 */
public class GlideBindingUtil {
    /**默认加载方式*/
    public static final int GLIDE_IMAGE_DEFAULT = 0;

    /**
     * 圆形加载方式
     * 使用圆形加载方式需要提供边框颜色和边框宽度
     * borderWidth 默认为0
     * borderColor 默认为白色
     * */
    public static final int GLIDE_IMAGE_CROPCIRCLE = 1;

    /**
     * 图片圆角方式
     * 使用图片圆角的方式需要提供圆角半径大小
     * radius 圆角半径
     * */
    public static final int GLIDE_IMAGE_ROUNDEDCORNER = 2;

    /**
     * @param imageType 图片加载方式,默认为0
     * */
    public static void loadImage(ImageView imgView,
                                 String url,
                                 int imageType,
                                 int borderWidth,
                                 int borderColor,
                                 int radius,
                                 Drawable error,
                                 Drawable placeholder) {

        DrawableRequestBuilder builder = Glide.with(imgView.getContext())
                .load(url)
                .error(error)
                .placeholder(placeholder);

        switch (imageType) {
            case GLIDE_IMAGE_DEFAULT:
                break;
            case GLIDE_IMAGE_CROPCIRCLE:
                builder.bitmapTransform(new CropCircleTransformation(imgView.getContext(),borderWidth, borderColor));
                break;
            case GLIDE_IMAGE_ROUNDEDCORNER:
                builder.bitmapTransform(new RoundedCornersTransformation(imgView.getContext(),radius));
                break;
        }

        builder.into(imgView);
    }

    @BindingAdapter(value={"imageUrl"
            , "error"
            , "placeholder"
            , "radius"},requireAll = true)
    public static void loadImage(ImageView imgView,
                                 String url,
                                 int radius,
                                 Drawable error,
                                 Drawable placeholder) {
        loadImage(imgView,url,radius,0,0,radius,error,placeholder);
    }

    @BindingAdapter(value={"imageUrl"
            , "radius"},requireAll = true)
    public static void loadImage(ImageView imgView,
                                 String url,
                                 int radius) {
        loadImage(imgView,url,radius,0,0,radius,null,null);
    }

    @BindingAdapter(value={"imageUrl"
            , "error"
            , "placeholder"},requireAll = false)
    public static void loadImage(ImageView imgView,
                                 String url,
                                 Drawable error,
                                 Drawable placeholder) {

        loadImage(imgView,url,GLIDE_IMAGE_DEFAULT,0,0,0,error,placeholder);
    }

    @BindingAdapter(value={"imageUrl"
            , "borderWidth"
            , "borderColor"
            , "error"
            , "placeholder"
    },requireAll = true)
    public static void loadImage(ImageView imgView,
                                 String url,
                                 int borderWidth,
                                 int borderColor,
                                 Drawable error,
                                 Drawable placeholder) {
        loadImage(imgView,url,GLIDE_IMAGE_CROPCIRCLE,borderWidth,borderColor,0,error,placeholder);
    }

    @BindingAdapter(value={"res"})
    public static void loadImage(ImageView imgView,
                                 Drawable res) {
        imgView.setImageDrawable(res);
    }

    @BindingAdapter(value={"imageUrl"
            , "borderWidth"
            , "borderColor"},requireAll = true)
    public static void loadImage(ImageView imgView,
                                 String url,
                                 int borderWidth,
                                 int borderColor) {
        loadImage(imgView,url,GLIDE_IMAGE_CROPCIRCLE,borderWidth,borderColor,0,null,null);
    }

    /**
     * 类型不匹配的问题，比如R.color.white是int，但是通过
     * Data Binding赋值给android:background属性后，需要
     * 把int转换为ColorDrawable
     * */
    @BindingConversion
    public static Drawable convertColorToDrawable(int drawable) {
        return new ColorDrawable(drawable);
    }

    @BindingAdapter(value={"background"},requireAll = true)
    public static void loadImage(View view,
                                 int background) {
        view.setBackgroundResource(background);
    }
}