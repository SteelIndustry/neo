package test;

public class Repository
{
	private String[] list;
	
	private int head;
	private int tail;
	private int count;
	
	private int QSIZE=10;
	
	private Repository() 
	{
		this.list = new String[QSIZE];
		
		this.head = 0;
		this.tail = 0;
		this.count = 0;
	}
	
	private static class InnerInstanceClazz
	{
		private static final Repository instance = new Repository();
	}
	
	public static Repository getInstance()
	{
		return InnerInstanceClazz.instance;
	}
	
	public synchronized void add(String s) throws InterruptedException
	{
		while( count >= list.length )
		{
			System.out.println("Producer Wait");
			wait();
		}
		
		list[tail] = s;
		
		tail = (tail + 1) % list.length;
		count++;
		
		print();
		
		notifyAll();
	}
	
	public synchronized String remove() throws InterruptedException
	{
		while (count <= 0)
		{
			System.out.println("Consumer Wait");
			wait();
		}
					
		String s = list[head];
		
		head = (head+1) % list.length;
		count--;
		
		print();
				
		notifyAll();
		
		return s;
	}
	
	private synchronized void print()
	{
		System.out.println("현재: "+count);
	}
}
