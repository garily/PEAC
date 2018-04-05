package ca.sfu.iat.peac;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Date;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }

}
