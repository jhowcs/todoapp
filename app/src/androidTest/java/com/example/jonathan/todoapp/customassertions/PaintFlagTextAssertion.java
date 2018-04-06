package com.example.jonathan.todoapp.customassertions;

import android.graphics.Paint;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

public class PaintFlagTextAssertion implements ViewAssertion {
    private static final int DEFAULT_PAINT_FLAG = 1;
    private static final int HIDDEN_DEFAULT_PAINT_FLAGS = 1280;

    private final int paintFlags;

    public static PaintFlagTextAssertion withStrikeThroughFlag() {
        final int flags = DEFAULT_PAINT_FLAG | HIDDEN_DEFAULT_PAINT_FLAGS | Paint.STRIKE_THRU_TEXT_FLAG;
        return new PaintFlagTextAssertion(flags);
    }

    public static PaintFlagTextAssertion withLinearFlag() {
        return new PaintFlagTextAssertion(Paint.LINEAR_TEXT_FLAG);
    }

    private PaintFlagTextAssertion(int paintFlags) {
        this.paintFlags = paintFlags;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }
        TextView textView = (TextView) view;
        Matcher<Integer> matcher = CoreMatchers.is(paintFlags);
        assertThat(textView.getPaintFlags(), matcher);
    }
}
