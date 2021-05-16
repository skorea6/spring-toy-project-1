package shop.mshop.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class PagingTest {
    @Test
    public void pagingTest() throws Exception {
        //given
        Paging paging = new Paging();
        paging.setPaging(1, 2, 5, 3);

        //when

        //then
        //Assertions.assertThat((int) Math.ceil(2 / (double)2)).isEqualTo(1);

        //Assertions.assertThat(paging.getBlock()).isEqualTo(1);
        Assertions.assertThat(paging.getFirstPageNum()).isEqualTo(1);
        //Assertions.assertThat(paging.getTotalPage()).isEqualTo(2);
        Assertions.assertThat(paging.getLastPageNum()).isEqualTo(2);
    }

}