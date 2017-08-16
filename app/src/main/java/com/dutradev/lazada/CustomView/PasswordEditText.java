package com.dutradev.lazada.CustomView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.dutradev.lazada.R;

/**
 * Created by dutradev on 15/08/2017.
 */

@SuppressLint("AppCompatCustomView")
public class PasswordEditText extends EditText {

    Drawable eye,eyeStrike;
    Boolean visible = false;
    Boolean useStrike = false;
    Drawable drawable;
    int ALPHA = (int) (255 * .70f);

    public PasswordEditText(Context context) {
        super(context);
        Create(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Create(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Create(attrs);
    }

    private void Create(AttributeSet attrs){

        if(attrs != null){
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText,0,0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike,false);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black_24dp).mutate();

        SetUp();
    }


    private void SetUp(){
        setInputType(InputType.TYPE_CLASS_TEXT |(visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        drawable = useStrike && !visible? eyeStrike : eye;
        drawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP && event.getX() >= (getRight() - drawable.getBounds().width()) ){
            visible = !visible;
            SetUp();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
