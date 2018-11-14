import java.util.*;
public class ProdCons
{
	public static void semwait(int s)
	{	
		if(s<=0)
			{
			 System.out.println("Other processes are busy");
			 while(s<=0);
			}
		else
		{	
			s=s-1;
		}
	}
	public static void semsignal(int s)
	{
		s=s+1;
	}
	public static int s,n,in,out,val=0;
	public static int queue[];
	public static void produce()
	{
		Random rn=new Random();
		int x;
		while(true)
		{
			if((in+1)%n==out)
			{
				System.out.println("Producer has to wait Queue is full");
			  		try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
					
					}
			}
			else
			{
				semwait(s);
				val++;
				queue[in]=val;
				System.out.println("Producer produced :"+queue[in]);
				in=(in+1)%n;
				semsignal(s);
				x=rn.nextInt();
				if(x>5)
				{
					try
					{
						Thread.sleep(1000);
					} 
					catch(Exception e)
					{
						
					}
				}
		}
	}
   }
	public static void consume()
	{
		Random rn=new Random();
		int y;
		int value=0;
		while(true)
		{
		if(in==out)
		{
			System.out.println("Consumer has to wait Queue is empty");
			try
				{
					Thread.sleep(700);
				} catch(Exception e)
				{
					
				}	
		}
		else
		{
		semwait(s);
		value=queue[out];
		System.out.println("Consumer consumed :"+queue[out]);
		queue[out]=-1;
		out=(out+1)%n;
		semsignal(s);
		y=rn.nextInt();
		if(y>5)
		{
			try
			{
				Thread.sleep(1000);
			} catch(Exception e)
			{
				
			}
		}
		}
	   }
	}
	public static void main(String args[]) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Queue Length=");
		n=sc.nextInt();
		n++;
		queue=new int[n];
		s=1;
		in=0;
		out=0;
		Thread t1 = new Thread(new Runnable()
        	{
			public void run()
            		{
                		produce();	
            		}
        	});
        	Thread t2 = new Thread(new Runnable()
        	{
          		public void run()
            		{
		                 consume();
        	}});
       		t1.start();
        	t2.start();   
	}
}