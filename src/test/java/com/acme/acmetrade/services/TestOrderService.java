
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.acmetrade.TradeApplication;
import com.acme.acmetrade.domain.OrderType;
import com.acme.acmetrade.domain.entities.Order;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TradeApplication.class, Order.class})
public class TestOrderService {

	@Autowired
	private Order order;
	
	@Test
	public void validateOrderType() {
		order.setOrderType(OrderType.LIMITED);
		Assert.assertEquals(OrderType.LIMITED, order.getOrderType());
		
	}

}
