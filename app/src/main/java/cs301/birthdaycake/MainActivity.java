package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.graphics.Paint;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeView = (CakeView) findViewById(R.id.cakeview);
        CakeController cakeController = new CakeController(cakeView);

        Button blowOut = (Button) findViewById(R.id.blowButton);
        blowOut.setOnClickListener(cakeController);

        Switch candlesSwitch = (Switch) findViewById(R.id.candlesSwitch);
        candlesSwitch.setOnCheckedChangeListener(cakeController);

        SeekBar candleNum = (SeekBar) findViewById(R.id.candleNum);
        candleNum.setOnSeekBarChangeListener(cakeController);
    }

    public void goodbye(View button){
        Log.i("button", "Goodbye");
        finishAffinity();
    }
}
