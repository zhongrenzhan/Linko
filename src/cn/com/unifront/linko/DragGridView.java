
package cn.com.unifront.linko;

import cn.com.unifront.adapter.GridViewAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class DragGridView extends GridView {
    private static final String TAG = "DragGridView";
    private int mDragItemPosition;
    private int mDragSrcItemPosition;
    private int mDragPointPositionX;
    private int mDragPointPositionY;
    private int mDragOffsetX;
    private int mDragOffsetY;
    private ImageView mDragImageView;
    private int upScrollBounce;
    private int downScrollBounce;
    private SharedPreferences sp;
    private static String NAME_ITEMS = "name_items_";
    private static String IMAGE_ITEMS = "image_items_";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private int mScaledTouchSlop;

    public DragGridView(Context context) {
        super(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        mScaledTouchSlop =ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        mScaledTouchSlop =ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public DragGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        mScaledTouchSlop =ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int downPositionX = (int) ev.getX();
        int downPositionY = (int) ev.getY();
        mDragSrcItemPosition = mDragItemPosition = pointToPosition(downPositionX, downPositionY);

        if (mDragItemPosition == AdapterView.INVALID_POSITION) {
            return super.onInterceptTouchEvent(ev);
        }
        ViewGroup viewGroup = (ViewGroup) getChildAt(mDragItemPosition - getFirstVisiblePosition());
        mDragPointPositionX = downPositionX - viewGroup.getLeft();
        mDragPointPositionY = downPositionY - viewGroup.getTop();
        mDragOffsetX = (int) (ev.getRawX() - downPositionX);
        mDragOffsetY = (int) (ev.getRawY() - downPositionY);

        upScrollBounce = Math.min(downPositionY - mScaledTouchSlop, getHeight() / 4);// distance of scroll up 
        downScrollBounce = Math.max(downPositionY + mScaledTouchSlop, getHeight() * 3 / 4);//distance of scroll down

        Log.d(TAG, "downPositionX:" + downPositionX + "   downPositionY:" + downPositionY);
        Log.d(TAG, "viewGroup.getLeft():" + viewGroup.getLeft() + "   viewGroup.getTop():"
                + viewGroup.getTop());
        Log.d(TAG, "ev.getRawX():" + ev.getRawX() + "   ev.getRawY():" + ev.getRawY());
        Log.d(TAG, "mDragPointPositionX:" + mDragPointPositionX + "   mDragPiontPositionY:"
                + mDragPointPositionY);
        Log.d(TAG, "mDragOffsetX:" + mDragOffsetX + "   mDragOffsetY:" + mDragOffsetY);

        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(viewGroup.getDrawingCache());
        startDrag(bitmap, downPositionX, downPositionY);
        return true;
    }

    private void startDrag(Bitmap bitmap, int positionX, int positionY) {
        stopDrag();
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.x = positionX - mDragPointPositionX + mDragOffsetX;
        mLayoutParams.y = positionY - mDragPointPositionY + mDragOffsetY;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.windowAnimations = 0;
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);

        mWindowManager = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        mWindowManager.addView(imageView, mLayoutParams);
        mDragImageView = imageView;
    }

    public void stopDrag() {
        if (mDragImageView != null) {
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int movePositionX = (int) ev.getX();
                int movePositionY = (int) ev.getY();
                onDrag(movePositionX, movePositionY);
                break;

            case MotionEvent.ACTION_UP:
                int upPositionX = (int) ev.getX();
                int upPositionY = (int) ev.getY();
                stopDrag();
                onDrop(upPositionX, upPositionY);
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void onDrag(int movePositionX, int movePositionY) {
        if (mDragImageView != null) {
            mLayoutParams.alpha = 0.8f;
            mLayoutParams.x = movePositionX - mDragPointPositionX + mDragOffsetX;
            mLayoutParams.y = movePositionY - mDragPointPositionY + mDragOffsetY;
            mWindowManager.updateViewLayout(mDragImageView, mLayoutParams);
        }
        int tempPosition = pointToPosition(movePositionX, movePositionY);
        if (tempPosition != INVALID_POSITION) {
            mDragItemPosition = tempPosition;
        }
        if (movePositionY < upScrollBounce || movePositionY > downScrollBounce) {
            setSelection(mDragItemPosition);
        }
    }

    private void onDrop(int upPositionX, int upPositionY) {
        int tempPosition = pointToPosition(upPositionX, upPositionY);
        if (tempPosition != INVALID_POSITION) {
            mDragItemPosition = tempPosition;
        }
        if (upPositionY < getChildAt(0).getTop()) {
            mDragItemPosition = 0;
        } else if (upPositionY > getChildAt(getChildCount() - 1).getBottom() ||
                (upPositionY > getChildAt(getChildCount() - 1).getTop() &&
                upPositionX > getChildAt(getChildCount() - 1).getRight())) {
            mDragItemPosition = getAdapter().getCount() - 1;
        }
        if (mDragItemPosition != mDragSrcItemPosition && mDragSrcItemPosition != -1
                && mDragItemPosition > -1 && mDragItemPosition < getAdapter().getCount()) {
            GridViewAdapter adapter = (GridViewAdapter) getAdapter();
            String dragSrcItemName = sp.getString(NAME_ITEMS + mDragSrcItemPosition, "UNKNOW");
            String dragTargetItemName = sp.getString(NAME_ITEMS + mDragItemPosition, "UNKNOW");
            // change the name order
            sp.edit().putString(NAME_ITEMS + mDragItemPosition + "", dragSrcItemName).commit();
            sp.edit().putString(NAME_ITEMS + mDragSrcItemPosition + "", dragTargetItemName)
                    .commit();
            // change the image order
            String imageIdStringCurrent = sp.getString(IMAGE_ITEMS + mDragItemPosition,
                    R.drawable.ic_launcher + "");
            String imageIdStringSrc = sp.getString(IMAGE_ITEMS + mDragSrcItemPosition,
                    R.drawable.ic_launcher + "");
            sp.edit().putString(IMAGE_ITEMS + mDragItemPosition, imageIdStringSrc).commit();
            sp.edit().putString(IMAGE_ITEMS + mDragSrcItemPosition, imageIdStringCurrent).commit();
            adapter.notifyDataSetChanged();
        }
    }

}
