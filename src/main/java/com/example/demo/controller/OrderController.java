package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/new")
    public String showNewOrderForm(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "new-order"; // Trả về template new-order.html
    }

    @PostMapping("/new")
    public String createOrder(@RequestParam("itemIds") Long[] itemIds,
            @RequestParam("quantities") Integer[] quantities) {
        Order order = new Order();
        orderRepository.save(order); // Lưu Order trước để có ID

        for (int i = 0; i < itemIds.length; i++) {
            if (quantities[i] > 0) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setItem(itemRepository.findById(itemIds[i]).orElse(null));
                orderDetail.setQuantity(quantities[i]);
                orderDetailRepository.save(orderDetail);
            }
        }

        return "redirect:/orders";
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders"; // Trả về template orders.html
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        double totalAmount = order.getOrderDetails().stream()
                .mapToDouble(od -> od.getQuantity() * od.getItem().getPrice())
                .sum();
        model.addAttribute("order", order);
        model.addAttribute("totalAmount", totalAmount);
        return "order-details"; // Trả về template order-details.html
    }
}
