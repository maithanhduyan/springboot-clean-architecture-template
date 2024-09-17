Ta có dự án spring boot như sau: 

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
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity6</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
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

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\config\MvcConfig.java

```
package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/orders").setViewName("orders");
        registry.addViewController("/items").setViewName("items");
        registry.addViewController("/customers").setViewName("customers");
    }
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\config\SecurityConfig.java

```
package com.example.demo.config;

import com.example.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Cấu hình SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider()) // Thêm dòng này
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/register", "/css/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    // Cấu hình DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\controller\AuthController.java

```
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về template login.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Trả về template register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(user.getUsername()) != null) {
            // Xử lý trường hợp username đã tồn tại
            return "redirect:/register?error";
        }
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\controller\CustomerController.java

```
package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customers"; // Trả về template customers.html
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer"; // Trả về template add-customer.html
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }
}

```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

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

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/new")
    public String showNewOrderForm(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        return "new-order"; // Trả về template new-order.html
    }

    @PostMapping("/new")
    public String createOrder(
            @RequestParam("customerId") Long customerId,
            @RequestParam("itemIds") Long[] itemIds,
            @RequestParam("quantities") Integer[] quantities) {

        Order order = new Order();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        order.setCustomer(customer);
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

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\controller\UserController.java

```
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Các annotation và phương thức cần thiết
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Phương thức để đăng ký, đăng nhập, quản lý người dùng
    // ...
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\model\Customer.java

```
package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // Liên kết với Order
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}

```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

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
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}

```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\model\User.java

```
package com.example.demo.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Hoặc bạn có thể triển khai phân quyền
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAuthorities'");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\repository\CustomerRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\repository\ItemRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\repository\OrderDetailRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\repository\OrderRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\repository\UserRepository.java

```
package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

```

Ta có dự án spring boot như sau: 

# ../src\main\java\com\example\demo\service\UserDetailsServiceImpl.java

```
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với username: " + username);
        }
        return user;
    }
}

```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\add-customer.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Thêm Khách hàng</title>
</head>
<body>
    <h1>Thêm Khách hàng mới</h1>
    <form th:action="@{/customers/add}" method="post" th:object="${customer}">
        <label>Tên:</label>
        <input type="text" th:field="*{name}" /><br/>
        <label>Email:</label>
        <input type="email" th:field="*{email}" /><br/>
        <label>Số điện thoại:</label>
        <input type="text" th:field="*{phone}" /><br/>
        <button type="submit">Lưu</button>
    </form>
</body>
</html>

```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\customers.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách Khách hàng</title>
</head>
<body>
    <h1>Danh sách Khách hàng</h1>
    <a href="/">Home</a> &nbsp;
    <a href="/customers/add">Thêm Khách hàng mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Tên</th><th>Email</th><th>Số điện thoại</th>
        </tr>
        <tr th:each="customer : ${customers}">
            <td th:text="${customer.id}">1</td>
            <td th:text="${customer.name}">Tên</td>
            <td th:text="${customer.email}">Email</td>
            <td th:text="${customer.phone}">SĐT</td>
        </tr>
    </table>
</body>
</html>

```

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\index.html

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

<head>
    <meta charset="UTF-8">
    <title>Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
    Bán Hàng (POS)
    <br>
    <div th:if="${#authentication?.name != null}">
        <h1>Chào mừng, <span th:text="${#authentication.name}"></span>!</h1>
        <!-- Nội dung cho người dùng đã đăng nhập -->
    </div>
    <div th:unless="${#authentication?.name != null}">
        <h1>Chào mừng, Khách!</h1>
        <p><a th:href="@{/login}">Đăng nhập</a> hoặc <a th:href="@{/register}">Đăng ký</a> để tiếp tục.</p>
    </div>
    <br>
    <a href="/items">Sản Phẩm</a>
    <br>
    <a href="/orders">Đơn Hàng</a>
    <br>
    <a href="/customers">Khách Hàng</a>
</body>

