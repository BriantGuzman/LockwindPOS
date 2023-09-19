
// The modules that are loaded by the system will all extend the service class which allows them to be deployed as a service.


public class Service {

	
	private String id;
	private String name;
	
	
	public Service() {
		
		id = "";
		name = "";
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setID(String id)
	{
		this.id = id;
	}
	public String getID()
	{
		return this.id;
	}
	
	
	
}
