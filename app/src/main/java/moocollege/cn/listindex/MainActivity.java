package moocollege.cn.listindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SliderBar.LetterTouchListener {

    private SliderBar mSliderBar;
    private TextView mShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSliderBar = (SliderBar) findViewById(R.id.slide_bar);
        mShowText = (TextView) findViewById(R.id.dialog);
        mSliderBar.setOnLetterTouchListener(this);
    }

    @Override
    public void touch(String letter, boolean isTouch) {
        if (isTouch) {
            mShowText.setVisibility(View.VISIBLE);
            mShowText.setText(letter);
        } else {
            mShowText.setVisibility(View.GONE);
        }
    }
}
