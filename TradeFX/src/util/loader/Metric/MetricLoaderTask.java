package util.loader.Metric;

import java.util.ArrayList;

import controller.TradeFXBusinessController;
import javafx.concurrent.Task;
import model.HistData;
import model.Symbol;
import model.metrics.IMetric;
import util.properties.MyProperties;

public class MetricLoaderTask extends Task {
	
	TradeFXBusinessController controller;
	Symbol symbol;
	ArrayList<HistData> alHistData,alMetricData;
	IMetric iMetric;
	Boolean DEBUG;
	
	public MetricLoaderTask(Symbol symbol, IMetric iMetric) {
		controller=TradeFXBusinessController.getInstance();
		this.alHistData=null;
		this.symbol=symbol;
		this.iMetric=iMetric;
		DEBUG=MyProperties.getDebugSettingFor("MetricLoaderTaskDbg");
	}

	@Override
	protected Double call() throws Exception {
		
		this.alHistData=controller.getModel().StockHistData.get(this.symbol);
		if (DEBUG) System.out.println("Calculate "+iMetric.getClass().getSimpleName()+" for "+symbol.getName()+" in "+alHistData.size());
		alMetricData =iMetric.calc(alHistData);
		return alHistData.get(alHistData.size()-1).getOpen()/alMetricData.get(alMetricData.size()-2).getOpen() -1 ;
	}
	@Override
	protected void succeeded() {
		// TODO Auto-generated method stub
		if (DEBUG)  System.out.println("Calculate "+iMetric.getClass().getSimpleName()+" for "+symbol.getName()+" out "+alHistData.size());
		updateProgress(iMetric.getData().size(),alHistData.size());
		updateProgress(1,1);
		super.succeeded();
	}

}
