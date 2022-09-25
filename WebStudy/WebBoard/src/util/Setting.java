package util;

public class Setting {
	
	private static int maxPostSize, postsPerPage, pagesPerBlock;
	private static String saveDir;
	
	public static int getPostsPerPage() {
		return postsPerPage;
	}

	public static void setPostsPerPage(String postsPerPage) {
		Setting.postsPerPage = Integer.parseInt(postsPerPage);
	}

	public static int getPagesPerBlock() {
		return pagesPerBlock;
	}

	public static void setPagesPerBlock(String pagesPerBlock) {
		Setting.pagesPerBlock = Integer.parseInt(pagesPerBlock);
	}

	public static void setMaxPostSize(String maxPostSize) {
		Setting.maxPostSize = Integer.parseInt(maxPostSize);
	}

	public static void setSaveDir(String saveDir) {
		Setting.saveDir = saveDir;
	}

	public static int getMaxPostSize() {
		return maxPostSize;
	}

	public static String getSaveDir() {
		return saveDir;
	}
}
