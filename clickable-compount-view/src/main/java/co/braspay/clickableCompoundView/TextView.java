package co.braspay.clickableCompoundView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TextView extends BaseView {

    public TextView(Context context) {
        super(context);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected android.widget.TextView createItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        return new AppCompatTextView(context, attrs);
    }

    @Override
    protected void configLayoutParams(RelativeLayout.LayoutParams params) {
        params.addRule(RelativeLayout.CENTER_VERTICAL);
    }
}
