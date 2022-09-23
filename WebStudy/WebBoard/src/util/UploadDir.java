package util;

public class UploadDir {
	
	private static int maxPostSize;
	private static String saveDir;
	
	public static void setMaxPostSize(String maxPostSize) {
		UploadDir.maxPostSize = Integer.parseInt(maxPostSize);
	}

	public static void setSaveDir(String saveDir) {
		UploadDir.saveDir = saveDir;
	}

	public static int getMaxPostSize() {
		return maxPostSize;
	}

	public static String getSaveDir() {
		return saveDir;
	}
}
