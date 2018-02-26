package loader;

import java.util.ArrayList;
import java.util.Map;

import controller.TradeFXBusinessController;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.IMetric;

public class SymbolMetric {
	TradeFXModel model;
	public static Map<Symbol, ArrayList<IMetric>> mSymbolMetrics;
	IMetric[] metrics;

	public SymbolMetric() {
	}

	public void build() {
		model = TradeFXBusinessController.getInstance().getModel();
		ArrayList<IMetric> aMetrics;
		for (Symbol symbol : model.getStockSymbols()) {
			aMetrics = new ArrayList<IMetric>();
			for (IMetric metric : metrics) {
				aMetrics.add(metric.getInstance());
			}
			model.aMetrics=aMetrics;
			//model.mSymbolMetrics.put(symbol, aMetrics);
		}
	}

	public void registerMetricClasses(IMetric... aMetricClass) {
		metrics = aMetricClass;
	}

}
