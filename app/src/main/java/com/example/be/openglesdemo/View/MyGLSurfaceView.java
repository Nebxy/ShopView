package com.example.be.openglesdemo.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.renderscript.Matrix2f;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.be.openglesdemo.BaseChild;
import com.example.be.openglesdemo.Child.Wall;
import com.example.be.openglesdemo.Dot;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Be on 2016/12/29.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private int width = 600;
    private int height = 1024;
    private ArrayList<BaseChild> childList;
    private boolean isInitial = true;
    private boolean isFirstWall = true;
    //保存所有点的集合
    private ArrayList<Dot> dots;
    //编辑模式时首个点的位置
    private Dot firstDot;
    private BaseChild checkedChild;
    private Paint mPaint;

    public ArrayList<Dot> getDots() {
        return dots;
    }

    public ArrayList<BaseChild> getChildList() {
        return childList;
    }

    public MyGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    /**
     * 初始化操作
     */
    private void init() {
        /**
         *  绑定渲染器
         */
        this.setRenderer(new GlRenderer());
    }

    boolean moving = false;

    float preX;
    float preY;

    /**
     * 手势交互控制绘图
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1;
        float y1;
        switch (event.getAction()) {
            //按下时判断选中哪个元素或者添加墙体
            case MotionEvent.ACTION_DOWN:
                x1 = (event.getX() * 2 / width) - 1;
                y1 = (((event.getY()) * -3 / height) + 1.5f);
                if (isInitial) {
                    //添加墙体元素，直到多边形头尾缝合
                    drawWall(x1, y1);
                } else { //否则判断选择哪个元素
                    ((Wall) childList.get(0)).checked = true;
                }
                moving = true;
                preX = (event.getX() * 2 / width) - 1;
                preY = (((event.getY()) * -3 / height) + 1.5f);

                break;
            //元素的拖拉或者旋转处理
            case MotionEvent.ACTION_MOVE:
                float dx = ((event.getX() * 2 / width) - 1) - preX;
                float dy = (((event.getY()) * -3 / height) + 1.5f) - preY;
                preX = (event.getX() * 2 / width) - 1;
                preY = (((event.getY()) * -3 / height) + 1.5f);
                for (BaseChild child : childList) {
                    if (((Wall) child).checked) {
                        //根据偏移量拖拉选中元素
                        ((Wall) child).setHeadDotXY(((Wall) child).headDot.x + dx, ((Wall) child).headDot.y + dy);
                        ((Wall) child).setEndDotXY(((Wall) child).endDot.x + dx, ((Wall) child).endDot.y + dy);

                    }
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }



    /**
     * 渲染器类，绘制的入口
     */
    class GlRenderer implements GLSurfaceView.Renderer {


        float rotateTri, rotateQuad;

        private FloatBuffer quateBuffer;

        public GlRenderer() {
            childList = new ArrayList<>();
            dots = new ArrayList<>();
           /* Wall wall = new Wall();
            wall.endDot.x = -0.5f;
            wall.endDot.y = -0.5f;
            Wall wall2 = new Wall();
            wall2.headDot = wall.endDot;
            wall2.endDot.x = 0.5f;
            wall2.endDot.y = -0.5f;
            childList.add(wall);
            childList.add(wall2);*/

        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 启用阴影平滑
            gl.glShadeModel(GL10.GL_SMOOTH);
            // 灰色背景
            gl.glClearColor(1f, 1f, 1f, 1f);
            // 设置深度缓存
            gl.glClearDepthf(1.0f);
            // 启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
            // 所作深度测试的类型
            gl.glDepthFunc(GL10.GL_LEQUAL);
            // 告诉系统对透视进行修正
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
            gl.glLineWidth(5);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            float ratio = (float) width / height;
            //设置OpenGL场景的大小
            gl.glViewport(0, 0, width, height);
            //设置投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //重置投影矩阵
            gl.glLoadIdentity();
            // 设置视口的大小
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
            // 选择模型观察矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            // 重置模型观察矩阵
            gl.glLoadIdentity();

        }

        @Override
        public void onDrawFrame(GL10 gl) {


            // 清除屏幕和深度缓存
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            // 重置当前的模型观察矩阵
            gl.glLoadIdentity();

            //设置定点数组
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

            gl.glTranslatef(0.0f, 0.0f, -1.5f);

            // 设置黑色画笔
            gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            //遍历所有元素并绘制
            for (BaseChild child : childList) {
                child.draw(gl);
            }
            gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            //设置旋转
            //gl.glRotatef(rotateQuad, 1.0f, 1.0f, 0.0f);


            // 绘制结束
            gl.glFinish();
            // 取消顶点数组
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

            //改变旋转的角度
            //rotateTri += 0.5f;
            rotateQuad += 5f;


        }

    }

    /**
     * 首次编辑模式下添加墙体
     *
     * @param
     */
    private void drawWall(float x1, float y1) {
        if (isFirstWall) {
            firstDot = new Dot(x1, y1);
            dots.add(firstDot);
            isFirstWall = false;
            return;
        }
        Dot dot = new Dot(x1, y1);
        //假如新增点接近首个点，并且有两条以上的线条，让其合并并退出编辑模式
        if (Math.abs(firstDot.x - dot.x) < 0.08f && Math.abs(firstDot.y - dot.y) < 0.08f) {
            if (childList.size() > 2) {
                Wall wall = new Wall(dots.get(dots.size() - 1), firstDot);
                childList.add(wall);
                isInitial = false;
            }
            return;
        }
        //头尾衔接
        Wall wall = new Wall(dots.get(dots.size() - 1), dot);
        dots.add(dot);
        childList.add(wall);
    }

    /**
     * 撤销上一步操作
     */
    public void revoke() {
        if (childList.size() == 0 || dots.size() == 0) {
            return;
        }
        childList.remove(childList.size() - 1);
        dots.remove(dots.size() - 1);
    }

    /**
     * 清空元素
     */
    public void clear() {
        childList.clear();
        dots.clear();
        isFirstWall = true;
        isInitial = true;
    }


}
