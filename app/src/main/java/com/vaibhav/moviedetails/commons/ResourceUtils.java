package com.vaibhav.moviedetails.commons;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import java.lang.ref.WeakReference;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class ResourceUtils {

    private static WeakReference<Context> contextWeakReference;

    public static void initialize(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    private static Context getContext() {
        return contextWeakReference.get();
    }

    @NonNull
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    @NonNull
    public static String getString(int id, Object... formatArgs) {
        return getContext().getResources().getString(id, formatArgs);
    }

    public static int getColor(int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    public static Drawable getDrawable(int drawable) {
        try {
            Drawable mDrawable = ContextCompat.getDrawable(getContext(), drawable);
            return mDrawable;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    public static float getDimen(int dimen) {
        return getContext().getResources().getDimension(dimen);
    }

    public static String getResourceEntryName(int id) {
        return getContext().getResources().getResourceEntryName(id);
    }

    public static Typeface getFont(int id) {
        return ResourcesCompat.getFont(getContext(), id);
    }
}
