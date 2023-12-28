package pay.point.sample;

public class Computer {

	// The purpose of this class is to listen to manage local computer interactions.
	// This is to make the computer do commands at will of the user without having to type into the program.
	// Similar to Star Trek computer.
	
	private String name;
	
	public Computer() { 
	
		name = "";
	}
	public void setDefaultValues() {
		name = "Computer";
	}
	
	public void setName(String name) { 
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
