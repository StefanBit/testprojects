package controller.state;

public interface IState {

	void loadSymbols();
	void nextState();
	public void registerObserver(Object o);
}