</html>
```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\login.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Đăng nhập</title>
</head>
<body>
    <h1>Đăng nhập</h1>
    <div th:if="${param.error}" style="color: red;">
        Invalid username and password.
    </div>
    <form th:action="@{/login}" method="post">
        <label>Tên đăng nhập:</label>
        <input type="text" name="username" /><br/>
        <label>Mật khẩu:</label>
        <input type="password" name="password" /><br/>
        <button type="submit">Đăng nhập</button>
    </form>
    <p>Nếu bạn chưa có tài khoản, <a href="/register">Đăng ký tại đây</a>.</p>
</body>
</html>

```

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\new-order.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Tạo Đơn hàng mới</title>
</head>

<body>
    <h1>Tạo Đơn hàng mới</h1>
    <!-- Thêm phần chọn Customer -->
    <label>Khách hàng:</label>
    <form th:action="@{/orders/new}" method="post">
        <select name="customerId">
            <option th:each="customer : ${customers}" th:value="${customer.id}" th:text="${customer.name}"></option>
        </select>
        <br />
        <table border="1">
            <tr>
                <th>Sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng</th>
            </tr>
            <tr th:each="item : ${items}">
                <td th:text="${item.name}">Tên Sản phẩm</td>
                <td th:text="${item.price}">Giá</td>
                <td>
                    <input type="hidden" name="itemIds" th:value="${item.id}" />
                    <input type="number" name="quantities" value="0" min="0" />
                </td>
            </tr>
        </table>
        <button type="submit">Tạo Đơn hàng</button>
    </form>
</body>

</html>
```

Ta có dự án spring boot như sau: 

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
    <p th:text="${'Khách hàng:' + order.customer.name}"><span></span></p>
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

Ta có dự án spring boot như sau: 

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
            <td th:text="${order.customer.name}">1</td>
            <td th:text="${#dates.format(order.date, 'yyyy-MM-dd HH:mm:ss')}">Ngày</td>
            <td><a th:href="@{'/orders/' + ${order.id}}">Xem Chi tiết</a></td>
        </tr>
    </table>
</body>
</html>

```

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\register.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Đăng ký</title>
</head>

<body>
    <h1>Đăng ký Tài khoản</h1>
    <div th:if="${param.error}" style="color: red;">
        Invalid username and password.
    </div>
    <form th:action="@{/register}" method="post" th:object="${user}">
        <label>Tên đăng nhập:</label>
        <input type="text" th:field="*{username}" /><br />
        <label>Mật khẩu:</label>
        <input type="password" th:field="*{password}" /><br />
        <button type="submit">Đăng ký</button>
    </form>
    <p>Nếu bạn đã có tài khoản, <a href="/login">Đăng nhập tại đây</a>.</p>
</body>

</html>
```

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\error\404.html

```
ERROR 404!
```

Ta có dự án spring boot như sau: 

# ../src\main\resources\templates\error\500.html

```
ERROR 500
```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../target\classes\templates\add-customer.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Thêm Khách hàng</title>
</head>
<body>
    <h1>Thêm Khách hàng mới</h1>
    <form th:action="@{/customers/add}" method="post" th:object="${customer}">
        <label>Tên:</label>
        <input type="text" th:field="*{name}" /><br/>
        <label>Email:</label>
        <input type="email" th:field="*{email}" /><br/>
        <label>Số điện thoại:</label>
        <input type="text" th:field="*{phone}" /><br/>
        <button type="submit">Lưu</button>
    </form>
</body>
</html>

```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../target\classes\templates\customers.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách Khách hàng</title>
</head>
<body>
    <h1>Danh sách Khách hàng</h1>
    <a href="/">Home</a> &nbsp;
    <a href="/customers/add">Thêm Khách hàng mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Tên</th><th>Email</th><th>Số điện thoại</th>
        </tr>
        <tr th:each="customer : ${customers}">
            <td th:text="${customer.id}">1</td>
            <td th:text="${customer.name}">Tên</td>
            <td th:text="${customer.email}">Email</td>
            <td th:text="${customer.phone}">SĐT</td>
        </tr>
    </table>
</body>
</html>

