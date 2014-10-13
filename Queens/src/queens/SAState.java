package queens;

public interface SAState {
	SAState randomSuccessor();
	double value();
	boolean isPerfect();
}
