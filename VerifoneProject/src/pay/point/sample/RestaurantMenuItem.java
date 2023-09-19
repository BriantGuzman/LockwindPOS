package pay.point.sample;


public class RestaurantMenuItem {

    private String name;
    private double retail_price;

    private RestaurantMenuItem next;
    
    public RestaurantMenuItem(){

    }

    public void setNext( RestaurantMenuItem item)
    {
        if(next == null)
        {
            next = item;
        }
        else{
            System.out.println("Error: next is already taken: ");

        }
    }
    public RestaurantMenuItem getNext()
    {
        return next;
    }
    public void remove(){

    }

}
