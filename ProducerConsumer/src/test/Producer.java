package test;

public class Producer implements Runnable
{
	private final Repository rep;
	private String name;
	private int i = 0;
	
	public Producer(String name, Repository rep)
	{
		this.name = name;
		this.rep = rep;
	}
	
	@Override
	public void run()
	{
		StringBuffer sb;
		try
		{
			while(true)
			{
				sb = new StringBuffer();
				sb.append("[").append(name).append("]: ").append(i++);
				rep.add(sb.toString());
				
				System.out.println(sb.toString() + " add");
				
				Thread.sleep(100);
			}
		} catch (InterruptedException e)
		{
		}		
	}
}
