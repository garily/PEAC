package ca.sfu.iat.peac;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {
    public static final int BASELINE = 0;
    public static final int AFTERDOSE = 1;


    private boolean ifStop = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TEST_TYPE = "test_type";
    private static final String TEST_TIME = "test_time";

    private Thread timerThread;

    private int cycleCount;
    private TextView tvTimer;
    private boolean ifFgmainClickable;

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    // TODO: eeg buffer and time interval buffer
    private int testType;
    private int testTime;
    //private eegbuffer

    private OnFragmentInteractionListener mListener;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param testType Parameter 1.
     * @param testTime Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(int testType, String testTime) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt(TEST_TYPE, testType);
        args.putString(TEST_TIME, testTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            testType = getArguments().getInt(TEST_TYPE);
            testTime = getArguments().getInt(TEST_TIME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cycleCount = 10;
        tvTimer = getActivity().findViewById(R.id.tvTimer);
        Button btnStartTest = getActivity().findViewById(R.id.btnStartTest);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                startTest();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void triggerTestAction(int action) {
        if (mListener != null) {
            mListener.onTestAction(action);
        }
    }

    @TargetApi(26)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Toast.makeText(getActivity(), context.toString(), Toast.LENGTH_SHORT).show();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Toast.makeText(getActivity(), activity.toString(), Toast.LENGTH_SHORT).show();
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (timerThread != null) {
            timerThread.interrupt();
            mListener.onTestAction(MainActivity.STOP_RECORDING);
        }
        mListener.onTestAction(MainActivity.STOP_RECORDING);
        mListener = null;
        getActivity().findViewById(R.id.fgMain).setOnClickListener(null);
    }

    private void startTest() {

        final Button btnQuitTest = getActivity().findViewById(R.id.btnQuitTest);
        btnQuitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.ifBaseline1 = true;
                getFragmentManager().popBackStack();
                view.setOnClickListener(null);
            }
        });


        mListener.onTestAction(MainActivity.START_RECORDING);

        timerThread = newTimerThread();
        timerThread.start();

        getActivity().findViewById(R.id.fgMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifFgmainClickable) {
                    ifFgmainClickable = false;
                    cycleCount--;
                    timerThread.interrupt();
                    tvTimer.setText("");
                    if (cycleCount == 0) {
                        mListener.onTestAction(MainActivity.STOP_RECORDING);
                        btnQuitTest.setVisibility(View.VISIBLE);
                        view.setOnClickListener(null);
                    }
                    else {
                        timerThread = newTimerThread();
                        timerThread.start();
                    }
                }
            }

        });
    }

    private Thread newTimerThread() {
        return new Thread() {
            @Override
            public void run() {
                try {
                    if (cycleCount != 10) {
                        Thread.sleep(1000);
                    }
                    Thread.sleep((long) (Math.random() * 5000));
                    ifFgmainClickable = true;
                    final long startTime = System.currentTimeMillis();
                    while (!isInterrupted()) {
                        Thread.sleep(50);
                         getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                tvTimer.setText(String.format("%.3f" ,(System.currentTimeMillis() - startTime)/1000f));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
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
        void onTestAction(int action);
    }
}
