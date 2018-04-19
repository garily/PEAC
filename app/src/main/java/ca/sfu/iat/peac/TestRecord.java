package ca.sfu.iat.peac;

import java.util.ArrayList;

public class TestRecord {
    private ArrayList<WavePowerRecord> alphaRecord;
    private ArrayList<Long> timerRecordStart;
    private ArrayList<Long> timerRecordEnd;
    public TestRecord(ArrayList<WavePowerRecord> alphaRecord, ArrayList<Long> timerRecordStart, ArrayList<Long> timerRecordEnd) {
        this.alphaRecord = alphaRecord;
        this.timerRecordStart = timerRecordStart;
        this.timerRecordEnd = timerRecordEnd;
    }

    public ArrayList<WavePowerRecord> getAlphaRecord() {
        return alphaRecord;
    }

    public ArrayList<Long> getTimerRecordStart() {
        return timerRecordStart;
    }

    public ArrayList<Long> getTimerRecordEnd() {
        return timerRecordEnd;
    }

}
