package trans.main;

import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		long start, end;
		start = System.currentTimeMillis();
		
		File file = new File("C:/TMTest/dest/asgw");
		
		System.out.println(file.isDirectory());
		
		/*		
		// 원본 디렉토리
		String sourcePath = "C:/TMTest/source";
		
		// 복사본을 저장하려는 디렉토리
		String destinationPath = "C:/TMTest/dest";
		
		Transmission trans = new Transmission();
		
		trans.transfer(sourcePath, destinationPath);
		
		end = System.currentTimeMillis();
		System.out.println(end-start+"ms");
		*/
	}
}