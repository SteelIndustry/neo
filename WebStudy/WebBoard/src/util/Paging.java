package util;

import java.util.HashMap;
import java.util.Map;

public class Paging {
	private final int PAGE_SIZE = Setting.getPostsPerPage(); // 한 페이지당 출력물 수
	private final int BLOCK_PAGE = Setting.getPagesPerBlock(); // 한 화면에 출력할 페이지 번호 수
	private int pageNum; // 현재 페이지 번호
	private int totalPage;
	private int totalCount;
	
	private int startPost, endPost; // 현재 페이지 게시물 첫 번호, 게시물 마지막 번호
	private int startPage, endPage; // 현재 화면 블록의 첫 페이지, 마지막 페이지
	
	public Paging(int totalCount, int pageNum)
	{
		// totalCount: 전체 게시물 수
		// pageNum: 현재 페이지 번호
		this.totalCount = totalCount;
		this.pageNum = pageNum;
		this.totalPage = (int)Math.ceil( (double)totalCount / PAGE_SIZE );
	}
	
	public int[] getPageRange()
	{
		startPost = (pageNum - 1) * PAGE_SIZE + 1;
		endPost = pageNum * PAGE_SIZE; // 마지막 게시물 번호
		
		return new int[]{startPost, endPost};
	}

	public Map<String, Integer> getPagingMap()
	{
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		
		// 화면에 출력될, 현재 블록의 첫 페이지 번호와 마지막 페이지 번호
		startPage = ( (pageNum-1) / BLOCK_PAGE  ) * BLOCK_PAGE + 1;
		endPage = ((pageNum-1) / BLOCK_PAGE) * BLOCK_PAGE + BLOCK_PAGE;
		if (endPage > totalPage)
		{
			endPage = totalPage;
		}
		
		map.put("blockPage", BLOCK_PAGE);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("totalCount", totalCount);
		map.put("firstPage", 1);
		map.put("lastPage", totalPage);
		
		// 이전 블록과 다음 블록의 페이지 번호
		int prevBlock = startPage != 1 ? (startPage - BLOCK_PAGE) : 1;
		int nextBlock = startPage + BLOCK_PAGE > totalPage ? totalPage : startPage+BLOCK_PAGE;
		
		map.put("prevBlock", prevBlock);
		map.put("nextBlock", nextBlock);
		
		return map;
	}
}
