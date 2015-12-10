package com.loobo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConditionalConfiguration.class)
public class MagicBeanTest {
    @Autowired
    MagicBean magicBean;

    @Test
    public void should_declare_magic_bean() throws Exception {
        assertNotNull(magicBean);
    }
}
