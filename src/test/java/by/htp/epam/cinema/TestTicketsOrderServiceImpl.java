//package by.htp.epam.cinema;
//
//import static org.mockito.Mockito.times;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PowerMockIgnore;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import by.htp.epam.cinema.db.dao.TicketsOrderDao;
//import by.htp.epam.cinema.db.dao.impl.TicketsOrderDaoImpl;
//import by.htp.epam.cinema.domain.TicketsOrder;
//import by.htp.epam.cinema.domain.TicketsOrder.Builder;
//import by.htp.epam.cinema.domain.User;
//import by.htp.epam.cinema.service.TicketsOrderService;
//import by.htp.epam.cinema.service.impl.TicketsOrderServiceImpl;
//import by.htp.epam.cinema.web.util.Timer;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ Timer.class, TicketsOrder.class })
//@PowerMockIgnore("javax.management.*")
//public class TestTicketsOrderServiceImpl {
//
//	private TicketsOrderService ticketsOrderService;
//	private TicketsOrderDao ticketsOrderDao;
//	private TicketsOrder ticketsOrder;
//	private User user;
//	private Timer timer;
//
//	@Before
//	public void init() {
//		user = User.builder().id(1).build();
//		ticketsOrderDao = Mockito.mock(TicketsOrderDaoImpl.class);
//		ticketsOrderService = new TicketsOrderServiceImpl(ticketsOrderDao);
//		ticketsOrder = Mockito.mock(TicketsOrder.class);
//		timer = Mockito.mock(Timer.class);
//		PowerMockito.mockStatic(Timer.class);
//		PowerMockito.mockStatic(TicketsOrder.class);
//
//		PowerMockito.when(TicketsOrder.newBuilder()).thenReturn(ticketsOrderBuilder);
//		Mockito.when(ticketsOrderService.readUserNonPaidOrder(user)).thenReturn(ticketsOrder);
//		Mockito.when(ticketsOrderDao.read(Mockito.anyInt())).thenReturn(ticketsOrder);
//		Mockito.when(ticketsOrderBuilder.setUserId(Mockito.anyInt())).thenReturn(ticketsOrderBuilder);
//		Mockito.when(ticketsOrderBuilder.build()).thenReturn(ticketsOrder);
//		Mockito.doNothing().when(timer).setStop(true);
//	}
//
//	@Test
//	public void readUserNonPaidOrderTest() {
//		ticketsOrderService.readUserNonPaidOrder(user);
//		Mockito.verify(ticketsOrderDao, times(1)).readByUserId(user.getId());
//	}
//
//	@Test
//	public void createTicketsOrderTest() {
//		ticketsOrderService.createTicketsOrder(user);
//		PowerMockito.verifyStatic(times(1));
//		TicketsOrder.newBuilder();
//		Mockito.verify(ticketsOrderBuilder, times(1)).setUserId(Mockito.anyInt());
//		Mockito.verify(ticketsOrderBuilder, times(1)).build();
//		Mockito.verify(ticketsOrderDao, times(1)).create(ticketsOrder);
//		Mockito.verify(ticketsOrderDao, times(1)).readByUserId(user.getId());
//	}
//
//	@Test
//	public void deleteNonPaidOrderTest() {
//		ticketsOrderService.deleteNonPaidOrder(user);
//		Mockito.verify(ticketsOrderDao, times(1)).readByUserId(user.getId());
//		Mockito.verify(ticketsOrderDao, times(1)).delete(Mockito.anyInt());
//	}
//
//	@Test
//	public void payOrderTest() {
//		ticketsOrderService.payOrder(Mockito.anyInt());
//		Mockito.verify(ticketsOrderDao, times(1)).read(Mockito.anyInt());
//		Mockito.verify(ticketsOrder, times(1)).setIsPaid(Mockito.anyBoolean());
//		Mockito.verify(ticketsOrderDao, times(1)).update(Mockito.any(TicketsOrder.class));
//	}
//}
