package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
 
/**
 * A candlestick chart is a style of bar-chart used primarily to describe
 * price movements of a security, derivative, or currency over time.
 *
 * The Data Y value is used for the opening price and then the
 * close, high and low values are stored in the Data's
 * extra value property using a CandleStickExtraValues object.
 */
public class CandleStickChart extends XYChart<String, Number> {
 
    /**
     * Construct a new CandleStickChart with the given axis.
     */
    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        final String candleStickChartCss =
            getClass().getResource("CandleStickChart.css").toExternalForm();
        getStylesheets().add(candleStickChartCss);
        setAnimated(false);
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        
    }
 
    /**
     * Construct a new CandleStickChart with the given axis and data.
     *             reflected in the chart.
     */
    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis,
                            ObservableList<Series<String, Number>> data) {
        this(xAxis, yAxis);
        setData(data);
    }
 
    /** Called to update and layout the content for the plot */
    @Override protected void layoutPlotChildren() {
        // we have nothing to layout if no data is present
        if (getData() == null) {
            return;
        }
        // update candle positions
        for (int index = 0; index < getData().size(); index++) {
            Series<String, Number> series = getData().get(index);
            Iterator<XYChart.Data<String, Number>> iter = getDisplayedDataIterator(series);
            Path seriesPath = null;
            if (series.getNode() instanceof Path) {
                seriesPath = (Path) series.getNode();
                seriesPath.getElements().clear();
            }
            while (iter.hasNext()) {
                Axis<Number> yAxis = getYAxis();
                XYChart.Data<String, Number> item = iter.next();
                String X = getCurrentDisplayedXValue(item);
                Number Y = getCurrentDisplayedYValue(item);
                double x = getXAxis().getDisplayPosition(X);
                double y = getYAxis().getDisplayPosition(Y);
                Node itemNode = item.getNode();
                CandleStickExtraValues extra =
                    (CandleStickExtraValues)item.getExtraValue();
                if (itemNode instanceof Candle && extra != null) {
                    double close = yAxis.getDisplayPosition(extra.getClose());
                    double high = yAxis.getDisplayPosition(extra.getHigh());
                    double low = yAxis.getDisplayPosition(extra.getLow());
                    // calculate candle width
                    double candleWidth = -1;
                    if (getXAxis() instanceof ValueAxis) {
                         // use 90% width between ticks
                        ValueAxis xa = (ValueAxis) getXAxis();
                        double unit = xa.getDisplayPosition(xa.getTickLength());
                        candleWidth = unit * 0.90;
                    }
                    // update candle
                    Candle candle = (Candle)itemNode;
                    candle.update(close - y, high - y, low - y, candleWidth);
                    candle.updateTooltip(item.getYValue().doubleValue(),
                                         extra.getClose(), extra.getHigh(),
                                         extra.getLow());
 
                    // position the candle
                    candle.setLayoutX(x);
                    candle.setLayoutY(y);
                }
                if (seriesPath != null) {
                    double ave = yAxis.getDisplayPosition(extra.getAverage());
                    if (seriesPath.getElements().isEmpty()) {
                        seriesPath.getElements().add(new MoveTo(x, ave));

                   } else {
                        seriesPath.getElements().add(new LineTo(x, ave));
                    }
                }
            }
        }
    }
 
    @Override protected void dataItemChanged(Data<String, Number> item) {
    }
 

   @Override protected void dataItemAdded(Series<String, Number> series,
                                           int itemIndex,
                                           Data<String, Number> item) {
        Node candle = createCandle(getData().indexOf(series), item, itemIndex);
        if (shouldAnimate()) {
            candle.setOpacity(0);
            getPlotChildren().add(candle);
            // fade in new candle
            final FadeTransition ft =
                new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(1);
            ft.play();
        } else {
            getPlotChildren().add(candle);
        }
        // always draw average line on top
        if (series.getNode() != null) {

           series.getNode().toFront();
        }
    }
 
    @Override protected void dataItemRemoved(Data<String, Number> item,
                                             Series<String, Number> series) {
        final Node candle = item.getNode();
        if (shouldAnimate()) {

           // fade out old candle
            final FadeTransition ft =
                new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(0);
            ft.setOnFinished((ActionEvent actionEvent) -> {
                getPlotChildren().remove(candle);
            });
            ft.play();
        } else {
            getPlotChildren().remove(candle);
        }
    }
 
    @Override protected void seriesAdded(Series<String, Number> series,
                                         int seriesIndex) {
        // handle any data already in series
        for (int j = 0; j < series.getData().size(); j++) {

           XYChart.Data item = series.getData().get(j);
            Node candle = createCandle(seriesIndex, item, j);
            if (shouldAnimate()) {
                candle.setOpacity(0);
                getPlotChildren().add(candle);
                // fade in new candle
                final FadeTransition ft =
                    new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(1);
                ft.play();

           } else {
                getPlotChildren().add(candle);
            }

       }
        // create series path
        Path seriesPath = new Path();
        seriesPath.getStyleClass().setAll("candlestick-average-line","series" + seriesIndex);
        series.setNode(seriesPath);
        getPlotChildren().add(seriesPath);
    }


    @Override protected void seriesRemoved(Series<String, Number> series) {
        // remove all candle nodes

       for (XYChart.Data<String, Number> d : series.getData()) {
            final Node candle = d.getNode();
            if (shouldAnimate()) {
                // fade out old candle

               final FadeTransition ft =
                    new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(0);
                ft.setOnFinished((ActionEvent actionEvent) -> {
                    getPlotChildren().remove(candle);
                });
                ft.play();

           } else {
                getPlotChildren().remove(candle);
            }

       }
    }


    /**
     * Create a new Candle node to represent a single data item

    */
    private Node createCandle(int seriesIndex, final XYChart.Data item,
                              int itemIndex) {

       Node candle = item.getNode();
        // check if candle has already been created
        if (candle instanceof Candle) {

           ((Candle)candle).setSeriesAndDataStyleClasses("series" + seriesIndex,
                                                          "data" + itemIndex);
        } else {

           candle = new Candle("series" + seriesIndex, "data" + itemIndex);
            item.setNode(candle);

       }
        return candle;

   }
 
    /**
     * This is called when the range has been invalidated and we need to
     * update it. If the axis are auto ranging then we compile a list of

    * all data that the given axis has to plot and call invalidateRange()
     * on the axis passing it that data.

    */
    @Override
    protected void updateAxisRange() {
        // For candle stick chart we need to override this method as we need

       // to let the axis know that they need to be able to cover the area
        // occupied by the high to low range not just its center data value.
        final Axis<String> xa = getXAxis();

       final Axis<Number> ya = getYAxis();
        List<String> xData = null;
        List<Number> yData = null;
        if (xa.isAutoRanging()) {
            xData = new ArrayList<String>();

       }
        if (ya.isAutoRanging()) {
            yData = new ArrayList<Number>();

       }
        if (xData != null || yData != null) {
            for (XYChart.Series<String, Number> series : getData()) {

               for (XYChart.Data<String, Number> data : series.getData()) {
                    if (xData != null) {
                        xData.add(data.getXValue());
                    }
                    if (yData != null) {
                        CandleStickExtraValues extras =

                           (CandleStickExtraValues)data.getExtraValue();
                        if (extras != null) {
                            yData.add(extras.getHigh());
                            yData.add(extras.getLow());
                        } else {
                            yData.add(data.getYValue());
                        }

                   }
                }
            }
            if (xData != null) {
                xa.invalidateRange(xData);
            }

           if (yData != null) {
                ya.invalidateRange(yData);
            }
        }
    }
}