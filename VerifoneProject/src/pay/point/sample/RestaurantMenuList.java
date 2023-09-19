
package pay.point.sample;public class RestaurantMenuList {
    

    private RestaurantMenuItem head;


    public RestaurantMenuList()
    {

    }
    public void add( RestaurantMenuItem item)
    {
        RestaurantMenuItem it = null;

        it = head;
        if(head == null) {
            head = item;
        }
        else{
            while(it.getNext() != null)
            {
                it = it.getNext();
            }
            it.setNext(head);
        }


    }
    public void remove(){

    }
}
