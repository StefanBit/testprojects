package loader;
import java.util.Date;
import java.util.Random;

import model.Symbol;

public class RandomLoader implements Loader {

	Data d;

	public RandomLoader() {
		
	}

	@Override
	public Data load(Symbol s, Date firstDate, Date lastDate) {
		
		d = new Data();
		d.setName(s.getName());
		d.setFirstDate(firstDate);
		d.setLastDate(lastDate);
		double value;
		for (Date date = (Date) firstDate.clone(); date.compareTo(lastDate) <= 0; date.setTime(date.getTime() + 1 * 24 * 60 * 60 * 1000)) {
			value= new Random().nextDouble();
			if (d.lDaySet.size()>0){
				value=value-0.5+d.lDaySet.get(d.lDaySet.size()-1).close;	
			}
			
			ds = new DaySet((Date) date.clone(), value, value, value,value, value);
			d.addSet(ds);
		}
		return d;
	}

}
