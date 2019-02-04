package my.ssm.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.entity.Area;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void testFindAll() {
        List<Area> areas = areaService.findAll();
        areas.forEach(System.out::println);
        assertEquals("西苑", areas.get(0).getAreaName());
    }
}
