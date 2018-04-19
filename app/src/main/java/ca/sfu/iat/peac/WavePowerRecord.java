package ca.sfu.iat.peac;

public class WavePowerRecord {
    private double record;
    private long timeStamp;
    public WavePowerRecord(double record, long timeStamp) {
        this.record = record;
        this.timeStamp = timeStamp;
    }
    @Override
    public String toString () {
        return "alphaValue = " + record + ", timeStamp = " + timeStamp;
    }

    public double getRecord() {
        return record;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
