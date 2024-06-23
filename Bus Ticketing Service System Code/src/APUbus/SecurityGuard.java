package APUbus;

public class SecurityGuard implements Runnable
{
    CCPAssignment bt;
    
    public SecurityGuard(CCPAssignment bt) //Security Guard checks if the terminal is full or not.
    {
        this.bt=bt;
    }
    
    @Override
    public void run()
    {
        while(true)
        {   
            if(bt.btSem.availablePermits()==0) //If the terminal is full, it closes the gates.
            {
                bt.Block();
            }
        }
    }
}
