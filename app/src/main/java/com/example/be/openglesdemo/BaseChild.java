package com.example.be.openglesdemo;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Neb on 2016/12/26.
 * 元素的基类
 */

public class BaseChild implements Draw {
    protected GL10 gl;

    public BaseChild() {

    }

    @Override
    public void draw(GL10 gl) {

    }

    public  IntBuffer bufferUtil(int[] arr) {
        IntBuffer buffer;

        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());

        buffer = qbb.asIntBuffer();
        buffer.put(arr);
        buffer.position(0);

        return buffer;
    }

    public  FloatBuffer bufferUtil(float[] arr) {
        FloatBuffer buffer;

        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());

        buffer = qbb.asFloatBuffer();
        buffer.put(arr);
        buffer.position(0);

        return buffer;
    }

}
