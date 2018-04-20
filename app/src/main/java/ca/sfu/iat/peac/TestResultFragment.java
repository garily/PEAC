package ca.sfu.iat.peac;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TestResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestResultFragment newInstance(String param1, String param2) {
        TestResultFragment fragment = new TestResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_result, container, false);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphView gvResultGraph1 = getActivity().findViewById(R.id.gvResultGraph1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(prepareSeries(MainActivity.dataDelegate.baseTest1));
        series.setTitle("Alpha Relative");

        gvResultGraph1.setTitle("Baseline Session 1 Result");

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(gvResultGraph1);
        staticLabelsFormatter.setHorizontalLabels(new String[] {
                Double.toString(series.getLowestValueX()),
                Double.toString(series.getHighestValueX())
        });

        gvResultGraph1.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        gvResultGraph1.addSeries(series);

        TextView tvAvgResponseTime = getActivity().findViewById(R.id.tvAvgResponseTime);
        tvAvgResponseTime.setText(String.format("%6.2f", avgResponseTime(MainActivity.dataDelegate.responseTimeRecord)));
    }

    private double avgResponseTime(ArrayList<Double> responseTimes) {
        double sum = 0.0;
        for (double i:responseTimes) {
            sum += i;
        }
        return sum / responseTimes.size();
    }

    private DataPoint[] prepareSeries(TestRecord testRecord) {
        ArrayList<WavePowerRecord> wavePowerRecords = testRecord.getAlphaRecord();
        DataPoint[] dataPoints = new DataPoint[wavePowerRecords.size()];
        for (int i = 0; i < testRecord.getAlphaRecord().size(); i ++) {
            dataPoints[i] = new DataPoint(
                    (wavePowerRecords.get(i).getTimeStamp() - wavePowerRecords.get(0).getTimeStamp()) / 1000.0,
                    wavePowerRecords.get(i).getRecord()
                );
            Log.d("dataPoint[" + i + "] = ", "[" + dataPoints[i].getX() + ", " + dataPoints[i].getY() + "]");
        }
        return dataPoints;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
