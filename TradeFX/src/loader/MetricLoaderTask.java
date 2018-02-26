package loader;

import java.util.ArrayList;

import controller.TradeFXBusinessController;
import javafx.concurrent.Task;
import model.HistData;
import model.Symbol;
import model.metrics.IMetric;

public class MetricLoaderTask extends Task {
	
	TradeFXBusinessController controller;
	Symbol symbol;
	ArrayList<HistData> alHistData;
	IMetric iMetric;
	
	public MetricLoaderTask(Symbol symbol, IMetric iMetric) {
		controller=TradeFXBusinessController.getInstance();
		this.symbol=symbol;
		this.iMetric=iMetric;
	}

	@Override
	protected Double call() throws Exception {
		alHistData = controller.getModel().StockHistData.get(this.symbol);
		alHistData =iMetric.calc(alHistData);
		updateProgress(1, 1);
		return alHistData.get(0).open;
	}

}
