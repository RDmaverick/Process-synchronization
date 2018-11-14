import java.util.*;

class Dining{
  public static int n; 
  public static int total=0;
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the no. of Philosophers:");
    n = sc.nextInt();
    for(int i=0; i<n; i++){
      Philo p = new Philo(i);
      p.start();
    }
  }
}

class Semaphore{
  public static int flag[] = new int[Main.n];
  public static void semWait(int x){
    flag[x] = 1;
    flag[(x+1)%(Main.n)] = 1;
  }
  public static void semSignal(int x){
    flag[x] = 0;
    flag[(x+1)%(Main.n)] = 0;
  }
  public static boolean check(int x){
    if(flag[x] == 0 && flag[(x+1)%(Main.n)] == 0){
      return true;
    }
    else{
      return false;
    }
  }
}

class Philo extends Thread{
  int id;
  int status; // 0:idle   1:hungry   2:eating
  Philo(int id){
    this.id = id;
    this.status = 0;
  }
  Random r = new Random();
  public void run(){
    while(Main.total<20){
      Main.total++;
      try{
        sleep((long)(r.nextInt(5)*1000));
      }
      catch(Exception e)
      {
        System.out.println("Thread Interrupted");
      }
      this.status = 1;
      if(Semaphore.check(this.id)){
        Semaphore.semWait(this.id);
        this.status = 2;
        int x = this.id;
        System.out.println("Philo "+(this.id+1)+" thinking");
         try{
        sleep(1000);
        }
        catch(Exception e)
        {
          System.out.println("Thread Interrupted");
        }
        System.out.println("Philo "+(this.id+1)+" eating with forks "+ (x+1) + " and "+((x+1)%(Main.n)+1));
        try{
        sleep(3000);
        }
        catch(Exception e)
        {
          System.out.println("Thread Interrupted");
        }
        Semaphore.semSignal(this.id);
        this.status = 0;
        System.out.println("Philo "+(this.id+1)+" has given up forks "+ (x+1) + " and "+((x+1)%(Main.n)+1));
      }
      else{
        System.out.println("Forks not available for philosopher "+(this.id+1));
        try{
        sleep(2000);
        }
        catch(Exception e)
        {
          System.out.println("Thread Interrupted");
        }
      }
    }
  }
}
