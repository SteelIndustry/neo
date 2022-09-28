package util;

import java.io.File;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class XSSForMultiPart {
	
	private static AntiSamy as;
	
	public static void setAntiSamy(String path)
	{
		File policyFile = new File(path);
		Policy policy;
		try {
			policy = Policy.getInstance(policyFile);
			as = new AntiSamy(policy);
		} catch (PolicyException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkXSS(String str)
	{
		boolean result = false;
		CleanResults cr;
		try {
			cr = as.scan(str);
			
			if (cr.getNumberOfErrors() > 0)
				result = true;
			
		} catch (ScanException | PolicyException e) {
			e.printStackTrace();
		}
		return result;
	}
}
