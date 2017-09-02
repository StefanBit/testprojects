package application;
import java.util.Date;

import model.Symbol;

public interface Loader {
	public Data load(Symbol s, Date firstDate, Date lastDate);
}
