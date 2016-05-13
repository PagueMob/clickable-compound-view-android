package co.braspay.clickableCompoundView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

abstract class BaseView extends RelativeLayout {

    private LayoutInflater inflater;
    protected View leftCompound, rightCompound;
    protected TextView itemView;

    private OnClickListener compoundClickListener;
    private OnRightCompoundClickListener rightCompoundClickListener;
    private OnLeftCompoundClickListener leftCompoundClickListener;

    public BaseView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        createClickListener();

        inflater = LayoutInflater.from(getContext());

        createMainItem(context, attrs, defStyleAttr, defStyleRes);
        createCompoundButtons(context, attrs);

        setPadding(0, 0, 0, 0);
    }

    private void createMainItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        configLayoutParams(params);

        itemView = createItemView(context, attrs, defStyleAttr, defStyleRes);
        itemView.setId(R.id.compound_item);

        addView(itemView, params);
    }

    private void createCompoundButtons(Context context, AttributeSet attrs) {
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.CompoundView, 0, 0);

        if (a == null || a.length() == 0) {
            return;
        }

        View compoundView = createCompoundImageButton(a, R.styleable.CompoundView_leftImage, this);
        if (compoundView == null) {
            compoundView = createCompoundTextButton(a, R.styleable.CompoundView_leftText, this);
        }
        if (compoundView != null) {
            configAndAddLeftCompoundView(compoundView);
        }
        leftCompound = compoundView;

        compoundView = createCompoundImageButton(a, R.styleable.CompoundView_rightImage, this);
        if (compoundView == null) {
            compoundView = createCompoundTextButton(a, R.styleable.CompoundView_rightText, this);
        }
        if (compoundView != null) {
            configAndAddRightCompoundView(compoundView);
        }
        rightCompound = compoundView;

        a.recycle();
    }

    private void configAndAddLeftCompoundView(View compoundView) {
        LayoutParams params = (LayoutParams) compoundView.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(getPaddingLeft(), 0, 0, 0);

        addView(compoundView, params);
        compoundView.setId(R.id.compound_left);
        compoundView.setOnClickListener(compoundClickListener);
    }

    private void configAndAddRightCompoundView(View compoundView) {
        LayoutParams params = (LayoutParams) compoundView.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(0, 0, getPaddingRight(), 0);

        addView(compoundView, params);
        compoundView.setId(R.id.compound_right);
        compoundView.setOnClickListener(compoundClickListener);
    }

    private View createCompoundTextButton(TypedArray a, int index, ViewGroup parent) {
        if (!a.hasValue(index)) {
            return null;
        }

        Button b = (Button) inflater.inflate(R.layout.compound_button, parent, false);
        b.setText(a.getText(index));
        return b;
    }

    private View createCompoundImageButton(TypedArray a, int index, ViewGroup parent) {
        if (!a.hasValue(index)) {
            return null;
        }

        ImageButton b = (ImageButton) inflater.inflate(R.layout.compound_image_button, parent, false);
        b.setImageDrawable(a.getDrawable(index));
        return b;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int leftPadding = itemView.getCompoundDrawablePadding();
        int rightPadding = itemView.getCompoundDrawablePadding();

        if (leftCompound != null && leftPadding < leftCompound.getWidth()) {
            leftPadding += leftCompound.getWidth();
            leftPadding += ((LayoutParams) leftCompound.getLayoutParams()).leftMargin;
        }

        if (rightCompound != null && rightPadding < rightCompound.getWidth()) {
            rightPadding += rightCompound.getWidth();
            rightPadding += ((LayoutParams) rightCompound.getLayoutParams()).rightMargin;
        }

        itemView.setPadding(leftPadding, itemView.getPaddingTop(), rightPadding, itemView.getPaddingBottom());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void createClickListener() {
        compoundClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                if (v == BaseView.this) {
                    itemView.requestFocus();
                }
                else if (viewId == R.id.compound_left) {
                    if (leftCompoundClickListener != null) {
                        leftCompoundClickListener.onLeftCompoundClick();
                    }

                }
                else if (viewId == R.id.compound_right) {
                    if (rightCompoundClickListener != null) {
                        rightCompoundClickListener.onRightCompoundClick();
                    }
                }
            }
        };
        setOnClickListener(compoundClickListener);
    }

    // ----- Abstract methods -------------------------------------------------

    protected abstract TextView createItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes);

    protected abstract void configLayoutParams(LayoutParams params);

    // ----- Public methods ---------------------------------------------------

    public void setOnCompoundClickListener(OnRightCompoundClickListener rightListener, OnLeftCompoundClickListener leftListener) {
        rightCompoundClickListener = rightListener;
        leftCompoundClickListener = leftListener;
    }

    public View getLeftButton() {
        return leftCompound;
    }

    public View getRightButton() {
        return rightCompound;
    }

    public TextView getItemView() {
        return itemView;
    }
}
