import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.acme.acmetrade.TradeApplication;
import com.acme.acmetrade.domain.OrderSide;
import com.acme.acmetrade.domain.OrderStatus;
import com.acme.acmetrade.domain.OrderType;
import com.acme.acmetrade.domain.entities.Order;
import com.acme.acmetrade.exceptions.OrderNegativeVolumeException;
import com.acme.acmetrade.exceptions.OrderNotFoundException;
import com.acme.acmetrade.exceptions.OrderAlreadyFulfilledException;
import com.acme.acmetrade.exceptions.OrderAlreadyCancelledException;
import com.acme.acmetrade.repository.OrderRepository;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TradeApplication.class, OrderRepository.class, Order.class})

@Transactional
public class TestOrderRepository {
	private Order CANCELLED_LIMIT_BUY_ORDER = new Order(UUID.randomUUID(), "CS", OrderSide.BUY, OrderType.LIMITED, 100, 1, new Date(), OrderStatus.CANCELLED );
	private Order FULFILLED_LIMIT_BUY_ORDER = new Order(UUID.randomUUID(), "CS", OrderSide.BUY, OrderType.LIMITED, 100, 1, new Date(), OrderStatus.FULFILLED );
	private Order OPEN_LIMIT_BUY_ORDER = new Order(UUID.randomUUID(), "CS", OrderSide.BUY, OrderType.LIMITED, 100, 1, new Date(), OrderStatus.OPEN );
	@Autowired
	private OrderRepository orderRepository;
	
	@Test
	public void testIdPlaceOrder() {
		UUID orderId = CANCELLED_LIMIT_BUY_ORDER.getId();
		orderRepository.placeOrder(CANCELLED_LIMIT_BUY_ORDER);
		List<Order> listOrderId = orderRepository.getOrderById(orderId);
		Assert.assertEquals(orderId, listOrderId.get(0).getId());
	}
	
	@Test
	public void testVolumePlaceOrder() {
		UUID orderId = CANCELLED_LIMIT_BUY_ORDER.getId();
		int orderVolume = CANCELLED_LIMIT_BUY_ORDER.getVolume();
		orderRepository.placeOrder(CANCELLED_LIMIT_BUY_ORDER);
		List<Order> listOrderId = orderRepository.getOrderById(orderId);
		Assert.assertEquals(orderVolume, listOrderId.get(0).getVolume());
	}
	
	@Test(expected=OrderNegativeVolumeException.class)
	public void testVolumeExceptionPlaceOrder() {
		Order order = new Order(UUID.randomUUID(), "CS", OrderSide.BUY, OrderType.LIMITED, 1, -2, new Date(), OrderStatus.CANCELLED );
		orderRepository.placeOrder(order);
	}
	
	@Test(expected=OrderAlreadyCancelledException.class)
	public void testCancelAlreadyCancelledTest() {
		orderRepository.placeOrder(CANCELLED_LIMIT_BUY_ORDER);
		orderRepository.cancelOrder(CANCELLED_LIMIT_BUY_ORDER.getId());
	}
	
	@Test(expected=OrderAlreadyFulfilledException.class)
	public void testCancelAlreadyFulfilledTest() {
		orderRepository.placeOrder(FULFILLED_LIMIT_BUY_ORDER);
		orderRepository.cancelOrder(FULFILLED_LIMIT_BUY_ORDER.getId());
	}
	
	@Test
	public void testCancelOrder() {
		orderRepository.placeOrder(OPEN_LIMIT_BUY_ORDER);
		orderRepository.cancelOrder(OPEN_LIMIT_BUY_ORDER.getId());
		Assert.assertEquals(OrderStatus.CANCELLED, orderRepository.getOrderById(OPEN_LIMIT_BUY_ORDER.getId()).get(0).getOrderStatus());
	}
	
}
