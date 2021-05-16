package shop.mshop.util;

import lombok.Data;

@Data
public class Paging {
    private Integer firstPageNum; //첫번째 페이지 번호
    private Integer lastPageNum; //마지막 페이지 번호
    private Integer prevBlockPageNum; // 전 블럭 시작페이지 넘버
    private Integer nextBlockPageNum; // 다음 블럭 시작페이지 넘버

    public void setPaging(Integer pageNum, Integer pageElementCount, Integer pageBlockCount, Integer totalElements) {
        int block = (int) Math.ceil(pageNum / (double)pageBlockCount);
        this.firstPageNum = ((block-1)*pageBlockCount)+1;
        this.prevBlockPageNum = (block - 1)*pageBlockCount-(pageBlockCount-1);
        // (2-1)*2-(2-1)

        int totalPage = (int) Math.ceil(totalElements / (double)pageElementCount);
        this.lastPageNum = Math.min(totalPage, block * pageBlockCount);
        this.nextBlockPageNum = (block + 1)*pageBlockCount-(pageBlockCount-1);
    }
}
