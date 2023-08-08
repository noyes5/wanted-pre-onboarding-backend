package com.wanted.onboarding.board;

public class PaginationUtils {
    public static int adjustPageNumber(int nowPage, int totalPage) {
        if (nowPage <= 0) {
            return 1;
        }
        return Math.min(nowPage, totalPage);
    }
    public static int calculateStartPage(int nowPage) {
        return Math.max(((nowPage - 1) / 10) * 10 + 1, 1);
    }
    public static int calculateEndPage(int startPage, int totalPages) {
        return Math.min(startPage + 9, totalPages);
    }
}