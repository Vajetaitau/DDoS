package ufc;

import ufc.config.JPAConfig;
import ufc.config.MVCConfig;
import ufc.config.SecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MVCConfig.class, JPAConfig.class, SecurityConfig.class})
@WebAppConfiguration
abstract public class TestClass {
}