```

Ta có dự án spring boot như sau: 

# ../target\classes\templates\index.html

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

<head>
    <meta charset="UTF-8">
    <title>Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
    Bán Hàng (POS)
    <br>
    <div th:if="${#authentication?.name != null}">
        <h1>Chào mừng, <span th:text="${#authentication.name}"></span>!</h1>
        <!-- Nội dung cho người dùng đã đăng nhập -->
    </div>
    <div th:unless="${#authentication?.name != null}">
        <h1>Chào mừng, Khách!</h1>
        <p><a th:href="@{/login}">Đăng nhập</a> hoặc <a th:href="@{/register}">Đăng ký</a> để tiếp tục.</p>
    </div>
    <br>
    <a href="/items">Sản Phẩm</a>
    <br>
    <a href="/orders">Đơn Hàng</a>
    <br>
    <a href="/customers">Khách Hàng</a>
</body>

</html>
```

Ta có dự án spring boot như sau: 

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

Ta có dự án spring boot như sau: 

# ../target\classes\templates\login.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Đăng nhập</title>
</head>
<body>
    <h1>Đăng nhập</h1>
    <div th:if="${param.error}" style="color: red;">
        Invalid username and password.
    </div>
    <form th:action="@{/login}" method="post">
        <label>Tên đăng nhập:</label>
        <input type="text" name="username" /><br/>
        <label>Mật khẩu:</label>
        <input type="password" name="password" /><br/>
        <button type="submit">Đăng nhập</button>
    </form>
    <p>Nếu bạn chưa có tài khoản, <a href="/register">Đăng ký tại đây</a>.</p>
</body>
</html>

```

Ta có dự án spring boot như sau: 

# ../target\classes\templates\new-order.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Tạo Đơn hàng mới</title>
</head>

<body>
    <h1>Tạo Đơn hàng mới</h1>
    <!-- Thêm phần chọn Customer -->
    <label>Khách hàng:</label>
    <form th:action="@{/orders/new}" method="post">
        <select name="customerId">
            <option th:each="customer : ${customers}" th:value="${customer.id}" th:text="${customer.name}"></option>
        </select>
        <br />
        <table border="1">
            <tr>
                <th>Sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng</th>
            </tr>
            <tr th:each="item : ${items}">
                <td th:text="${item.name}">Tên Sản phẩm</td>
                <td th:text="${item.price}">Giá</td>
                <td>
                    <input type="hidden" name="itemIds" th:value="${item.id}" />
                    <input type="number" name="quantities" value="0" min="0" />
                </td>
            </tr>
        </table>
        <button type="submit">Tạo Đơn hàng</button>
    </form>
</body>

</html>
```

Ta có dự án spring boot như sau: 

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
    <p th:text="${'Khách hàng:' + order.customer.name}"><span></span></p>
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

Ta có dự án spring boot như sau: 

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
            <td th:text="${order.customer.name}">1</td>
            <td th:text="${#dates.format(order.date, 'yyyy-MM-dd HH:mm:ss')}">Ngày</td>
            <td><a th:href="@{'/orders/' + ${order.id}}">Xem Chi tiết</a></td>
        </tr>
    </table>
</body>
</html>

```

Ta có dự án spring boot như sau: 

# ../target\classes\templates\register.html

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Đăng ký</title>
</head>

<body>
    <h1>Đăng ký Tài khoản</h1>
    <div th:if="${param.error}" style="color: red;">
        Invalid username and password.
    </div>
    <form th:action="@{/register}" method="post" th:object="${user}">
        <label>Tên đăng nhập:</label>
        <input type="text" th:field="*{username}" /><br />
        <label>Mật khẩu:</label>
        <input type="password" th:field="*{password}" /><br />
        <button type="submit">Đăng ký</button>
    </form>
    <p>Nếu bạn đã có tài khoản, <a href="/login">Đăng nhập tại đây</a>.</p>
</body>

</html>
```

Ta có dự án spring boot như sau: 

# ../target\classes\templates\error\404.html

```
ERROR 404!
```

Ta có dự án spring boot như sau: 

# ../target\classes\templates\error\500.html

```
ERROR 500
```

