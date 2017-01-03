package com.example.be.openglesdemo.Child;

import com.example.be.openglesdemo.BaseChild;
import com.example.be.openglesdemo.Dot;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Neb on 2016/12/26.
 * 墙体元素
 */

public class Wall extends BaseChild {
    //头部坐标点
    public Dot headDot;
    //末尾坐标点
    public Dot endDot;
    public boolean checked = false;
    public FloatBuffer quateBuffer;
    public float[] line;

    public Wall() {
        headDot = new Dot();
        endDot = new Dot();
        line = new float[]{
                headDot.x, headDot.y,
                endDot.x, endDot.y
        };
        quateBuffer = bufferUtil(line);
    }

    public Wall(Dot headDot, Dot endDot) {
        this.headDot = headDot;
        this.endDot = endDot;
        line = new float[]{
                this.headDot.x, this.headDot.y,
                this.endDot.x, this.endDot.y
        };
        quateBuffer = bufferUtil(line);
    }

    /**
     * 元素的具体绘制
     *
     * @param gl10
     */
    @Override
    public void draw(GL10 gl10) {
        super.draw(gl);
        if (gl == null) {
            gl = gl10;
        }
      /*  //未选中时不操作
 9       if (!checked){
            return;
        }*/
        // 选中状态
        if (checked) {
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        }
        // 根据两点绘制Dot和墙体
        if (endDot != null) {
            gl.glVertexPointer(2, GL10.GL_FLOAT, 0, quateBuffer);
            gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 2);
        }
        if (checked) {
            gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        }
        refresh();
    }

    /**
     * 刷新缓冲区中的数据
     */
    public void refresh() {
        line = new float[]{
                headDot.x, headDot.y,
                endDot.x, endDot.y
        };
        quateBuffer = bufferUtil(line);
    }

    public void setHeadDotXY(float x, float y) {
        headDot.x = x;
        headDot.y = y;
    }

    public void setEndDotXY(float x, float y) {
        endDot.x = x;
        endDot.y = y;
    }
}
