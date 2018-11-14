import java.io.*;
import java.util.Random;

public class Main extends Thread {
	
	public static int count = 0;
	
	public static void main(String[] args)
	{
		Process1 obj = new Process1();
		obj.start();
		Process2 obj1 = new Process2();
		obj1.start();
	}
	
    public static void read() {
		
		if(Semaphore.readCheck())
		{
			Semaphore.readFlag++;
      System.out.println("Readcount="+Semaphore.readFlag);
			// The name of the file to open.
			String fileName = "test.txt";

			// This will reference one line at a time
			String line = null;

			try {
				// FileReader reads text files in the default encoding.
				FileReader fileReader = 
					new FileReader(fileName);

				// Always wrap FileReader in BufferedReader.
				BufferedReader bufferedReader = 
					new BufferedReader(fileReader);

					System.out.print("\n");
				while((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
				}   

				// Always close files.
				bufferedReader.close();         
			}
			catch(FileNotFoundException ex) {
				System.out.println(
					"Unable to open file '" + 
					fileName + "'");                
			}
			catch(IOException ex) {
				System.out.println(
					"Error reading file '" 
					+ fileName + "'");                  
				// Or we could just do this: 
				// ex.printStackTrace();
			}
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.out.println("\nSystem interrupted");
			}
			Semaphore.readFlag--;
      
      System.out.println("Reading Done");
      System.out.println("Readcount="+Semaphore.readFlag);
		}
		else
		{
			System.out.println("File busy, read failed");
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.out.println("\nSystem interrupted");
			}
		}
        
    }
	
	public static void write(String str) {
		
		if(Semaphore.writeCheck())
		{
			Semaphore.waitt();
			// The name of the file to open.
			String fileName = "test.txt";

			try {
				// Assume default encoding.
				FileWriter fileWriter =
					new FileWriter(fileName, true);

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);

				// Note that write() does not automatically
				// append a newline character.
				bufferedWriter.append(str);
				bufferedWriter.newLine();

				// Always close files.
				bufferedWriter.close();
			}
			catch(IOException ex) {
				System.out.println(
					"Error writing to file '"
					+ fileName + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.out.println("\nSystem interrupted");
			}
			Semaphore.signal();
      System.out.println("Writing Done");
		}
		else
		{
			System.out.println("\nFile busy, write failed");
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.out.println("\nSystem interrupted");
			}
		}
        
    }
}

class Semaphore
{
  public static int readFlag;
  public static int writeFlag;

  public static void waitt()
  {
    readFlag=1;
	writeFlag=1;
  }
  public static void signal()
  {
    readFlag=0;
	writeFlag=0;
  }
  public static boolean readCheck()
  {
    if(writeFlag==0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  public static boolean writeCheck()
  {
    if(readFlag==0 && writeFlag==0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}

class Process1 extends Thread{
	public void run()
	{
		Main.count++;
		Random r = new Random();
		int i = r.nextInt(10);
		if(i%2==0)
		{
			System.out.println("P1 read");
			Main.read();
		}
		else
		{
			System.out.println("P1 write");
			Main.write("PROCESS 1 running");
		}
		try
		{
			Thread.sleep((long)r.nextInt(5)*1000);
		}
		catch(Exception e)
		{
			System.out.println("\nSystem interrupted");
		}
		if(Main.count<20)
		{
			Process1 obj = new Process1();
			obj.start();
		}
	}
}

class Process2 extends Thread{
	public void run()
	{
		Main.count++;
		Random r = new Random();
		int i = r.nextInt(10);
		if(i%2==0)
		{
			System.out.println("P2 read");
			Main.read();
		}
		else
		{
			System.out.println("P2 Write");
			Main.write(" PROCESS 2 running ");
		}
		try
		{
			Thread.sleep((long)r.nextInt(5)*1000);
		}
		catch(Exception e)
		{
			System.out.println("\nSystem interrupted");
		}
		if(Main.count<20)
		{
			Process2 obj = new Process2();
			obj.start();
		}
	}
}

