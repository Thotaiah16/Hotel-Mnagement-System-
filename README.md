# Hotel Management System

Welcome to the Hotel Management System — a robust, enterprise-grade solution designed to revolutionize hotel operations through dual applications: a powerful Desktop Manager for staff and a dynamic Spring Boot Web Application for customers, both unified by a centralized MySQL database ensuring real-time data consistency across all business operations.[1]

## 🏨 Desktop Manager Application
**Professional Hotel Staff Operations Platform**

A sophisticated JavaFX desktop application engineered to empower hotel staff and management with comprehensive operational control. This application delivers seamless management of rooms, reservations, restaurant services, and administrative functions through an intuitive interface connected to a centralized MySQL database.

### **Core Features**
- **🏨 Advanced Room Management**: Complete room lifecycle management including real-time availability tracking, pricing updates, and status monitoring
- **🍽️ Restaurant Operations**: Full-service menu management with dish categorization, pricing control, and order processing capabilities  
- **📋 Integrated Booking System**: Unified reservation and order management interface for streamlined operations
- **🔐 Secure Administrative Control**: Protected manager operations with BCrypt encryption and role-based access controls
- **📊 Real-time Dashboard**: Comprehensive operational insights and data visualization tools

### **Technology Stack - Desktop Manager**

| **Category** | **Technology** | **Purpose** |
|-------------|----------------|-------------|
| **Runtime Environment** | Java 21 | Core application platform |
| **UI Framework** | JavaFX + FXML | Modern desktop interface |
| **Database** | MySQL | Data persistence layer |
| **Security** | jBCrypt | Password encryption |
| **Data Processing** | JSON Library | Data serialization |
| **UI Components** | JCalendar | Date/time selection |
| **Database Connectivity** | MySQL Connector/J | Database integration |
| **Development Environment** | Apache NetBeans | IDE platform |
| **Server Stack** | XAMPP | Local development server |

***

## 🌐 Web Application
**Customer-Facing Online Platform**

A modern Spring Boot web application providing customers with seamless access to hotel services through any web browser. Features secure registration, authentication, and comprehensive booking management capabilities.

### **Key Features**
- **📧 Email-Based Registration**: Secure account creation with OTP verification
- **🔐 Authenticated Access**: BCrypt-secured login/logout functionality
- **📊 Customer Dashboard**: Personalized booking history and profile management
- **🛡️ Session Security**: Server-side session management with encrypted storage
- **📱 Responsive Design**: Cross-platform compatibility for all devices

### **Technology Stack - Web Application**

| **Category** | **Technology** | **Purpose** |
|-------------|----------------|-------------|
| **Backend Framework** | Spring Boot (Web/MVC) | Application architecture |
| **Data Layer** | Spring Data JPA + Hibernate ORM | Object-relational mapping |
| **Runtime** | Java 21 | Core platform |
| **Build Tool** | Maven | Dependency management |
| **Template Engine** | Thymeleaf | Server-side rendering |
| **Frontend Assets** | CSS3/JavaScript/HTML5 | Client-side presentation |
| **Database** | MySQL + Connector/J | Data persistence |
| **Email Services** | Spring Boot Mail + Jakarta Mail | OTP delivery system |
| **SMTP Provider** | Gmail SMTP (App Password) | Email authentication |
| **Security** | jBCrypt + Session Management | Authentication & encryption |
| **Testing Framework** | Spring Boot Test + JUnit 5 | Quality assurance |

***

## 🚀 Getting Started

### **Prerequisites**
- **JDK 21**: Latest Java Development Kit
- **Apache NetBeans IDE**: Development environment
- **XAMPP**: Apache and MySQL server management
- **JavaFX SDK 21**: UI framework libraries

### **Quick Setup**

1. **Database Configuration**
   ```bash
   # Start XAMPP services
   Start Apache and MySQL modules
   
   # Create database at http://localhost/phpmyadmin/
   Database name: java_hotel_management
   
   # Import provided SQL schema
   ```

2. **Desktop Application**
   ```bash
   git clone <repository-url>
   # Open project in NetBeans
   # Add required JAR libraries
   # Run with admin/admin credentials
   ```

3. **Web Application**
   ```bash
   # Configure Gmail App Password for OTP
   # Import Spring Boot project in NetBeans  
   # Run application (auto-starts server)
   ```

## 🔒 Security Architecture

Both applications implement enterprise-grade security measures:
- **BCrypt Password Hashing**: Military-grade encryption for all user credentials[2][1]
- **Session Management**: Secure server-side session handling
- **OTP Verification**: Two-factor authentication for account registration
- **Role-Based Access**: Hierarchical permission systems

## 🎯 Business Value

This dual-application architecture provides:
- **Operational Efficiency**: Streamlined staff workflows and customer self-service
- **Data Integrity**: Real-time synchronization across all platforms  
- **Scalability**: Modern tech stack supporting business growth
- **Security Compliance**: Industry-standard protection protocols
- **User Experience**: Intuitive interfaces for both staff and customers
