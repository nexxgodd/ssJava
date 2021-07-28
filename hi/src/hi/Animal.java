package hi;

public abstract class Animal {
	int mass;
	String name;

	public int getMass() {
		return mass;

	}

	public abstract void communicate();
}

class Wolf extends Animal{
	public void communicate() {
		
	}
}