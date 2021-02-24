package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private CakeView cakeView;
    private CakeModel cakeModel;

    public CakeController(CakeView cakeView) {
        this.cakeView = cakeView;
        this.cakeModel = cakeView.getCakeModel();
        cakeView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
//        Log.d("CakeController", "The implemented method was called!");
        //need to change text of the button
        cakeModel.litCandles = !cakeModel.litCandles;
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cakeModel.hasCandles = !cakeModel.hasCandles;
        cakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        cakeModel.numCandles = progress;
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        cakeView.x = (int) motionEvent.getX();
        cakeView.y = (int) motionEvent.getY();
        cakeView.invalidate();
        return false;
    }
}
