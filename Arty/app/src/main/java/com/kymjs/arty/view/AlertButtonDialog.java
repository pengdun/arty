package com.kymjs.arty.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kymjs.arty.R;
import com.kymjs.arty.utils.ViewUtils;

/**
 * <b>Create Date:</b> 2017/1/8<br>
 * <b>Author:</b> pengdun<br>
 * <b>Description:</b> <br>
 */
public class AlertButtonDialog extends Dialog {
    private String mTitle = "";
    private String mContent = "";
    private ButtonEnum mButtonEnum = ButtonEnum.TWO;
    private String mLeftStr = "";
    private String mRigthStr = "";
    private int mIconRid = R.mipmap.ic_base_dialog_prompt;
    private Context context;

    private DialogOnClickListener listener;
    private TextView mTitle_tv;
    private TextView mContent_tv;
    private View mDiving_view;
    private Button mLeft_btn;
    private Button mRigth_btn;
    private int mContentGravity = -1;
    private boolean isCancel = false;
    private Spanned mContentSpanned;


    public interface DialogOnClickListener {

        void onRigth();// 右

        void onLeft();// 左

    }

    public AlertButtonDialog(Context context, DialogOnClickListener listener) {
        super(context, R.style.Dialog_AlertDialog);
        this.context = context;
        this.listener = listener;
    }

    public AlertButtonDialog(Context context, String content, DialogOnClickListener listener) {
        super(context, R.style.Dialog_AlertDialog);
        this.context = context;
        this.listener = listener;
        this.mContent = content;
    }

    public AlertButtonDialog(Context context, int contentRid, DialogOnClickListener listener) {
        super(context, R.style.Dialog_AlertDialog);
        this.context = context;
        this.listener = listener;
        this.mContent = context.getString(contentRid);
    }

    public AlertButtonDialog(Context context, PromptIconEnumType iconEnumType, int titleRid, int contentRid, DialogOnClickListener listener) {
        super(context, R.style.Dialog_AlertDialog);
        this.context = context;
        this.mIconRid = iconEnumType.iconRid;
        this.mTitle = getContext().getString(titleRid);
        this.mContent = getContext().getString(contentRid);
        this.listener = listener;
    }

    public AlertButtonDialog(Context context, PromptIconEnumType iconEnumType, String title, String content, DialogOnClickListener listener) {
        super(context, R.style.Dialog_AlertDialog);
        this.context = context;
        this.mIconRid = iconEnumType.iconRid;
        this.mTitle = title;
        this.mContent = content;
        this.listener = listener;
    }


    /**
     * @param context
     * @param iconEnumType 提示图标显示的样式
     * @param title        标题
     * @param content      内容
     * @param buttonEnum   出现几个按钮，1.出现一个“确定” 2.出现两个按钮 “取消”，“确定”，可以修改值
     * @param leftStr      左边按钮的字符串
     * @param rightStr     右边按钮的字符串
     * @param listener     监听
     */
    public AlertButtonDialog(Context context, PromptIconEnumType iconEnumType, String title, String content, ButtonEnum buttonEnum, String leftStr, String rightStr, DialogOnClickListener listener) {
        super(context, R.style.Dialog_AlertDialog);
        this.context = context;
        if (iconEnumType != null) this.mIconRid = iconEnumType.iconRid;
        if (!TextUtils.isEmpty(title)) this.mTitle = title;
        if (!TextUtils.isEmpty(content)) this.mContent = content;
        if (mButtonEnum != null) this.mButtonEnum = buttonEnum;
        if (!TextUtils.isEmpty(leftStr)) this.mLeftStr = leftStr;
        if (!TextUtils.isEmpty(rightStr)) this.mRigthStr = rightStr;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_alertdialog_layout);

        mTitle_tv = (TextView) findViewById(R.id.dialog_title_tv);
        mContent_tv = (TextView) findViewById(R.id.dialog_content_tv);
        mDiving_view = findViewById(R.id.dialog_diving_vertical_view);
        mLeft_btn = (Button) findViewById(R.id.dialog_left_btn);
        mRigth_btn = (Button) findViewById(R.id.dialog_right_btn);

