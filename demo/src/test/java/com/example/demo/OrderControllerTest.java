//  package com.example.demo;

// import com.example.demo.controller.OrderController;
// import com.example.demo.controller.ShipmentController;
// import com.example.demo.controller.SupplierController;
// import com.example.demo.controller.TransportPlanController;
// import com.example.demo.controller.WarehouseController;
// import com.example.demo.model.Order;
// import com.example.demo.model.Shipment;
// import com.example.demo.model.Supplier;
// import com.example.demo.model.TransportPlan;
// import com.example.demo.model.WarehouseItem;
// import com.example.demo.service.OrderService;
// import com.example.demo.service.ShipmentService;
// import com.example.demo.service.SupplierService;
// import com.example.demo.service.TransportPlanService;
// import com.example.demo.service.WarehouseService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.Optional;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest({OrderController.class, SupplierController.class,TransportPlanController.class, WarehouseController.class,ShipmentController.class})  // Include both controllers in WebMvcTest
// public class OrderControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private OrderService orderService;

//     @MockBean
//     private TransportPlanService transportPlanService;

//     @MockBean
//     private SupplierService supplierService;

//     @MockBean
//     private WarehouseService warehouseService;

//     @MockBean
//     private ShipmentService shipmentService;


//     @Autowired
//     private ObjectMapper objectMapper;

//     // -------------------- Order Tests -------------------- //

//     @Test
//     void testGetAllOrders() throws Exception {
//         Order order1 = new Order();
//         order1.setId(1L);
//         order1.setCustomerName("John Doe");
//         order1.setProduct("Laptop");
//         order1.setQuantity(2);
//         order1.setStatus("Pending");
//         order1.setOrderDate(LocalDateTime.now());

//         Order order2 = new Order();
//         order2.setId(2L);
//         order2.setCustomerName("Jane Smith");
//         order2.setProduct("Phone");
//         order2.setQuantity(1);
//         order2.setStatus("Shipped");
//         order2.setOrderDate(LocalDateTime.now());

//         Mockito.when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

//         mockMvc.perform(get("/api/orders"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].customerName").value("John Doe"))
//                 .andExpect(jsonPath("$[1].customerName").value("Jane Smith"))
//                 .andExpect(jsonPath("$[0].product").value("Laptop"))
//                 .andExpect(jsonPath("$[1].product").value("Phone"))
//                 .andExpect(jsonPath("$.size()").value(2));
//     }

//     @Test
//     void testGetOrderById_Success() throws Exception {
//         Order order = new Order();
//         order.setId(1L);
//         order.setCustomerName("John Doe");
//         order.setProduct("Laptop");
//         order.setQuantity(2);
//         order.setStatus("Pending");
//         order.setOrderDate(LocalDateTime.now());

//         Mockito.when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

//         mockMvc.perform(get("/api/orders/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.customerName").value("John Doe"))
//                 .andExpect(jsonPath("$.product").value("Laptop"))
//                 .andExpect(jsonPath("$.quantity").value(2))
//                 .andExpect(jsonPath("$.status").value("Pending"));
//     }

//     @Test
//     void testGetOrderById_NotFound() throws Exception {
//         Mockito.when(orderService.getOrderById(2L)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/api/orders/2"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testCreateOrder() throws Exception {
//         Order order = new Order();
//         order.setCustomerName("John Doe");
//         order.setProduct("Laptop");
//         order.setQuantity(2);
//         order.setStatus("Pending");
//         order.setOrderDate(LocalDateTime.now());

//         Mockito.when(orderService.saveOrder(Mockito.any(Order.class))).thenReturn(order);

//         mockMvc.perform(post("/api/orders")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(order)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.customerName").value("John Doe"))
//                 .andExpect(jsonPath("$.product").value("Laptop"))
//                 .andExpect(jsonPath("$.quantity").value(2))
//                 .andExpect(jsonPath("$.status").value("Pending"));
//     }

//     @Test
//     void testDeleteOrder() throws Exception {
//         mockMvc.perform(delete("/api/orders/1"))
//                 .andExpect(status().isNoContent());
//     }

//     // -------------------- Supplier Tests -------------------- //

//     @Test
//     void testGetAllSuppliers() throws Exception {
//         Supplier supplier1 = new Supplier();
//         supplier1.setId(1L);
//         supplier1.setName("Supplier 1");
// //        supplier1.setProduct("Laptop");

//         Supplier supplier2 = new Supplier();
//         supplier2.setId(2L);
//         supplier2.setName("Supplier 2");
//        // supplier2.setProduct("Phone");

