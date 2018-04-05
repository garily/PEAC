package ca.sfu.iat.peac;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private boolean ifDisclaimerAgreed;
    private RelativeLayout fgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Retrieve previous data
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ifDisclaimerAgreed = prefs.getBoolean("ifDisclaimerAgreed", false);

        fgMain = (RelativeLayout) findViewById(R.id.fgMain);
        fgMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareHome(ifDisclaimerAgreed);
            }
        });

        if (!prefs.getBoolean("ifBaseline", false)) {
            //disable new AfterDose session
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void prepareHome(boolean ifDisclaimerAgreed) {
        fgMain.removeAllViews();
        if (!ifDisclaimerAgreed) {
            showDislcaimer();
        }
        else setMainFragment(R.id.fgMain);
    }

    private void showDislcaimer() {
        fgMain.setGravity(Gravity.CENTER);

        LinearLayoutCompat ll = new LinearLayoutCompat(this);

        TextView tvDisclaimer = new TextView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        tvDisclaimer.setLayoutParams(lp);
        tvDisclaimer.setMaxWidth(1100);
        tvDisclaimer.setMaxHeight(1100);
        tvDisclaimer.setTextColor(Color.rgb(0, 0, 0));
        tvDisclaimer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        tvDisclaimer.setGravity(Gravity.TOP);
        tvDisclaimer.setText(R.string.strDisclaimer);

        Button btnDisagree = new Button(this);
        Button btnAgree = new Button(this);

        btnAgree.setText(R.string.strAgree);
        btnDisagree.setText(R.string.strDisagree);

        btnAgree.setLayoutParams(lp);
        btnDisagree.setLayoutParams(lp);


        ll.setOrientation(LinearLayoutCompat.VERTICAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.addView(tvDisclaimer);
        ll.addView(btnAgree);
        ll.addView(btnDisagree);

        fgMain.addView(ll);

        fgMain.setOnClickListener(null);

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifDisclaimerAgreed = true;

                setMainFragment(R.id.fgMain);
            }
        });

        btnDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setMainFragment(int fragmentId) {
        fgMain.removeAllViews();

        FragmentManager fragmentManager = getFragmentManager();


        fragmentManager
                .beginTransaction()
                .replace(fragmentId, new HomeFragment())
                .commit();
    }

}