        mLeft_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeft();
                }
                dismiss();
            }
        });
        mRigth_btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRigth();
                }
                dismiss();
            }
        });

        ViewUtils.setTextViewDrawbleLeft(mTitle_tv, mIconRid);
        mTitle_tv.setText(mTitle);
        mContent_tv.setText(mContent);
        mLeft_btn.setText(mLeftStr);
        mRigth_btn.setText(mRigthStr);
        setButtonEnumShow();
        if (mContentGravity > 0) {
            mContent_tv.setGravity(mContentGravity);
        }
        if (isCancel) {
            setCancelable(isCancel);
            setCanceledOnTouchOutside(true);
        }
        if (mContentSpanned != null) {
            mContent_tv.setText(mContentSpanned);
        }
    }

    /**
     * 设置提示图标
     *
     * @param iconEnumType
     */
    public void setIcon(PromptIconEnumType iconEnumType) {
        mIconRid = iconEnumType.iconRid;
        if (mTitle_tv != null) {
            ViewUtils.setTextViewDrawbleLeft(mTitle_tv, iconEnumType.iconRid);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitle = title;
        if (mTitle_tv != null) {
            mTitle_tv.setText(title);
        }
    }

    public void setTitle(int titleId) {
        if (titleId < 0) {
            return;
        }
        mTitle = getContext().getString(titleId);
        if (mTitle_tv != null) {
            mTitle_tv.setText(titleId);
        }
    }

    /**
     * 设置内容
     *
     * @param contentText
     */
    public void setContentText(String contentText) {
        if (TextUtils.isEmpty(contentText)) return;
        mContent = contentText;
        if (mContent_tv != null) {
            mContent_tv.setText(contentText);
        }
    }

    /**
     * 设置内容
     *
     * @param contentSpanned
     */
    public void setContentText(Spanned contentSpanned) {
        mContentSpanned = contentSpanned;
        if (mContent_tv != null) {
            mContent_tv.setText(contentSpanned);
        }
    }

    public void setContentText(int contentTextRid) {
        if (contentTextRid < 0) {
            return;
        }
        mContent = getContext().getString(contentTextRid);
        if (mContent_tv != null) {
            mContent_tv.setText(contentTextRid);
        }
    }

    /**
     * 设置内容的对齐方式
     *
     * @param contentGravity
     */
    public void setContentGravity(int contentGravity) {
        mContentGravity = contentGravity;
        if (mContent_tv != null) {
            mContent_tv.setGravity(contentGravity);
        }
    }

    /**
     * 设置左边的文字
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        mLeftStr = leftText;
        if (mLeft_btn != null) {
            mLeft_btn.setText(leftText);
        }
    }

    public void setLeftText(int leftTextId) {
        if (leftTextId < 0) {
            return;
        }
        mLeftStr = getContext().getString(leftTextId);
        if (mLeft_btn != null) {
            mLeft_btn.setText(leftTextId);
        }
    }

    /**
     * 设置右边的文字
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        mRigthStr = rightText;
        if (mRigth_btn != null) {
            mRigth_btn.setText(rightText);
        }
    }

    public void setRightText(int rightTextRid) {
        if (rightTextRid < 0) {
            return;
        }
        mRigthStr = getContext().getString(rightTextRid);
        if (mRigth_btn != null) {
            mRigth_btn.setText(rightTextRid);
        }
    }

    /**
     * 设置按钮的个数
     */
    public void setButtonNum(ButtonEnum buttonNum) {
        mButtonEnum = buttonNum;
        if (mLeft_btn != null) {
            setButtonEnumShow();
        }
    }

    private void setButtonEnumShow() {
        if (mButtonEnum == ButtonEnum.ONE) {
            mDiving_view.setVisibility(View.GONE);
            mLeft_btn.setVisibility(View.GONE);
        } else {
            mDiving_view.setVisibility(View.VISIBLE);
            mLeft_btn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 提示图标的枚举
     */
    public enum PromptIconEnumType {
        PROMPT(R.mipmap.ic_base_dialog_prompt),//提示
        WRING(R.mipmap.ic_base_dialog_wring);  //警告

        public int iconRid = R.mipmap.ic_base_dialog_prompt;

        PromptIconEnumType(int rid) {
            this.iconRid = rid;
        }
    }

    /**
     * 按钮的个数的枚举，一个或者两个
     */
    public enum ButtonEnum {
        ONE,
        TWO
    }
}
