package APUbus;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class CCPAssignment extends Thread
{
    Semaphore btSem=new Semaphore(50,true); //Only 50 individual at one time.
    Semaphore gateSem=new Semaphore(1,true); //customer enter waiting area 1 after another
    TBooth tcA;
    TBooth tcB;
    TMachine tm;
    TNoGenerator tng;
    boolean open;
    int custID=1;
    
    public static void main(String[] args) 
    {
        CCPAssignment bt=new CCPAssignment();
        bt.start();
    }
    
    public CCPAssignment()
    {
        open=true;
        tng=new TNoGenerator();
        tcA=new TBooth(tng, "A");
        tcB=new TBooth(tng, "B");
        tm=new TMachine(tng);
    }
    
    
    public void run()
    {
        ExecutorService terminal=Executors.newCachedThreadPool();
        terminal.submit(new SecurityGuard(this));
        terminal.submit(new StaffMembers(tcA));
        terminal.submit(new StaffMembers(tcB));

        while(true)
        {
            terminal.submit(new Customers(custID, tcA, tcB,tm )); 
            custID++;
            try
            {
                //thread.sleep(200) is to test, if the terminal close or open 
                //Thread.sleep(200);
                Thread.sleep(1000*(1+new Random().nextInt(3))); //every 1-4 seconds a new Customers arrives
            }
            catch(Exception e){}
        }
    }
    
    
    public void TerminalEntry(Customers c)
    {
        if(open==false)
        {
            System.out.println("The entrance is closed by the security gaurd ... Customer "+c.custID+" is waiting.... (terminal population: "+(100-btSem.availablePermits())+")");
        }
        try
        {
            gateSem.acquire(); //only 1 Customers can go throught the gate at a time
            btSem.acquire(); 
            Thread.sleep(10);
            System.out.println("Customer "+c.custID+" has now entered the Bus Terminal (terminal population: "+(100-btSem.availablePermits())+")");
            Thread.sleep(100); //customer take 0.1 second to enter the Bus Terminal
            gateSem.release();
        }
        catch(Exception e){}  
    }
    
    
        public void Block()
    {
        open=false;
        System.out.println("The Bus Terminal entrance is now closed.");
        try 
        {
            btSem.acquire(20); //acquire(20) is used to close the entrance of the terminal
            System.out.println("The Bus Terminal entrance is now opened.");
            btSem.release(20); //releasing so that the customers can enter
        }
        catch(Exception e){}

        open=true;
    }
        
}