//         Mockito.when(supplierService.getAllSuppliers()).thenReturn(Arrays.asList(supplier1, supplier2));

//         mockMvc.perform(get("/api/suppliers"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].name").value("Supplier 1"))
//                 .andExpect(jsonPath("$[1].name").value("Supplier 2"))
//                // .andExpect(jsonPath("$[0].product").value("Laptop"))
//                 //.andExpect(jsonPath("$[1].product").value("Phone"))
//                 .andExpect(jsonPath("$.size()").value(2));
//     }

//     @Test
//     void testGetSupplierById_Success() throws Exception {
//         Supplier supplier = new Supplier();
//         supplier.setId(1L);
//         supplier.setName("Supplier 1");
//        // supplier.setProduct("Laptop");

//         Mockito.when(supplierService.getSupplierById(1L)).thenReturn(Optional.of(supplier));

//         mockMvc.perform(get("/api/suppliers/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.name").value("Supplier 1"));
//                // .andExpect(jsonPath("$.product").value("Laptop"));
//     }

//     @Test
//     void testGetSupplierById_NotFound() throws Exception {
//         Mockito.when(supplierService.getSupplierById(2L)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/api/suppliers/2"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testCreateSupplier() throws Exception {
//         Supplier supplier = new Supplier();
//         supplier.setName("Supplier 1");
//        // supplier.setProduct("Laptop");

//         Mockito.when(supplierService.createSupplier(Mockito.any(Supplier.class))).thenReturn(supplier);

//         mockMvc.perform(post("/api/suppliers")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(supplier)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Supplier 1"));
//                // .andExpect(jsonPath("$.product").value("Laptop"));
//     }

//     @Test
//     void testDeleteSupplier() throws Exception {
//         mockMvc.perform(delete("/api/suppliers/1"))
//                 .andExpect(status().isNoContent());
//     }

//     // -------------------- TransportPlan Tests -------------------- //

//     @Test
// void testGetAllTransportPlans() throws Exception {
//     TransportPlan plan1 = new TransportPlan();
//     plan1.setId(1L);
//     plan1.setRoute("Route 1");
//     plan1.setCarrier("Carrier 1");
//     plan1.setLoadCapacity(100);
//     plan1.setSchedule(LocalDateTime.now());

//     TransportPlan plan2 = new TransportPlan();
//     plan2.setId(2L);
//     plan2.setRoute("Route 2");
//     plan2.setCarrier("Carrier 2");
//     plan2.setLoadCapacity(200);
//     plan2.setSchedule(LocalDateTime.now());

//     Mockito.when(transportPlanService.getAllPlans()).thenReturn(Arrays.asList(plan1, plan2));

//     mockMvc.perform(get("/api/transport-plans"))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$[0].route").value("Route 1"))
//             .andExpect(jsonPath("$[1].route").value("Route 2"))
//             .andExpect(jsonPath("$.size()").value(2));
// }

// @Test
// void testGetTransportPlanById_Success() throws Exception {
//     TransportPlan plan = new TransportPlan();
//     plan.setId(1L);
//     plan.setRoute("Route 1");
//     plan.setCarrier("Carrier 1");
//     plan.setLoadCapacity(100);
//     plan.setSchedule(LocalDateTime.now());

//     Mockito.when(transportPlanService.getPlanById(1L)).thenReturn(Optional.of(plan));

//     mockMvc.perform(get("/api/transport-plans/1"))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.id").value(1L))
//             .andExpect(jsonPath("$.route").value("Route 1"))
//             .andExpect(jsonPath("$.carrier").value("Carrier 1"))
//             .andExpect(jsonPath("$.loadCapacity").value(100));
// }

// @Test
// void testGetTransportPlanById_NotFound() throws Exception {
//     Mockito.when(transportPlanService.getPlanById(2L)).thenReturn(Optional.empty());

//     mockMvc.perform(get("/api/transport-plans/2"))
//             .andExpect(status().isNotFound());
// }

// @Test
// void testCreateTransportPlan() throws Exception {
//     TransportPlan plan = new TransportPlan();
//     plan.setRoute("Route 1");
//     plan.setCarrier("Carrier 1");
//     plan.setLoadCapacity(100);
//     plan.setSchedule(LocalDateTime.now());

//     Mockito.when(transportPlanService.createPlan(Mockito.any(TransportPlan.class))).thenReturn(plan);

