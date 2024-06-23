package APUbus;

public class TNoGenerator 
{
    private static int ticketOrder=0;
    
    public synchronized int CollectTicketNumber()
    {
        //The first return ticket number is 1 (0++=1)
        ticketOrder++;
        return ticketOrder;
    }
}
