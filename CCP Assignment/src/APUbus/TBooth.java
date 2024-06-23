package APUbus;

import java.util.concurrent.Semaphore;

public class TBooth 
{
    Semaphore tcSem=new Semaphore(1,true);
    Tickets t;
    String ticketBoothName; //The name of the TB is TBooth A or B
    boolean available; //To check if staff member is on toilet break or not.
    boolean servingCustomer; //To check if the counter is used or not, so that the Staff Member can go to the toilet
    TNoGenerator tng;
    
    public TBooth(TNoGenerator tng, String ticketBoothName)
    {
        this.tng=tng;
        this.ticketBoothName=ticketBoothName;
        available=true;
    }
    //TC = Ticket Counter
    
    
    public Tickets TicketCollect(Customers c, String destination)
    {
        if(tcSem.availablePermits()==0)
        {
            System.out.println("TB "+ticketBoothName+" is now occupied... Customer "+c.custID+" is queuing..");
        }
        try
        {
            tcSem.acquire();
        }
        catch(Exception e){}
        if(available==false)
        {
            System.out.println("Customer "+c.custID+" is exiting the queue at TB "+ticketBoothName);
            tcSem.release();
            return null;
        }
        else
        {
            servingCustomer=true;
            System.out.println("Customer "+c.custID+" is at the TB "+ticketBoothName);
            try
            {
                Thread.sleep(3000);
            }
            catch(Exception e){}
            int ticketNo=tng.CollectTicketNumber();
            t=new Tickets(ticketNo,destination);
            System.out.println("Customer "+c.custID+" has received the ticket: "+t.ticketNo+" ("+t.destination+")");
            System.out.println("Customer "+c.custID+" has left the TB "+ticketBoothName);
            servingCustomer=false;
            tcSem.release();
            return t;
        }
        
    }
    
    
    public void ToiletBreak()
    {
        available=false;
        System.out.println("\t\tTB staff "+ticketBoothName+" is now going to the toilet");
        System.out.println("\t\tTB "+ticketBoothName+" is now closed");
    }
    
        
    public void ToiletBreakEnd()
    {
        System.out.println("\t\tTB staff "+ticketBoothName+" has returned.");
        available=true;
        System.out.println("\t\tTB "+ticketBoothName+" is now opened.");
    }
    
}
