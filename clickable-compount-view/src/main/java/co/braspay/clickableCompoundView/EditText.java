package co.braspay.clickableCompoundView;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class EditText extends TextView {

    public EditText(Context context) {
        super(context);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected android.widget.TextView createItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        return new AppCompatEditText(context, attrs);
    }

    @Override
    protected void configLayoutParams(RelativeLayout.LayoutParams params) {

    }
}
