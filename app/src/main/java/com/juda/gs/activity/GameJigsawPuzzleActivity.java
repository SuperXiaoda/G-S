package com.juda.gs.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.juda.gs.R;
import com.youngfeng.snake.annotations.EnableDragToClose;

import butterknife.BindView;

import com.juda.gs.util.jigsaw_puzzle.GestureHelper;
import com.juda.gs.util.jigsaw_puzzle.JigsawHelper;
import com.juda.gs.bean.Jigsaw;


/**
 * @Description: 拼图页面
 * @author: CodingDa
 * @Date : 2020/3/5 14:18
 */
@EnableDragToClose
public class GameJigsawPuzzleActivity extends BaseActivity implements View.OnClickListener {

    // 返回按钮
    @BindView(R.id.back)
    AppCompatImageButton mBack;
    // 标题
    @BindView(R.id.title)
    AppCompatTextView mTitle;
    // 拍照
    @BindView(R.id.photograph)
    AppCompatButton mPhotograph;
    // 选择图片
    @BindView(R.id.choice_image)
    AppCompatButton mChoiceImage;
    // 拼图布局
    @BindView(R.id.jigsaw_puzzle_grid)
    GridLayout mJigsawPuzzleGrid;

    //维护拼图碎片的集合
    private int[][] mJigsawArray = new int[3][5];
    private ImageView[][] mImageViewArray = new ImageView[3][5];
    //空白拼图碎片
    private ImageView mEmptyImageView;

    private GestureDetector mGestureDetector;

    //动画是否在执行，避免快速点击导致动画重复执行
    private boolean isAnimated;

    // 图片选择请求吗
    private static final int REQUEST_CODE_IMAGE = 1001;
    // 单开相机请求吗
    private static final int REQUEST_CODE_CAMERA = 1002;

    @Override
    protected int getContentView() {
        return R.layout.activity_game_jigsaw_puzzle;
    }

