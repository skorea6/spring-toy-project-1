package shop.mshop.message.response;

import lombok.Data;

@Data
public class ListResponseBase {
    private Integer pageNum; //현재 페이지번호
    private Long totalElements; //전체 게시물 총 개수
    private Integer totalPageCount; //전체 페이지 총 개수
    private Integer numberOfElements; //현재 페이지에서 조회된 개수
    private Integer firstPageNum; //첫번째 페이지 번호
    private Integer lastPageNum; //마지막 페이지 번호
    private Integer prevBlockPageNum; // 전 블럭 시작페이지 넘버
    private Integer nextBlockPageNum; // 다음 블럭 시작페이지 넘버
    private boolean firstPageCheck; //첫번째 페이지 여부
    private boolean lastPageCheck; //마지막 페이지 여부
}
