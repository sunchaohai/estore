import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: estore
 * @description: 测试类
 * @author: xiaohai
 * @create: 2018-08-30 11:11
 **/
public class Testj {
    private static final Logger LOGGER = LoggerFactory.getLogger(Testj.class);

    Integer t;
    Integer oldT;
    @Test
    public void test1(){
        LOGGER.error("哈哈Error Message!");
        LOGGER.warn("中文乱码过滤器，init方法.Warn Message!");
        LOGGER.info("Info Message!");
        LOGGER.debug("Debug Message!");
        LOGGER.trace("Trace Message!");

        setTemperature(10);
        setTemperature(100);
    }


    public   void  setTemperature(Integer temperature)  {
        oldT  =  t;
        t  =  temperature;
        LOGGER.debug( "Temperature set to {}. Old temperature was {}. " , t, oldT);
        if  (temperature.intValue()  >   50 )  {
            LOGGER.info( " Temperature has risen above 50 degrees. " );
        }
    }
}
