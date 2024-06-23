package APUbus;
import java.util.Random;

public class StaffMembers implements Runnable
{
    TBooth tc;
    
    public StaffMembers(TBooth tc)
    {
        this.tc=tc;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(15000+new Random().nextInt(30000));
                //The StaffMembers want to go toilet every 15 to 30 seconds.
            }
            catch(Exception e){}
            while(true)
            {
                //here we are using tryAcquire. if the StaffMembers thread can acquire then the StaffMembers can go to the toilet.
                if(tc.tcSem.tryAcquire()==true)
                {
                    break;
                }
                
            }
            tc.ToiletBreak();
            tc.tcSem.release();
            try
            {
                Thread.sleep(10000);
                //the Staff Members toilet break is of 10 sec
            }
            catch(Exception e){}
            tc.ToiletBreakEnd();
        }
    }
}