    @Override
    protected void init() {
        mTitle.setText(R.string.jigsaw_puzzle);
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //判断手指滑动方向
                int gestureDirection = GestureHelper.getInstance().getGestureDirection(e1, e2);
                //处理滑动拼图
                handleFlingGesture(gestureDirection, true);
                return true;
            }
        });
        //初始化拼图碎片
        Bitmap jigsaw = JigsawHelper.getInstance().getJigsaw(this);
        initJigsaw(jigsaw);
        new Handler().postDelayed(this::randomJigsaw, 200);
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
        mPhotograph.setOnClickListener(this);
        mChoiceImage.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.photograph:
                //      GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHandlerResultCallback);
                break;
            case R.id.choice_image:
                //    GalleryFinal.openGalleryMulti(REQUEST_CODE_IMAGE, 1, mOnHandlerResultCallback);
                break;
        }
    }


    private int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 处理拼图间的移动
     *
     * @param imageView
     * @param animation
     */
    private void handleClickItem(final ImageView imageView, boolean animation) {
        if (animation) {
            handleClickItem(imageView);
        } else {
            changeJigsawData(imageView);
        }
    }

    /**
     * 游戏初始化，随机打乱顺序
     */
    private void randomJigsaw() {
        for (int i = 0; i < 100; i++) {
            int gestureDirection = (int) ((Math.random() * 4) + 1);
            handleFlingGesture(gestureDirection, false);
        }
    }

    /**
     * 初始化拼图碎片
     *
     * @param jigsawBitmap
     */
    private void initJigsaw(Bitmap jigsawBitmap) {
        int itemWidth = jigsawBitmap.getWidth() / 5;
        int itemHeight = jigsawBitmap.getHeight() / 3;

        //切割原图为拼图碎片装入GridLayout
        for (int i = 0; i < mJigsawArray.length; i++) {
            for (int j = 0; j < mJigsawArray[0].length; j++) {
                Bitmap bitmap = Bitmap.createBitmap(jigsawBitmap, j * itemWidth, i * itemHeight, itemWidth, itemHeight);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否可移动
                        boolean isNearBy = JigsawHelper.getInstance().isNearByEmptyView((ImageView) v, mEmptyImageView);
                        if (isNearBy) {
                            //处理移动
                            handleClickItem((ImageView) v, true);
                        }
                    }
                });
                //绑定数据
                imageView.setTag(new Jigsaw(i, j, bitmap));
                //添加到拼图布局
                mImageViewArray[i][j] = imageView;
                mJigsawPuzzleGrid.addView(imageView);
            }
        }
        //设置拼图空碎片
        ImageView imageView = (ImageView) mJigsawPuzzleGrid.getChildAt(mJigsawPuzzleGrid.getChildCount() - 1);
        imageView.setImageBitmap(null);
        mEmptyImageView = imageView;

    }


    /**
     * 处理手势移动拼图
     *
     * @param gestureDirection
     * @param animation        是否带有动画
     */
    private void handleFlingGesture(int gestureDirection, boolean animation) {
        ImageView imageView = null;
        Jigsaw emptyJigsaw = (Jigsaw) mEmptyImageView.getTag();
        switch (gestureDirection) {
            case GestureHelper.LEFT:
                if (emptyJigsaw.getOriginalY() + 1 <= mJigsawPuzzleGrid.getColumnCount() - 1) {
                    imageView = mImageViewArray[emptyJigsaw.getOriginalX()][emptyJigsaw.getOriginalY() + 1];
                }
                break;
            case GestureHelper.RIGHT:
                if (emptyJigsaw.getOriginalY() - 1 >= 0) {
                    imageView = mImageViewArray[emptyJigsaw.getOriginalX()][emptyJigsaw.getOriginalY() - 1];
                }
                break;
            case GestureHelper.UP:
                if (emptyJigsaw.getOriginalX() + 1 <= mJigsawPuzzleGrid.getRowCount() - 1) {
                    imageView = mImageViewArray[emptyJigsaw.getOriginalX() + 1][emptyJigsaw.getOriginalY()];
                }
                break;
            case GestureHelper.DOWN:
                if (emptyJigsaw.getOriginalX() - 1 >= 0) {
                    imageView = mImageViewArray[emptyJigsaw.getOriginalX() - 1][emptyJigsaw.getOriginalY()];
                }
                break;
            default:
                break;
        }
        if (imageView != null) {
            handleClickItem(imageView, animation);
        }
    }

    /**
     * 处理点击拼图的移动事件
     *
     * @param imageView
     */
    private void handleClickItem(final ImageView imageView) {
        if (!isAnimated) {
            TranslateAnimation translateAnimation = null;
            if (imageView.getX() < mEmptyImageView.getX()) {
                //左往右
                translateAnimation = new TranslateAnimation(0, imageView.getWidth(), 0, 0);
            }

            if (imageView.getX() > mEmptyImageView.getX()) {
                //右往左
                translateAnimation = new TranslateAnimation(0, -imageView.getWidth(), 0, 0);
            }

            if (imageView.getY() > mEmptyImageView.getY()) {
                //下往上
                translateAnimation = new TranslateAnimation(0, 0, 0, -imageView.getHeight());
            }

            if (imageView.getY() < mEmptyImageView.getY()) {
                //上往下
                translateAnimation = new TranslateAnimation(0, 0, 0, imageView.getHeight());
            }

            if (translateAnimation != null) {
                translateAnimation.setDuration(80);
                translateAnimation.setFillAfter(true);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isAnimated = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //清除动画
                        isAnimated = false;
                        imageView.clearAnimation();
                        //交换拼图数据
                        changeJigsawData(imageView);
                        //判断游戏是否结束
                        boolean isFinish = JigsawHelper.getInstance().isFinishGame(mImageViewArray, mEmptyImageView);
                        if (isFinish) {
                            Toast.makeText(GameJigsawPuzzleActivity.this, "拼图成功，游戏结束！", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                imageView.startAnimation(translateAnimation);
            }
        }
    }

    /**
     * 交换拼图数据
     *
     * @param imageView
     */
    public void changeJigsawData(ImageView imageView) {
        Jigsaw emptyJigsaw = (Jigsaw) mEmptyImageView.getTag();
        Jigsaw jigsaw = (Jigsaw) imageView.getTag();

        //更新imageView的显示内容
        mEmptyImageView.setImageBitmap(jigsaw.getBitmap());
        imageView.setImageBitmap(null);
        //交换数据
        emptyJigsaw.setCurrentX(jigsaw.getCurrentX());
        emptyJigsaw.setCurrentY(jigsaw.getCurrentY());
        emptyJigsaw.setBitmap(jigsaw.getBitmap());

        //更新空拼图引用
        mEmptyImageView = imageView;
    }

}
