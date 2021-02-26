
import com.mmd.springcloud.mapper.PaymentMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class testq {

    @Autowired
    private PaymentMapper paymentMapper;

    @Test
    public void test(){
        System.out.println(paymentMapper);
    }
}
