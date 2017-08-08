package moocollege.cn.listindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zsd on 2017/8/8 09:28
 * desc:字母索引的侧边栏
 */

public class SliderBar extends View {

    private Paint mPaint;
    // 定义26个字母
    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    //当前选中的字母
    private String mCurrentSelectedLetter;


    public SliderBar(Context context) {
        this(context, null);
    }

    public SliderBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        // 自定义属性，颜色  字体大小
        mPaint.setTextSize(sp2px(15));// 设置的是像素
        // 默认颜色
        mPaint.setColor(Color.BLACK);
    }

    // sp 转 px
    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 宽度 = 左右的padding + 字母的宽度(取决于你的画笔)
        int textWidth = (int) mPaint.measureText("A");// A字母的宽度,这里随便一个字母的宽度都行
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        // 高度可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取每个字母的高度
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
        for (int i = 0; i < mLetters.length; i++) {
            //每个字母的中间位置
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;
            int textWidth = (int) mPaint.measureText(mLetters[i]);
            int x = getWidth() / 2 - textWidth / 2;
            if (mLetters[i].equals(mCurrentSelectedLetter)) {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            } else {
                mPaint.setColor(Color.BLACK);
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //获得手指当前的触摸位置Y
                float currentY = event.getY();
                //字母的高度
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
                //获得手指触摸位置是在哪个字母的位置
                int currentPosition = (int) (currentY / itemHeight);
                if (currentPosition < 0)
                    currentPosition = 0;
                if (currentPosition > mLetters.length - 1)
                    currentPosition = mLetters.length - 1;
                mCurrentSelectedLetter = mLetters[currentPosition];
                if (mListener != null) {
                    mListener.touch(mCurrentSelectedLetter, true);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.touch(mCurrentSelectedLetter, false);
                }
                mCurrentSelectedLetter = "";
                invalidate();
                break;
        }
        return true;
    }

    public interface LetterTouchListener {
        void touch(String letter, boolean isTouch);
    }

    private LetterTouchListener mListener;

    public void setOnLetterTouchListener(LetterTouchListener listener) {
        this.mListener = listener;
    }
}
