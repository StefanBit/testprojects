package view;

 

/** Data extra values for storing close, high and low. */

public class CandleStickExtraValues  {
    private double close;
    private double high;
    private double low;
    private double average;
 
    public CandleStickExtraValues(Object day, Object day2,
                                  Object day3, Object day4) {
        this.close = (double) day;
        this.high =(double) day2;
        this.low = (double)day3;
        this.average =(double) day4;
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