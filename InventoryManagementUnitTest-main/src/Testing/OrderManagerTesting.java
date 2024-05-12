package Testing;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cse430.Order;
import cse430.Order.OrderStatus;
import cse430.OrderManager;
import cse430.Product;
import cse430.Inventory;

public class OrderManagerTesting {
    
    OrderManager Odm = new OrderManager();

    Order Odr1 = new Order(1, 101, "2024-05-02", "Credit Card");
    Order Odr2 = new Order(2, 102, "2024-05-03", "Digital Payment");
    Order Odr3 = new Order(3, 103, "2024-05-06", "Cash On Delivery");
    Order Odr4 = new Order(4, 104, "2024-05-07", "Credit Card");
    
    Product prod1 = new Product(1, "Kitkat", 100.0, 2, null, null); 
    Product prod2 = new Product(2, "Safary", 100.0, 3, null, null); 

    @Test
    public void testAddOneOrder() {
        Odm.addOrder(Odr1);
        assertTrue(Odm.getOrders().contains(Odr1));
    }
    @Test
    public void testAddMultipleOrder() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        assertTrue(Odm.getOrders().contains(Odr1));
        assertTrue(Odm.getOrders().contains(Odr2));
    }
    
    @Test
    public void testRemoveOneOrder() {
        Odm.addOrder(Odr1);
        assertTrue(Odm.removeOrder(Odr1.getOrderId()));
        assertFalse(Odm.getOrders().contains(Odr3));
    }
    @Test
    public void testRemoveMultipleOrder() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        assertTrue(Odm.removeOrder(Odr1.getOrderId()));
        assertTrue(Odm.removeOrder(Odr2.getOrderId()));
        assertFalse(Odm.getOrders().contains(Odr3));
    }

    @Test
    public void testRemoveNonExistingOrder() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        assertFalse(Odm.removeOrder(Odr3.getOrderId()));
    }

    @Test
        public void testGetOrders() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        List<Order> expectedOrders = Arrays.asList(Odr1, Odr2);
        assertEquals(expectedOrders, Odm.getOrders());
        assertEquals(2, Odm.getOrders().size());
}
    @Test
    public void testGetOrdersWithNoOrders() {
        List<Order> expectedOrders = Collections.emptyList();
        assertEquals(expectedOrders, Odm.getOrders());
    }

    @Test
    public void testGetOrdersWithSingleOrder() {
        Odm.addOrder(Odr1);
        List<Order> expectedOrders = Collections.singletonList(Odr1);
        assertEquals(expectedOrders, Odm.getOrders());
    }
    

    @Test
    public void testCalculateTotalRevenue() {
        Odr1.setStatus(OrderStatus.DELIVERED);
        Odr1.addProduct(prod1, 2);
        Odm.addOrder(Odr1);
        //Odr2.setStatus(OrderStatus.DELIVERED);
        Odr2.addProduct(prod2, 1);
        Odm.addOrder(Odr2);
        
        assertEquals(100.0 * 2 + 100.0, Odm.calculateTotalRevenue(), 0.001);
    }
//     @Test
//     public void testCalculateTotalRevenueWithMultipleStatus() {

//     Odr1.setStatus(OrderStatus.DELIVERED);
//     Odr1.setTotalAmount(100.0); // Set revenue for Odr1
//     Odm.addOrder(Odr1);
    
//     Odr2.setStatus(OrderStatus.IN_PROGRESS);
//     Odm.addOrder(Odr2);
//     assertEquals(100.0, Odm.calculateTotalRevenue(), 0.001);
// }

    @Test
    public void testCalculateTotalRevenueWithNoOrders() {

        assertEquals(0.0, Odm.calculateTotalRevenue(), 0.001);
    }

    @Test
    public void testGetOrdersByCustomerIdWithNoOrders() {

    int customerId = 1; 
    assertEquals(0, Odm.getOrdersByCustomerId(customerId).size());
}

    @Test
    public void testGetOrdersByCustomerId() {
        Odm.addOrder(Odr2);
        assertEquals(1, Odm.getOrdersByCustomerId(102).size());
    }
    @Test
    public void testGetTotalOrders() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr4);
        assertEquals(2, Odm.getTotalOrders());
    }
    @Test
    public void testGetTotalOrdersWithNoOrders() {

        assertEquals(0, Odm.getTotalOrders());
    }
    @Test
    public void testClearAllOrders() {
        
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        Odm.clearAllOrders();
        List<Order> allOrders = Odm.getOrders();
        assertEquals(0, allOrders.size());
    }


    @Test
    public void testFindOrderById() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        assertEquals(Odr1, Odm.findOrderById(1));
        assertEquals(Odr2, Odm.findOrderById(2));
        assertNull(Odm.findOrderById(5)); 
    }
    @Test
    public void testUpdateOrderStatus() {
       // Odr1.setStatus(OrderStatus.DELIVERED);
        Odm.addOrder(Odr1);
        // boolean statusUpdated = Odm.updateOrderStatus(1, OrderStatus.SHIPPED);
        // assertTrue(statusUpdated);
        // Order updatedOrder = Odm.findOrderById(1);
        // assertEquals(OrderStatus.SHIPPED, updatedOrder.getStatus());
        assertTrue(Odm.updateOrderStatus(1, OrderStatus.PROCESSING));
     assertEquals(OrderStatus.PROCESSING, Odm.findOrderById(1).getStatus());

    }

    @Test
    public void testGetOrdersWithStatus() {
        OrderStatus status = OrderStatus.DELIVERED;
        Odr1.setStatus(status);
        Odr2.setStatus(status);
        List<Order> ordersWithStatus = Odm.getOrdersWithStatus(status);
       // assertEquals(2, ordersWithStatus.size());
        assertTrue(ordersWithStatus.contains(Odr1));
        assertFalse(ordersWithStatus.contains(Odr3));
    }

    @Test
    public void testGetOrdersByPaymentMethod() {

        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        Odm.addOrder(Odr3);
        Odm.addOrder(Odr4);

        List<Order> ordersByPaymentMethod = Odm.getOrdersByPaymentMethod("Credit Card");
        assertEquals(2, ordersByPaymentMethod.size());
        assertTrue(ordersByPaymentMethod.contains(Odr1));
        assertFalse(ordersByPaymentMethod.contains(Odr2));
    }
    
    @Test
    public void testGetOrdersByDateRange() {
        Odm.addOrder(Odr1);
        Odm.addOrder(Odr2);
        Odm.addOrder(Odr3);
        Odm.addOrder(Odr4);
        List<Order> ordersWithinDateRange = Odm.getOrdersByDateRange("2024-05-01", "2024-05-05");
        assertEquals(2, ordersWithinDateRange.size());
        assertTrue(ordersWithinDateRange.contains(Odr1));
        assertFalse(ordersWithinDateRange.contains(Odr4));
    }
}