//     mockMvc.perform(post("/api/transport-plans")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(plan)))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.route").value("Route 1"))
//             .andExpect(jsonPath("$.carrier").value("Carrier 1"))
//             .andExpect(jsonPath("$.loadCapacity").value(100));
// }

// @Test
// void testUpdateTransportPlan_Success() throws Exception {
//     TransportPlan updatedPlan = new TransportPlan();
//     updatedPlan.setRoute("Updated Route");
//     updatedPlan.setCarrier("Updated Carrier");
//     updatedPlan.setLoadCapacity(150);
//     updatedPlan.setSchedule(LocalDateTime.now());

//     Mockito.when(transportPlanService.updatePlan(Mockito.eq(1L), Mockito.any(TransportPlan.class)))
//             .thenReturn(updatedPlan);

//     mockMvc.perform(put("/api/transport-plans/1")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(updatedPlan)))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.route").value("Updated Route"))
//             .andExpect(jsonPath("$.carrier").value("Updated Carrier"))
//             .andExpect(jsonPath("$.loadCapacity").value(150));
// }

// @Test
// void testDeleteTransportPlan() throws Exception {
//     mockMvc.perform(delete("/api/transport-plans/1"))
//             .andExpect(status().isNoContent());
// }
// // -------------------- Warehouse Tests -------------------- //

// @Test
// void testGetAllWarehouseItems() throws Exception {
//     WarehouseItem item1 = new WarehouseItem();
//     item1.setId(1L);
//     item1.setItemName("Laptop");
//     item1.setCategory("Electronics");
//     item1.setQuantity(50);
//     item1.setStorageLocation("Shelf 1");

//     WarehouseItem item2 = new WarehouseItem();
//     item2.setId(2L);
//     item2.setItemName("Phone");
//     item2.setCategory("Electronics");
//     item2.setQuantity(100);
//     item2.setStorageLocation("Shelf 2");

//     Mockito.when(warehouseService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

//     mockMvc.perform(get("/api/warehouse-items"))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$[0].itemName").value("Laptop"))
//             .andExpect(jsonPath("$[1].itemName").value("Phone"))
//             .andExpect(jsonPath("$.size()").value(2));
// }

// @Test
// void testGetWarehouseItemById_Success() throws Exception {
//     WarehouseItem item = new WarehouseItem();
//     item.setId(1L);
//     item.setItemName("Laptop");
//     item.setCategory("Electronics");
//     item.setQuantity(50);
//     item.setStorageLocation("Shelf 1");

//     Mockito.when(warehouseService.getItemById(1L)).thenReturn(Optional.of(item));

//     mockMvc.perform(get("/api/warehouse-items/1"))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.id").value(1L))
//             .andExpect(jsonPath("$.itemName").value("Laptop"))
//             .andExpect(jsonPath("$.category").value("Electronics"))
//             .andExpect(jsonPath("$.quantity").value(50))
//             .andExpect(jsonPath("$.storageLocation").value("Shelf 1"));
// }

// @Test
// void testGetWarehouseItemById_NotFound() throws Exception {
//     Mockito.when(warehouseService.getItemById(2L)).thenReturn(Optional.empty());

//     mockMvc.perform(get("/api/warehouse-items/2"))
//             .andExpect(status().isNotFound());
// }

// @Test
// void testCreateWarehouseItem() throws Exception {
//     WarehouseItem item = new WarehouseItem();
//     item.setItemName("Laptop");
//     item.setCategory("Electronics");
//     item.setQuantity(50);
//     item.setStorageLocation("Shelf 1");

//     Mockito.when(warehouseService.createItem(Mockito.any(WarehouseItem.class))).thenReturn(item);

//     mockMvc.perform(post("/api/warehouse-items")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(item)))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.itemName").value("Laptop"))
//             .andExpect(jsonPath("$.category").value("Electronics"))
//             .andExpect(jsonPath("$.quantity").value(50))
//             .andExpect(jsonPath("$.storageLocation").value("Shelf 1"));
// }

// @Test
// void testUpdateWarehouseItem_Success() throws Exception {
//     WarehouseItem updatedItem = new WarehouseItem();
//     updatedItem.setItemName("Updated Laptop");
//     updatedItem.setCategory("Electronics");
//     updatedItem.setQuantity(60);
//     updatedItem.setStorageLocation("Shelf 2");

//     Mockito.when(warehouseService.updateItem(Mockito.eq(1L), Mockito.any(WarehouseItem.class)))
//             .thenReturn(updatedItem);

