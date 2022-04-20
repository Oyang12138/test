package com.example.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MatrixImageView extends androidx.appcompat.widget.AppCompatImageView {
    private final static String TAG = "MatrixImageView";
    private GestureDetector mGestureDetector;
    private Matrix mMatrix = new Matrix();//模板Matrix，用以初始化
    private float mImageWidth;//图片长度
    private float mImageHeight;//图片高度

    private OnMovingListener moveListener;
    private OnSingleTapListener singleTapListener;

    public MatrixImageView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        MatrixTouchListener mListener = new MatrixTouchListener();
        setOnTouchListener(mListener);
        mGestureDetector = new GestureDetector(getContext(),new GestureListener(mListener));
        setBackgroundColor(Color.BLACK);//背景设置为black
//        setScaleType(ScaleType.CENTER);//将缩放类型设置为FIT_CENTER，表示把图片按比例扩大/缩小到View的宽度，居中显示
    }

    public void setOnMovingListener(OnMovingListener listener){
        this.moveListener = listener;
    }
    public void setOnSingleTapListener(OnSingleTapListener onSingleTapListener) {
        this.singleTapListener = onSingleTapListener;
    }

    @Override
    public void setImageBitmap(Bitmap bitmap){
        super.setImageBitmap(bitmap);
        //设置完图片后，获取该图片的坐标变换矩阵
        mMatrix.set(getImageMatrix());
        float[] values = new float[9];
        mMatrix.getValues(values);
        //图片宽度为屏幕宽度除缩放倍数
        mImageWidth = getWidth() / values[Matrix.MSCALE_X];
        mImageHeight = (getHeight() - values[Matrix.MSCALE_Y] * 2) / values[Matrix.MSCALE_Y];
    }

    public class MatrixTouchListener implements OnTouchListener{
        private static final int MODE_DRAG = 1;//拖拉照片模式
        private static final int MODE_ZOOM = 2;//放大缩小照片模式
        private static final int MODE_UNABLE = 3;//不支持Matrix
        float mMaxScale = 6;//最大缩放级别
        float mDoubleClickScale = 2;//双击时的缩放级别
        private int mMode = 0;
        private float mStartDis;//缩放开始时的手指间距
        private Matrix mCurrentMatrix = new Matrix();//当前Matrix
        private PointF startPoint = new PointF();//用于记录开始时候的坐标位置

        public void setDragMatrix(MotionEvent motionEvent){
            if (isZoomChanged()){
                float dx = motionEvent.getX() - startPoint.x;// 得到x轴的移动距离
                float dy = motionEvent.getY() - startPoint.y;// 得到x轴的移动距离
                if(Math.sqrt(dx * dx + dy * dy) > 10f){//避免和双击冲突,大于10f才算是拖动
                    startPoint.set(motionEvent.getX(),motionEvent.getY());
                    //在当前基础上移动
                    mCurrentMatrix.set(getImageMatrix());
                    float[] values = new float[9];
                    mCurrentMatrix.getValues(values);
                    dx = checkDxBound(values,dx);
                    dy = checkDyBound(values,dy);
                    mCurrentMatrix.postTranslate(dx,dy);
                    setImageMatrix(mCurrentMatrix);
                }
            }
        }

        //判断缩放级别是否是改变过
        private boolean isZoomChanged(){
            float[] values = new float[9];
            getImageMatrix().getValues(values);
            //获取当前X轴缩放级别
            float scale = values[Matrix.MSCALE_X];
            //获取模板的X轴缩放级别，两者做比较
            mMatrix.getValues(values);
            return scale != values[Matrix.MSCALE_X];
        }

        // 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
        private float checkDyBound(float[] values, float dy) {
            float height = getHeight();
            if(mImageHeight * values[Matrix.MSCALE_Y] < height)
                return 0;
            if(values[Matrix.MTRANS_Y] + dy > 0)
                dy = - values[Matrix.MTRANS_Y];
            else if(values[Matrix.MTRANS_Y] + dy < - (mImageHeight * values[Matrix.MSCALE_Y] - height))
                dy = - (mImageHeight * values[Matrix.MSCALE_Y] - height) - values[Matrix.MTRANS_Y];
            return dy;
        }

        //和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
        private float checkDxBound(float[] values,float dx) {
            float width = getWidth();
            if(mImageWidth * values[Matrix.MSCALE_X] < width)
                return 0;
            if(values[Matrix.MTRANS_X] + dx > 0)
                dx = - values[Matrix.MTRANS_X];
            else if(values[Matrix.MTRANS_X] + dx < - (mImageWidth * values[Matrix.MSCALE_X] - width))
                dx = - (mImageWidth * values[Matrix.MSCALE_X] - width) - values[Matrix.MTRANS_X];
            return dx;
        }

        //设置缩放Matrix
        private void setZoomMatrix(MotionEvent motionEvent){
            if (motionEvent.getPointerCount() < 2){
                return;//只有同时触屏两个点的时候才执行
            }
            float endDis = distance(motionEvent);//结束距离
            if (endDis > 10f){//两个手指并拢在一起的时候像素大于10
                float scale = endDis / mStartDis;//得到缩放倍数
                mStartDis = endDis;//重置距离
                mCurrentMatrix.set(getImageMatrix());//初始化Matrix
                float[] values = new float[9];
                mCurrentMatrix.getValues(values);

                scale = checkMaxScale(scale,values);
                setImageMatrix(mCurrentMatrix);
            }
        }

        //检验scale，使图像缩放后不会超出最大倍数
        private float checkMaxScale(float scale,float[] values){
            if (scale * values[Matrix.MSCALE_X] > mMaxScale){
                scale = mMaxScale / values[Matrix.MSCALE_X];
            }
            mCurrentMatrix.postScale(scale,scale,getWidth() / 2,getHeight() / 2);
            return scale;
        }

        //判断是否需要重置
        private boolean checkRest(){
            float[] values = new float[9];
            getImageMatrix().getValues(values);
            float scale = values[Matrix.MSCALE_X];//获取当前X轴缩放级别
            mMatrix.getValues(values);//获取模板的X轴缩放级别，两者做比较
            return scale < values[Matrix.MSCALE_X];
        }

        //重置Matrix
        private void reSetMatrix(){
            if(checkRest()){
                mCurrentMatrix.set(mMatrix);
                setImageMatrix(mCurrentMatrix);
            }
        }

        //判断是否支持Matrix
        private void isMatrixEnable() {
            //当加载出错时，不可缩放
            if(getScaleType() != ScaleType.CENTER){
                setScaleType(ScaleType.MATRIX);
            }else {
                mMode = MODE_UNABLE;//设置为不支持手势
            }
        }

        //计算两个手指间的距离
        private float distance(MotionEvent motionEvent){
            float dx = motionEvent.getX(1) - motionEvent.getX(0);
            float dy = motionEvent.getY(1) - motionEvent.getY(0);
            return (float) Math.sqrt(dx * dx + dy * dy);//使用勾股定理返回两点之间的距离
        }

        //双击时触发
        public void onDoubleClick(){
            float scale = isZoomChanged() ? 1 : mDoubleClickScale;
            mCurrentMatrix.set(mMatrix);//初始化Matrix
            mCurrentMatrix.postScale(scale, scale,getWidth() / 2,getHeight() / 2);
            setImageMatrix(mCurrentMatrix);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()){
                case MotionEvent.ACTION_DOWN://设置拖动模式
                    mMode = MODE_DRAG;
                    startPoint.set(motionEvent.getX(),motionEvent.getY());
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    reSetMatrix();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mMode == MODE_ZOOM){
                        setZoomMatrix(motionEvent);
                    }else if (mMode == MODE_DRAG){
                        setDragMatrix(motionEvent);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (mMode == MODE_UNABLE){
                        return true;
                    }
                    mMode = MODE_ZOOM;
                    mStartDis = distance(motionEvent);
                    break;
                default:
                    break;
            }
            return mGestureDetector.onTouchEvent(motionEvent);
        }
    }

    private class  GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final MatrixTouchListener listener;
        public GestureListener(MatrixTouchListener listener) {
            this.listener=listener;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            //捕获Down事件
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //触发双击事件
            listener.onDoubleClick();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }
    }

    public interface OnMovingListener{
        public void  startDrag();
        public void  stopDrag();
    }

    public interface OnSingleTapListener{
        public void onSingleTap();
    }
}
