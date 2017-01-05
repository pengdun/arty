package com.kymjs.arty.module.diary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.kymjs.arty.R;
import com.kymjs.arty.db.UserDiaryDao;
import com.kymjs.arty.module.base.BaseActivity;
import com.kymjs.arty.utils.SimpleTextWatcher;
import com.kymjs.arty.utils.StringByTime;
import com.kymjs.arty.utils.TypefaceUtils;
import com.kymjs.common.App;
import com.kymjs.common.StringUtils;
import com.kymjs.common.function.ThreadSwitch;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.kymjs.common.PreferenceHelper.readString;
import static com.kymjs.common.PreferenceHelper.remove;
import static com.kymjs.common.PreferenceHelper.write;

/**
 * Created by ZhangTao on 1/4/17.
 */
public class EditDiaryActivity extends BaseActivity {

    public final static String DIARY_UUID = "diary_uuid";
    public final static String DIARY_PREFERENCE_FILE = "edit_diary";
    public final static String DIARY_TITLE_KEY = "edit_diary_title";
    public final static String DIARY_CONTENT_KEY = "edit_diary_content";

    @BindView(R.id.edit_title)
    EditText mEtDiaryTitle;
    @BindView(R.id.edit_content)
    EditText mEtDiaryContent;
    @BindView(R.id.edit_fabButton)
    FloatingActionButton mFabSave;
    @BindView(R.id.edit_scroll_view)
    ScrollView mScrollView;

    private boolean unchanged = true;

    private UserDiaryDao mDiaryDao;
    private String originContent, originTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        TypefaceUtils.setTypeface(mEtDiaryTitle, mEtDiaryContent);
        mDiaryDao = new UserDiaryDao(this);

        final Intent intent = getIntent();
        String diaryID = intent.getStringExtra(DIARY_UUID);
        if (!TextUtils.isEmpty(diaryID)) {
            setDiary(diaryID);
        } else {
            originTitle = readString(App.INSTANCE, DIARY_PREFERENCE_FILE, DIARY_TITLE_KEY);
            originContent = readString(App.INSTANCE, DIARY_PREFERENCE_FILE, DIARY_CONTENT_KEY);
        }

        SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                unchanged = false;
            }
        };

        mEtDiaryTitle.addTextChangedListener(textWatcher);
        mEtDiaryContent.addTextChangedListener(textWatcher);
        mEtDiaryTitle.setHint(StringByTime.getEditTitleHintByNow());
        mEtDiaryContent.setHint(StringByTime.getEditContentHintByNow());

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                onCancelEditClick();
            }
        });
    }

    @Override
    public void onBackPressed() {
        boolean unchangedFlag = (TextUtils.equals(originContent, mEtDiaryContent.getText().toString().trim())
                && TextUtils.equals(originTitle, mEtDiaryTitle.getText().toString().trim()))
                || unchanged;
        if (unchangedFlag) {
            super.onBackPressed();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.want_to_save)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveDiary();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();

        }
    }

    @OnClick(R.id.layout_root)
    void onCancelEditClick() {
        if (mEtDiaryContent.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEtDiaryContent, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @OnClick(R.id.edit_fabButton)
    void onSaveClick() {
        saveDiary();
    }

    private List<Diary> mTempDiaryList = null;

    void setDiary(final String id) {
        final ThreadSwitch threadSwitch = ThreadSwitch.get();
        threadSwitch.io(new ThreadSwitch.IO() {
            @Override
            public void run() {
                mTempDiaryList = mDiaryDao.query("where _id =" + id);
            }
        }).ui(new ThreadSwitch.UI() {
            @Override
            public void run() {
                if (mTempDiaryList != null && !mTempDiaryList.isEmpty()) {
                    originContent = mTempDiaryList.get(0).getContent();
                    originTitle = mTempDiaryList.get(0).getTitle();
                    mEtDiaryTitle.setText(originTitle);
                    mEtDiaryContent.setText(originContent);
                    mTempDiaryList = null;
                }
                threadSwitch.breakTask();
            }
        });
    }

    private boolean saveSuccess = false;

    void saveDiary() {
        originTitle = mEtDiaryTitle.getText().toString();
        originContent = mEtDiaryContent.getText().toString();

        if (!StringUtils.isEmpty(originTitle, originContent)) {
            final ThreadSwitch threadSwitch = ThreadSwitch.get();
            threadSwitch.io(new ThreadSwitch.IO() {
                @Override
                public void run() {
                    write(App.INSTANCE, DIARY_PREFERENCE_FILE, DIARY_TITLE_KEY, originTitle);
                    write(App.INSTANCE, DIARY_PREFERENCE_FILE, DIARY_CONTENT_KEY, originContent);
                    Diary tempDiary = new Diary();
                    tempDiary.setTitle(originTitle);
                    tempDiary.setContent(originContent);
                    tempDiary.setCreateTime(new Timestamp(new Date().getTime()));
                    saveSuccess = mDiaryDao.save(tempDiary);
                    if (saveSuccess) {
                        remove(App.INSTANCE, DIARY_PREFERENCE_FILE, DIARY_TITLE_KEY);
                        remove(App.INSTANCE, DIARY_PREFERENCE_FILE, DIARY_CONTENT_KEY);
                    }
                }
            }).ui(new ThreadSwitch.UI() {
                @Override
                public void run() {
                    if (saveSuccess) {
                        Toast.makeText(App.INSTANCE, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(App.INSTANCE, R.string.save_failure, Toast.LENGTH_SHORT).show();
                    }
                    threadSwitch.breakTask();
                }
            });
        }
        finish();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, EditDiaryActivity.class));
    }

    public static void start(Context context, long diaryId) {
        Intent intent = new Intent(context, EditDiaryActivity.class);
        intent.putExtra(DIARY_UUID, String.valueOf(diaryId));
        context.startActivity(intent);
    }
}
