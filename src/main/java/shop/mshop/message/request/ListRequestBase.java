package shop.mshop.message.request;

import lombok.Data;

@Data
public class ListRequestBase {
    private Integer pageNum; // 현재 페이지
    private Integer pageElementCount; // 한페이지에 얼마나 나올것인지
    private Integer pageBlockCount; // 하단 디스플레이 블럭 개수
    private String sortNm; // 정렬 이름 (id, name 등)
    private String sortType; // 정렬 타입 (desc, asc 등)
}
