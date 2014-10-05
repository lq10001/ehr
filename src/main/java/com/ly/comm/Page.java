package com.ly.comm;

import org.nutz.dao.pager.Pager;

public class Page extends Pager {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 232222222222333L;


	private int pageIndex;


    public int getPageIndex() {
        return super.getPageNumber();
    }

    public void setPageIndex(int pageIndex) {
        super.setPageNumber(pageIndex + 1);
    }
}
