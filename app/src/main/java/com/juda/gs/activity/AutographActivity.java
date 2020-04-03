package com.juda.gs.activity;

import android.os.Environment;
import com.juda.gs.R;
import com.juda.gs.util.FileUtil;
import com.juda.gs.util.ToastUtil;
import com.juda.gs.view.LinePathView;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * @description: 说明
 * @author: CodingDa
 * @date : 2019/12/6 9:57
 */
public class AutographActivity extends BaseActivity {

    // 返回按钮
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    // 返回
    @BindView(R.id.back_title)
    AppCompatTextView mTitle;
    // 签名文字
    @BindView(R.id.line_path_view)
    LinePathView mLinePathView;

    // 签名文件文件夹
    private String mAutographFiles;

    @Override
    protected int getContentView() {
        return R.layout.activity_autograph;
    }

    @Override
    protected void init() {
        mToolbar.inflateMenu(R.menu.autograph);
        mTitle.setText(getString(R.string.autograph));
        if (FileUtil.getSdState()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            mAutographFiles = sdcardDir.getPath() + "/GS/Autograph/";
            FileUtil.checkDir(mAutographFiles);
        }
    }

    @Override
    protected void setListener() {
        mTitle.setOnClickListener(((v -> {
            finish();
        })));

        mToolbar.setOnMenuItemClickListener(((item -> {
            switch (item.getItemId()) {
                case R.id.clear_autograph:
                    mLinePathView.clear();
                    return true;
                case R.id.save_autograph:
                    if (mLinePathView.getTouched()) {
                        try {
                            mAutographFiles = mAutographFiles + System.currentTimeMillis() + ".png";
                            mLinePathView.save(mAutographFiles, true, 10);
                            ToastUtil.showShort("签名文件保存完成GS->Autograph目录下查看");
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtil.showShort(getString(R.string.autograph_null));
                    }
                    return true;
            }
            return false;
        })));
    }


}
