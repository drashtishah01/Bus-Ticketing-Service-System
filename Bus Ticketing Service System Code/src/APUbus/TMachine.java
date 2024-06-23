package APUbus;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class TMachine 
{
    Semaphore tmSem=new Semaphore(1,true);
    Tickets t;
    boolean available;
    TNoGenerator tng;
    
    public TMachine(TNoGenerator tng)
    {
        this.tng=tng;
        available=true;
    }
    //TM = Ticket Machine
    public void BreakDown()
    {
        available=false;
        System.out.println("\t\tTM has a breakdown so it cannot be used...");
    }
    
    public void Repairing()
    {
        System.out.println("\t\tTM is now repaired and can be used...");  
        available=true;
    }
    
    
    public Tickets TicketCollect(Customers c, String destination)
    {
        if(tmSem.availablePermits()==0)
        {
            System.out.println("TM is now in-use... Customer "+c.custID+" is queuing for the TM...");
        }
        try
        {
            tmSem.acquire();
        }
        catch(Exception e){}
        if(available==false)
        {
            System.out.println("Customer "+c.custID+" is exiting the queue at TM");
            tmSem.release();
            return null;
        }
        else
        {
            System.out.println("Customer "+c.custID+" is now using the TM");
            try
            {
                Thread.sleep(500);
            }
            catch(Exception e){}
            //The Tickets Machine can only be broken when a Customers is using it
            int rand=new Random().nextInt(10); //Probability of Tickets machine breaking is 10%
            if(rand==0)
            {
                BreakDown();
                System.out.println("Customer "+c.custID+" has not yet receive any Ticket!");
                System.out.println("Customer "+c.custID+" is leaving the TM");
                tmSem.release();
                return null;
            }
            try
            {
                Thread.sleep(500);
            }
            catch(Exception e){}
            int ticketNo=tng.CollectTicketNumber();
            t=new Tickets(ticketNo, destination);
            System.out.println("TM has print ticket: "+t.ticketNo+" (Destination: "+t.destination+")");
            System.out.println("Customer "+c.custID+" has left the TM");
            tmSem.release();
            return t;
        }
    }
    
}
