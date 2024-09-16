# ../pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.3.3</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo</name>
	<description>Demo project for Spring Boot</description>
	<url />
	<licenses>
		<license />
	</licenses>
	<developers>
		<developer />
	</developers>
	<scm>
		<connection />
		<developerConnection />
		<tag />
		<url />
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<!-- Compiler Plugin -->
			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.11.0</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
					<compilerArgs>
						<arg>-parameters</arg>
					</compilerArgs>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>
```

# ../.mvn\wrapper\maven-wrapper.properties

```
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
wrapperVersion=3.3.2
distributionType=only-script
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip

```

# ../src\main\java\com\example\demo\DemoApplication.java

```
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

```

# ../src\main\java\com\example\demo\controller\ItemController.java

```
package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "items"; // Trả về template items.html
    }

    @GetMapping("/add")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "add-item"; // Trả về template add-item.html
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }
}

```

# ../src\main\java\com\example\demo\controller\OrderController.java

```
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

```

# ../src\main\java\com\example\demo\model\Item.java

```
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    // Constructors
    public Item() {
    }

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}

```

# ../src\main\java\com\example\demo\model\Order.java

```
package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // Tránh xung đột với từ khóa SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    // Constructors
    public Order() {
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}

```

# ../src\main\java\com\example\demo\model\OrderDetail.java

```
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Liên kết với Item
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer quantity;

    // Constructors
    public OrderDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

```

# ../src\main\java\com\example\demo\repository\ItemRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

```

# ../src\main\java\com\example\demo\repository\OrderDetailRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}

```

# ../src\main\java\com\example\demo\repository\OrderRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

```

# ../src\main\resources\application.properties

```
spring.application.name=demo
spring.datasource.url=jdbc:mysql://localhost:3306/springboot?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=admin@2024
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

# ../src\main\resources\templates\add-item.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Thêm Sản phẩm</title>
</head>
<body>
    <h1>Thêm Sản phẩm mới</h1>
    <form th:action="@{/items/add}" method="post" th:object="${item}">
        <label>Tên:</label>
        <input type="text" th:field="*{name}" /><br/>
        <label>Giá:</label>
        <input type="number" th:field="*{price}" step="0.01" /><br/>
        <button type="submit">Lưu</button>
    </form>
</body>
</html>

```

# ../src\main\resources\templates\index.html

```
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
Bán Hàng (POS)
<br>
<a href="/items">Sản Phẩm</a>
<br>
<a href="/orders">Đơn Hàng</a>
</body>

</html>
```

# ../src\main\resources\templates\items.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách Sản phẩm</title>
</head>
<body>
    <h1>Danh sách Sản phẩm</h1>
    <a href="/">Home</a> &nbsp;
    <a href="/items/add">Thêm Sản phẩm mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Tên</th><th>Giá</th>
        </tr>
        <tr th:each="item : ${items}">
            <td th:text="${item.id}">1</td>
            <td th:text="${item.name}">Tên Sản phẩm</td>
            <td th:text="${item.price}">Giá</td>
        </tr>
    </table>
</body>
</html>

```

# ../src\main\resources\templates\new-order.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Tạo Đơn hàng mới</title>
</head>
<body>
    <h1>Tạo Đơn hàng mới</h1>
    <form th:action="@{/orders/new}" method="post">
        <table border="1">
            <tr>
                <th>Sản phẩm</th><th>Giá</th><th>Số lượng</th>
            </tr>
            <tr th:each="item : ${items}">
                <td th:text="${item.name}">Tên Sản phẩm</td>
                <td th:text="${item.price}">Giá</td>
                <td>
                    <input type="hidden" name="itemIds" th:value="${item.id}" />
                    <input type="number" name="quantities" value="0" min="0"/>
                </td>
            </tr>
        </table>
        <button type="submit">Tạo Đơn hàng</button>
    </form>
</body>
</html>

```

# ../src\main\resources\templates\order-details.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Chi tiết Đơn hàng</title>
</head>

<body>
    <a href="#" onclick="history.back()">Back</a> &nbsp;
    <h1>Chi tiết Đơn hàng #<span th:text="${order.id}"></span></h1>
    <p>Ngày: <span th:text="${#dates.format(order.date, 'yyyy-MM-dd HH:mm:ss')}"></span></p>
    <table border="1">
        <tr>
            <th>Sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Tổng</th>
        </tr>
        <tr th:each="detail : ${order.orderDetails}">
            <td th:text="${detail.item.name}">Tên Sản phẩm</td>
            <td th:text="${detail.item.price}">Giá</td>
            <td th:text="${detail.quantity}">Số lượng</td>
            <td th:text="${detail.quantity * detail.item.price}">Tổng</td>
        </tr>
    </table>
    <p>Tổng Cộng:
        <!-- <span th:text="${#numbers.formatCurrency(totalAmount)}"></span> -->
        <span th:text="${totalAmount}"></span>
    </p>
    <a href="/orders">Quay lại</a>
