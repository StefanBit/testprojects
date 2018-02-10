package gui.view;

 

/** Data extra values for storing close, high and low. */

public class CandleStickExtraValues  {
    private double close;
    private double high;
    private double low;
    private double average;
 
    public CandleStickExtraValues(Object close, Object high, Object low, Object average) {
        this.high = (double) high;
        this.low =(double) low;
        this.close = (double) close;
        this.average =(double) average;
    }
 
    public double getClose() {
        return close;
    }
 
    public double getHigh() {
        return high;
    }
 
    public double getLow() {
        return low;
    }
 
    public double getAverage() {
        return average;
    }
 
    private static final String FORMAT =
        "CandleStickExtraValues{close=%f, high=%f, low=%f, average=%f}";
 
    @Override
    public String toString() {
        return String.format(FORMAT, close, high, low, average);
    }
}