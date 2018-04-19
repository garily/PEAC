package ca.sfu.iat.peac;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.Date;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ImageButton imgBtnBaselineDone1 = getActivity().findViewById(R.id.imgBtnBaselineDone1);

        if (MainActivity.dataDelegate.baseTest1 != null) {
            imgBtnBaselineDone1.setVisibility(View.VISIBLE);
            imgBtnBaselineDone1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction()
                            .addToBackStack("home")
                            .replace(R.id.fgMain, new TestResultFragment())
                            .commit();
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ImageButton imgBtnBaselineDone1 = getActivity().findViewById(R.id.imgBtnBaselineDone1);

        if (MainActivity.dataDelegate.baseTest1 != null) {
            imgBtnBaselineDone1.setVisibility(View.VISIBLE);
            imgBtnBaselineDone1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction()
                            .addToBackStack("home")
                            .replace(R.id.fgMain, new TestResultFragment())
                            .commit();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView tvCredits = getActivity().findViewById(R.id.tvCredits);

        ImageButton btnStartTest = getActivity().findViewById(R.id.imgBtnStartSession);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle testInfo = new Bundle();
                testInfo.putLong("date_n_time", new Date().getTime());
                testInfo.putInt("type", TestFragment.BASELINE);

                getFragmentManager().beginTransaction()
                        .addToBackStack("home")
                        .replace(R.id.fgMain, new TestFragment())
                        .commit();
            }
        });

        ImageButton imgBtnInfo = getActivity().findViewById(R.id.imgBtnInfo);
        imgBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvCredits.getVisibility() == View.VISIBLE) {
                    tvCredits.setVisibility(View.GONE);
                    }
                    else {
                    tvCredits.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
