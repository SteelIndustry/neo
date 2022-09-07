package producer_consumer;

public class Consumer implements Runnable
{
	private final Repository rep;
	private String name;
	
	public Consumer(String name, Repository rep)
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
				
				sb.append(rep.remove());
				sb.append(" / ").append("[").append(name).append("]: ").append("worked.");
				
				System.out.println(sb.toString());
			}
		} catch (InterruptedException e)
		{
		}
	}
	
}