</body>

</html>
```

# ../src\main\resources\templates\orders.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách Đơn hàng</title>
</head>
<body>
    <h1>Danh sách Đơn hàng</h1>
    <a href="/" >Home</a> &nbsp;
    <a href="/orders/new">Tạo Đơn hàng mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Ngày</th><th>Chi tiết</th>
        </tr>
        <tr th:each="order : ${orders}">
            <td th:text="${order.id}">1</td>
            <td th:text="${#dates.format(order.date, 'yyyy-MM-dd HH:mm:ss')}">Ngày</td>
            <td><a th:href="@{'/orders/' + ${order.id}}">Xem Chi tiết</a></td>
        </tr>
    </table>
</body>
</html>

```

# ../src\test\java\com\example\demo\DemoApplicationTests.java

```
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}

```

# ../target\classes\application.properties

```
spring.application.name=demo
spring.datasource.url=jdbc:mysql://localhost:3306/springboot?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=admin@2024
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

# ../target\classes\templates\add-item.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Thêm Sản phẩm</title>
</head>
<body>
    <h1>Thêm Sản phẩm mới</h1>
    <form th:action="@{/items/add}" method="post" th:object="${item}">
        <label>Tên:</label>
        <input type="text" th:field="*{name}" /><br/>
        <label>Giá:</label>
        <input type="number" th:field="*{price}" step="0.01" /><br/>
        <button type="submit">Lưu</button>
    </form>
</body>
</html>

```

# ../target\classes\templates\index.html

```
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
Bán Hàng (POS)
<br>
<a href="/items">Sản Phẩm</a>
<br>
<a href="/orders">Đơn Hàng</a>
</body>

</html>
```

# ../target\classes\templates\items.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách Sản phẩm</title>
</head>
<body>
    <h1>Danh sách Sản phẩm</h1>
    <a href="/">Home</a> &nbsp;
    <a href="/items/add">Thêm Sản phẩm mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Tên</th><th>Giá</th>
        </tr>
        <tr th:each="item : ${items}">
            <td th:text="${item.id}">1</td>
            <td th:text="${item.name}">Tên Sản phẩm</td>
            <td th:text="${item.price}">Giá</td>
        </tr>
    </table>
</body>
</html>

```

# ../target\classes\templates\new-order.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Tạo Đơn hàng mới</title>
</head>
<body>
    <h1>Tạo Đơn hàng mới</h1>
    <form th:action="@{/orders/new}" method="post">
        <table border="1">
            <tr>
                <th>Sản phẩm</th><th>Giá</th><th>Số lượng</th>
            </tr>
            <tr th:each="item : ${items}">
                <td th:text="${item.name}">Tên Sản phẩm</td>
                <td th:text="${item.price}">Giá</td>
                <td>
                    <input type="hidden" name="itemIds" th:value="${item.id}" />
                    <input type="number" name="quantities" value="0" min="0"/>
                </td>
            </tr>
        </table>
        <button type="submit">Tạo Đơn hàng</button>
    </form>
</body>
</html>

```

# ../target\classes\templates\order-details.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Chi tiết Đơn hàng</title>
</head>

<body>
    <a href="#" onclick="history.back()">Back</a> &nbsp;
    <h1>Chi tiết Đơn hàng #<span th:text="${order.id}"></span></h1>
    <p>Ngày: <span th:text="${#dates.format(order.date, 'yyyy-MM-dd HH:mm:ss')}"></span></p>
    <table border="1">
        <tr>
            <th>Sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Tổng</th>
        </tr>
        <tr th:each="detail : ${order.orderDetails}">
            <td th:text="${detail.item.name}">Tên Sản phẩm</td>
            <td th:text="${detail.item.price}">Giá</td>
            <td th:text="${detail.quantity}">Số lượng</td>
            <td th:text="${detail.quantity * detail.item.price}">Tổng</td>
        </tr>
    </table>
    <p>Tổng Cộng:
        <!-- <span th:text="${#numbers.formatCurrency(totalAmount)}"></span> -->
        <span th:text="${totalAmount}"></span>
    </p>
    <a href="/orders">Quay lại</a>
</body>

</html>
```

# ../target\classes\templates\orders.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách Đơn hàng</title>
</head>
<body>
    <h1>Danh sách Đơn hàng</h1>
    <a href="/" >Home</a> &nbsp;
    <a href="/orders/new">Tạo Đơn hàng mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Ngày</th><th>Chi tiết</th>
        </tr>
        <tr th:each="order : ${orders}">
            <td th:text="${order.id}">1</td>
            <td th:text="${#dates.format(order.date, 'yyyy-MM-dd HH:mm:ss')}">Ngày</td>
            <td><a th:href="@{'/orders/' + ${order.id}}">Xem Chi tiết</a></td>
        </tr>
    </table>
</body>
</html>

```

