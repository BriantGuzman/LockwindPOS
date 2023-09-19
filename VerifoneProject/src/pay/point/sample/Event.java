package pay.point.sample;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private List<Event> links;
    
    public Event(String eventName) {
        this.eventName = eventName;
        this.links = new ArrayList<Event>();
    }
    
    public void addLink(Event Event) {
        this.links.add(Event);
        Event.links.add(this);
    }
    
    public List<Event> getLinks() {
        return links;
    }
    
    public String toString() {
        return eventName;
    }
    
    public static void main(String[] args) {
        // Create a set of Events representing real-world events
        Event event1 = new Event("Register Started");
        Event event2 = new Event("Received Invoice Number");
        Event event3 = new Event("Register Transaction Complete");
        
        // Link the Events together in a fully meshed structure
        event1.addLink(event2);
        event1.addLink(event3);
        event2.addLink(event3);
        
        // Print out the links for each Event in the network
        System.out.println("Links for " + event1 + ": " + event1.getLinks());
        System.out.println();
        System.out.println("Links for " + event2 + ": " + event2.getLinks());
        System.out.println();
        System.out.println("Links for " + event3 + ": " + event3.getLinks());
    }
}