//     mockMvc.perform(put("/api/warehouse-items/1")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(updatedItem)))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.itemName").value("Updated Laptop"))
//             .andExpect(jsonPath("$.category").value("Electronics"))
//             .andExpect(jsonPath("$.quantity").value(60))
//             .andExpect(jsonPath("$.storageLocation").value("Shelf 2"));
// }

// @Test
// void testDeleteWarehouseItem() throws Exception {
//     mockMvc.perform(delete("/api/warehouse-items/1"))
//             .andExpect(status().isNoContent());
// }
//  // -------------------- Shipment Tests -------------------- //

//     @Test
//     void testGetAllShipments() throws Exception {
//         Shipment shipment1 = new Shipment();
//         shipment1.setId(1L);
//         //shipment1.setShipmentNumber("SHP001");
//         shipment1.setDestination("New York");
//         shipment1.setStatus("Shipped");

//         Shipment shipment2 = new Shipment();
//         shipment2.setId(2L);
//         //shipment2.setShipmentNumber("SHP002");
//         shipment2.setDestination("Los Angeles");
//         shipment2.setStatus("In Transit");

//         Mockito.when(shipmentService.getAllShipments()).thenReturn(Arrays.asList(shipment1, shipment2));

//         mockMvc.perform(get("/api/shipments"))
//                 .andExpect(status().isOk())
//                 //.andExpect(jsonPath("$[0].shipmentNumber").value("SHP001"))
//                 //.andExpect(jsonPath("$[1].shipmentNumber").value("SHP002"))
//                 .andExpect(jsonPath("$.size()").value(2));
//     }

//     @Test
//     void testGetShipmentById_Success() throws Exception {
//         Shipment shipment = new Shipment();
//         shipment.setId(1L);
//         //shipment.setShipmentNumber("SHP001");
//         shipment.setDestination("New York");
//         shipment.setStatus("Shipped");

//         Mockito.when(shipmentService.getShipmentById(1L)).thenReturn(Optional.of(shipment));

//         mockMvc.perform(get("/api/shipments/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 //.andExpect(jsonPath("$.shipmentNumber").value("SHP001"))
//                 .andExpect(jsonPath("$.destination").value("New York"))
//                 .andExpect(jsonPath("$.status").value("Shipped"));
//     }

//     @Test
//     void testGetShipmentById_NotFound() throws Exception {
//         Mockito.when(shipmentService.getShipmentById(2L)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/api/shipments/2"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testCreateShipment() throws Exception {
//         Shipment shipment = new Shipment();
//        // shipment.setShipmentNumber("SHP003");
//         shipment.setDestination("Chicago");
//         shipment.setStatus("Shipped");

//         Mockito.when(shipmentService.createShipment(Mockito.any(Shipment.class))).thenReturn(shipment);

//         mockMvc.perform(post("/api/shipments")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(shipment)))
//                 .andExpect(status().isOk())
//                // .andExpect(jsonPath("$.shipmentNumber").value("SHP003"))
//                 .andExpect(jsonPath("$.destination").value("Chicago"))
//                 .andExpect(jsonPath("$.status").value("Shipped"));
//     }

//     @Test
//     void testUpdateShipment_Success() throws Exception {
//         Shipment updatedShipment = new Shipment();
//         //updatedShipment.setShipmentNumber("SHP001");
//         updatedShipment.setDestination("San Francisco");
//         updatedShipment.setStatus("Delivered");

//         Mockito.when(shipmentService.updateShipment(Mockito.eq(1L), Mockito.any(Shipment.class)))
//                 .thenReturn(updatedShipment);

//         mockMvc.perform(put("/api/shipments/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(updatedShipment)))
//                 .andExpect(status().isOk())
//                // .andExpect(jsonPath("$.shipmentNumber").value("SHP001"))
//                 .andExpect(jsonPath("$.destination").value("San Francisco"))
//                 .andExpect(jsonPath("$.status").value("Delivered"));
//     }

//     @Test
//     void testUpdateShipment_NotFound() throws Exception {
//         Shipment updatedShipment = new Shipment();
//         //updatedShipment.setShipmentNumber("SHP003");
//         updatedShipment.setDestination("Miami");
//         updatedShipment.setStatus("Shipped");

//         Mockito.when(shipmentService.updateShipment(Mockito.eq(2L), Mockito.any(Shipment.class)))
//                 .thenThrow(new RuntimeException("Shipment not found"));

//         mockMvc.perform(put("/api/shipments/2")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(updatedShipment)))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testDeleteShipment() throws Exception {
//         mockMvc.perform(delete("/api/shipments/1"))
//                 .andExpect(status().isNoContent());
//     }
// }

