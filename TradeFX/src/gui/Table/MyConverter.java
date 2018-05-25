package gui.Table;

import javafx.util.StringConverter;

class MyConverter extends StringConverter {
	Class c;

	public MyConverter(Class c) {
		this.c = c;
	}

	// Translates Cells String Value to desired Field Type
	@Override
	public Object fromString(String arg0) {
		Object o = null;
		System.out.println("Translates Cells String Value to desired Field Type");
		System.out.println(c);
		if (c.equals("class java.lang.String")) {
			o = arg0;
		}
		if (c.equals("class java.lang.Integer")) {
			o = Integer.parseInt(arg0);
		}
		if (c.equals(String.class)) {
			o = arg0;
		}
		if (c.equals(int.class)) {
			o = Integer.parseInt(arg0);
		}
		if (c.equals(Integer.class)) {
			o = Integer.parseInt(arg0);
		}
		if (c.equals(double.class)) {
			o = Double.parseDouble(arg0);
		}
		return o;
	}

	@Override
	public String toString(Object arg0) {
		return String.valueOf(arg0);

	}
}