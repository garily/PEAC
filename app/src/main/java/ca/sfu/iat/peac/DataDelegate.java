package ca.sfu.iat.peac;

import java.util.ArrayList;

public class DataDelegate {
    protected ArrayList<WavePowerRecord> alphaRecord;
    protected ArrayList<Long> timerRecordStart;
    protected ArrayList<Long> timerRecordStop;
    protected TestRecord baseTest1;

    protected void  saveBaseTest1() {
        baseTest1 = new TestRecord(alphaRecord, timerRecordStart, timerRecordStop);
    }
}
