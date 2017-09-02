package application;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class Console extends ScrollPane {

	public Console() {
		super();
		TextArea ta = new TextArea();
		PrintStream ps = new PrintStream(new MyOutputStream(ta));
		System.setOut(ps);
		System.setErr(ps);
		this.setContent(ta);
	}

}

class MyOutputStream extends OutputStream {
	TextArea ta;

	public MyOutputStream(TextArea ta) {
		this.ta = ta;
	}

	@Override
	public synchronized void write(int b) throws IOException {
		String t = String.valueOf((char) b);
		ta.appendText(t);
	}
}