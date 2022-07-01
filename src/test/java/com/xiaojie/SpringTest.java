package com.xiaojie;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.xiaojie.check.TranInvalidCaseWithoutInjectSpring;
import com.xiaojie.service.OrderService;
import com.xiaojie.service.check.TranInvalidCaseByCallMethodSelf;
import com.xiaojie.service.check.TranInvalidCaseByThrowCheckException;
import com.xiaojie.service.check.TranInvalidCaseWithCatchException;
import com.xiaojie.service.check.TranInvalidCaseWithFinalAndStaticMethod;
import com.xiaojie.service.check.TranInvalidCaseWithMultThread;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SpringTest {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private TranInvalidCaseByThrowCheckException tranInvalidCaseByThrowCheckException;
    
    
    @Autowired
    private TranInvalidCaseWithCatchException tranInvalidCaseWithCatchException;

    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private TranInvalidCaseByCallMethodSelf tranInvalidCaseByCallMethodSelf;
    
    
    @Autowired
    private TranInvalidCaseWithMultThread tranInvalidCaseWithMultThread;
    /**
     * 场景一：service没有托管给spring.
     * 原因：spring事务生效的前提是，service必须是一个bean对象
     * 解决方案：将service注入spring
     * errorFlag：0：有异常 1：无异常
     * 总结：有自定义事务注解（@MyTransactional）的service必须创建在定义切入点包含的路径下，并且service必须托管给spring管理，这样事务才会生效
     * 
     */
//    @Test
    public void testServiceWithoutInjectSpring(){
        int errorFlag = 0;
        //1.此类没有被spring托管   2.此类下自定义事务注解没有创建在定义切入点包含的路径下
        TranInvalidCaseWithoutInjectSpring tranInvalidCaseWithoutInjectSpring = new TranInvalidCaseWithoutInjectSpring(orderService);
        boolean isSuccess = tranInvalidCaseWithoutInjectSpring.add(errorFlag);
        Assert.assertTrue(isSuccess);
    }
    
    /**
     * 场景二：针对受检异常事务是否回滚
     * 总结：声明式事务注解@Transactional：spring默认只会回滚非检查异常和error异常        自定义事务注解：针对所有异常进行回滚
     */
//    @Test
    public void testThrowCheckException() throws Exception{
        tranInvalidCaseByThrowCheckException.add();

    }
    
    /**
     * 场景三：业务自己捕获了异常
     * 原因：才能进行后续的处理，如果业务自己捕获了异常，则事务无法感知
     * 总结： 自定义事务注解异常捕获后事务失效
     */
//    @Test
    public void testCatchExecption() throws Exception{
        tranInvalidCaseWithCatchException.add();
    }
    
    
    /**
     * 场景四：方法用final修饰
     * 原因：因为spring事务是用动态代理实现，因此如果方法使用了final修饰，则代理类无法对目标方法进行重写，植入事务功能
     * 解决方案：方法不要用final修饰
     * 总结： 自定义事务注解方法用final修饰事务失效
     *
     */
//    @Test
    public void testFinal() {
        TranInvalidCaseWithFinalAndStaticMethod tranInvalidCaseWithFinalAndStaticMethod = applicationContext.getBean(TranInvalidCaseWithFinalAndStaticMethod.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);
        tranInvalidCaseWithFinalAndStaticMethod.add(orderService);
    }
    
    /**
     * 场景五：方法用static修饰
     * 原因：因为spring事务是用动态代理实现，因此如果方法使用了static修饰，则代理类无法对目标方法进行重写，植入事务功能
     * 解决方案：方法不要用final修饰
     * 总结： 自定义事务注解方法用static修饰事务失效
     *
     */
//    @Test
    public void testStatic() {
        TranInvalidCaseWithFinalAndStaticMethod tranInvalidCaseWithFinalAndStaticMethod = applicationContext.getBean(TranInvalidCaseWithFinalAndStaticMethod.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);
        tranInvalidCaseWithFinalAndStaticMethod.add(orderService);
    }
    
    /**
     * 场景六：调用本类方法
     * 原因：本类方法不经过代理，无法进行增强
     * 总结： 自定义事务注解方法调用本类方法事务失效
     */
//    @Test
    public void testCallMethodBySelf() {
        tranInvalidCaseByCallMethodSelf.save();
    }
    
    /**
     * 场景七：多线程调用
     * 原因：因为spring的事务是通过数据库连接来实现，而数据库连接spring是放在threalocal里面。
     * 同一个事务，只能用同一个数据库连接。而多线程场景下，拿到的数据库连接是不一样的，即是属于不同事务
     * 总结： 自定义事务注解多线程调用事务失效
     */
//    @Test
    public void testMultThread() throws Exception{
        tranInvalidCaseWithMultThread.save();
    }
}
