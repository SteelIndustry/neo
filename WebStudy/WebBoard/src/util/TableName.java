package util;

import java.util.HashMap;
import java.util.Map;

public class TableName {
	private static Map<String, String> tableNames;

	public static String getTableName(String type)
	{
		return tableNames.get(type);
	}

	public static void setTableNames() {
		tableNames = new HashMap<String, String>();
		
		tableNames.put("free", "BOARD");
		tableNames.put("etc", "BOARD2");
	}
	
}
