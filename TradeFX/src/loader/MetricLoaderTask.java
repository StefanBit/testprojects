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
		this.alHistData=null;
		this.symbol=symbol;
		this.iMetric=iMetric;
	}

	@Override
	protected Double call() throws Exception {
		this.alHistData=controller.getModel().StockHistData.get(this.symbol);;
		System.out.println("Calculate "+iMetric.getClass().getSimpleName()+" for "+symbol.getName()+" in "+alHistData.size());
		alHistData =iMetric.calc(alHistData);
		return alHistData.get(0).open;
	}
	@Override
	protected void succeeded() {
		// TODO Auto-generated method stub
		System.out.println("Calculate "+iMetric.getClass().getSimpleName()+" for "+symbol.getName()+" out "+alHistData.size());
		updateProgress(iMetric.getData().size(),alHistData.size());
		super.succeeded();
	}

}
