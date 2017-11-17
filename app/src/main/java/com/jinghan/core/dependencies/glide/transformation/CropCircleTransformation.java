package com.jinghan.core.dependencies.glide.transformation;

/**
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class CropCircleTransformation implements Transformation<Bitmap> {

  private static final int DEFAULT_BORDER_WIDTH = 10;
  private static final int DEFAULT_BORDER_COLOR = Color.parseColor("#ffffff");

  private int mBorderWidth = 0;
  private int mBorderColor = 0;

  private BitmapPool mBitmapPool;

  public CropCircleTransformation(Context context) {
    this(Glide.get(context).getBitmapPool());
  }

  public CropCircleTransformation(Context context, int borderWidth) {
    this(context);

    this.mBorderWidth = borderWidth;
    this.mBorderColor = DEFAULT_BORDER_COLOR;
  }

  public CropCircleTransformation(Context context, int borderWidth, int borderColor) {
    this(context);

    this.mBorderWidth = borderWidth;
    this.mBorderColor = borderColor;
  }

  public CropCircleTransformation(BitmapPool pool) {
    this.mBitmapPool = pool;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();
    int size = Math.min(source.getWidth(), source.getHeight());

    int width = (source.getWidth() - size) / 2;
    int height = (source.getHeight() - size) / 2;

    Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    BitmapShader shader =
        new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    if (width != 0 || height != 0) {
      // source isn't square, move viewport to center
      Matrix matrix = new Matrix();
      matrix.setTranslate(-width, -height);
      shader.setLocalMatrix(matrix);
    }
    paint.setColor(Color.WHITE);
    paint.setShader(shader);
    paint.setAntiAlias(true);

    float r = size / 2f;

    if (mBorderWidth > 0) {
      Paint bgPaint = new Paint();
      bgPaint.setColor(mBorderColor);
      bgPaint.setAntiAlias(true);
      canvas.drawCircle(r , r , r , bgPaint);
    }
    canvas.drawCircle(r, r, r-mBorderWidth, paint);
//    canvas.drawCircle(r, r, r, paint);

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }

  @Override
  public String getId() {
    return "CropCircleTransformation()";
  }
}
