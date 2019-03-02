package my.ssm.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.entity.HeadLine;

public class HeadLineServiceTest extends BaseTest {
    @Autowired
    private HeadLineService headLineService;
    @Test
    public void testFindByCondition() {
        HeadLine condition = new HeadLine();
        condition.setEnableStatus(1);
        List<HeadLine> headLines = headLineService.findByCondition(condition);
        headLines.forEach(System.out::println);
    }
}
