package APUbus;

import java.util.Random;

public class Customers implements Runnable
{
    int custID;
    Tickets t=null;
    String destination;
    TBooth tcA;
    TBooth tcB;
    TMachine tm;
    
    public Customers(int custID, TBooth tcA, TBooth tcB, TMachine tm)
    {
        this.custID=custID;
        this.tcA=tcA;
        this.tcB=tcB;
        this.tm=tm;
    }
    
    @Override
    public void run()
    {
        System.out.println("\nNEW CUSTOMER "+custID+" HAS ARRIVED TO THE BUS TERMINAL\n");

        int rand=new Random().nextInt(3); //Customers can choose their destination over here
        if(rand==0)
        {
            destination="West";
        }
        else if(rand==1)
        {
            destination="South";
        }
        else
        {
            destination="East";
        }
        
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e){}

        while(t==null) 
        {
            rand=new Random().nextInt(3); 
            if(rand==0) //Entering TM
            {
                if(tm.available==true)
                {
                    t=tm.TicketCollect(this, destination);
                }
            }
            else if(rand==1) //Entering TC A
            {
                if(tcA.available==true)
                {
                    t=tcA.TicketCollect(this, destination);
                }
            }
            else if(rand==2) //Entering TC B
            {
                if(tcB.available==true)
                {
                    t=tcB.TicketCollect(this, destination);
                }
            }
            else
            {
                try
                {
                    Thread.sleep(10);
                }
                catch(Exception e){}
            }
        }
        
        try
        {
            Thread.sleep(2500);
        }
        catch(Exception e){}
        
        //Entering the Waiting Area
        System.out.println("Customer "+custID+" is now going to the Waiting Area "+t.destination);

    }
}
