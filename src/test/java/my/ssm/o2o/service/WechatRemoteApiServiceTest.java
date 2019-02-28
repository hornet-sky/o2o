package my.ssm.o2o.service;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatRemoteApiServiceTest extends BaseTest {
    @SuppressWarnings("unused")
    @Autowired
    private WechatRemoteApiService wechatRemoteApiService;
    @Test
    public void test() throws IOException {
        
    }
}
