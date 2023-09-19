
public class ServiceData {

	private String name;
	private String value;

	private String user_access_level; // This is the minimum level of access required to see the value of a specific data object.
	private String group; // This is the group of service configuration objects to which this ServiceData objects can be read by. 
	
	
	public void setAccessLevel(String access_level)
	{
		this.user_access_level = access_level;
	}
	public String getAccessLevel()
	{
		return user_access_level;
	}
	
	public void setGroup(String group_id)
	{
		this.group = group_id;
	}
	public String getGroup()
	{
		return this.group;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	
		
	}
	
	

