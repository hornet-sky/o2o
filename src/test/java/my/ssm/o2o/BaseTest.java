package my.ssm.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", 
    "classpath:spring/spring-service.xml",
    "classpath:spring/spring-web.xml"})
@WebAppConfiguration
public class BaseTest {

}
