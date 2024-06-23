package APUbus;


public class Tickets 
{
    int ticketNo;
    String destination; //Given destinations are (West, South, East)
    boolean scanned;
    boolean inspected;
    
    public Tickets(int ticketNo, String destination)
    {
        this.ticketNo=ticketNo;
        this.destination=destination;
    }
    
    
    public void TicketInspection()
    {
        inspected=true;
    }
        
    public void TicketScanning()
    {
        scanned=true;
    }
    
}
