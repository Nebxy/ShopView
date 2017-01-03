package com.example.be.openglesdemo;

import android.graphics.Canvas;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Neb on 2016/12/26.
 * 绘制的接口，所有子元素都要实现它并完成具体绘制
 */
public interface Draw {
    void draw(GL10 gl10);
}
