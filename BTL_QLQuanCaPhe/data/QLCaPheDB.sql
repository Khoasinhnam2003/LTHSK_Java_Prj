-- Tạo Database
CREATE DATABASE QLCaPhe;
GO

-- Sử dụng Database vừa tạo
USE QLCaPhe;
GO

-- Tạo bảng Customer
CREATE TABLE customer (
    customer_id NVARCHAR(10) PRIMARY KEY,
    customer_name NVARCHAR(50) NOT NULL,
    phone NVARCHAR(10) NOT NULL,
    point INT NOT NULL
);
GO

-- Tạo bảng Employee
CREATE TABLE employee (
    employee_id NVARCHAR(10) PRIMARY KEY,
    employee_name NVARCHAR(50) NOT NULL,
    dob DATE,
    email NVARCHAR(50) NOT NULL,
    role NVARCHAR(20) NOT NULL,
    password NVARCHAR(10) NOT NULL
);
GO

-- Tạo bảng Promotion
CREATE TABLE promotion (
    promotion_id NVARCHAR(10) PRIMARY KEY,
    promotion_name NVARCHAR(100) NOT NULL,
    discount FLOAT NOT NULL,
    startdate DATETIME NOT NULL,
    enddate DATETIME NOT NULL
);
GO

-- Tạo bảng Ban
CREATE TABLE tabless (
    table_id NVARCHAR(10) PRIMARY KEY,
    num_of_seats INT NOT NULL,
    floors NVARCHAR(50) NOT NULL,
    table_status NVARCHAR(20) NOT NULL
);
GO

-- Tạo bảng Invoice
CREATE TABLE Invoice (
    invoice_id NVARCHAR(10) PRIMARY KEY,      -- Mã hóa đơn
    invoice_date DATETIME NOT NULL,           -- Ngày đặt
    table_id NVARCHAR(10) NOT NULL,           -- Mã bàn
    customer_id NVARCHAR(10) NULL,            -- Mã khách hàng
	employee_name NVARCHAR(50) NOT NULL,		--Tên nhân viên
    employee_id NVARCHAR(10) NOT NULL,        -- Mã nhân viên
    promotion_id NVARCHAR(10) NULL,           -- Mã khuyến mãi
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
    FOREIGN KEY (promotion_id) REFERENCES promotion(promotion_id),
    FOREIGN KEY (table_id) REFERENCES tabless(table_id) -- Tham chiếu mã bàn
);
GO

-- Tạo bảng Category
CREATE TABLE category (
    category_id NVARCHAR(10) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL
);
GO

-- Tạo bảng Drink
CREATE TABLE drink (
    drink_id NVARCHAR(10) PRIMARY KEY,
    drink_name NVARCHAR(100) NOT NULL,
    image NVARCHAR(255) NOT NULL,
    description NVARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    category_id NVARCHAR(10) NOT NULL,
    vat FLOAT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
GO

-- Tạo bảng Ingredient
CREATE TABLE ingredient (
    ingredient_id NVARCHAR(10) PRIMARY KEY,
    ingredient_name NVARCHAR(100) NOT NULL,
    quantity FLOAT NOT NULL,
    description NVARCHAR(255) NOT NULL,
    date_of_entry DATE NOT NULL,
    expiration_date DATE NOT NULL,
    unit NVARCHAR(10) NOT NULL,
    price FLOAT NOT NULL
);
GO

-- Tạo bảng InvoiceDetail
CREATE TABLE InvoiceDetail (
    invoice_id NVARCHAR(10),                  -- Mã hóa đơn
    product_id NVARCHAR(10) NOT NULL,         -- Mã sản phẩm
    product_name NVARCHAR(100) NOT NULL,      -- Tên sản phẩm
    quantity INT NOT NULL,                    -- Số lượng
    unit_price FLOAT NOT NULL,                -- Đơn giá
    total_amount FLOAT NOT NULL,              -- Thành tiền
    total_price FLOAT NOT NULL,               -- Tổng tiền (bao gồm VAT)
    PRIMARY KEY (invoice_id, product_id),     -- Khóa chính kết hợp
    FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_id),
    FOREIGN KEY (product_id) REFERENCES drink(drink_id)
);
GO

-- Tạo bảng DrinkDetail
CREATE TABLE drink_detail (
    drink_id NVARCHAR(10),
    ingredient_id NVARCHAR(10) NOT NULL,
    quantity FLOAT NOT NULL,
    PRIMARY KEY (drink_id, ingredient_id),
    FOREIGN KEY (drink_id) REFERENCES drink(drink_id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(ingredient_id)
);
GO

-- Chèn dữ liệu bảng Employee
INSERT INTO employee VALUES 
('NV1', N'Phạm Anh Khoa', '2003-05-10', 'anhkhoa22022003@gmail.com', 'Admin', '1234');
GO

-- Chèn dữ liệu bảng Category
INSERT INTO category VALUES 
('CAT1', N'Nước ép'),
('CAT2', N'Cà phê'),
('CAT3', N'Sinh tố');
GO

-- Chèn dữ liệu bảng Drink
INSERT INTO drink VALUES 
('D001', N'Coca Cola', 'coca.jpg', N'Nước ngọt có ga', 15000, 'CAT1', 0.1),
('D002', N'Latte', 'latte.jpg', N'Cà phê sữa', 30000, 'CAT2', 0.1),
('D003', N'Sinh tố bơ', 'bo.jpg', N'Sinh tố trái cây', 25000, 'CAT3', 0.1);
GO

-- Chèn dữ liệu bảng Ingredient
INSERT INTO ingredient VALUES 
('NL0', N'Đường', 10, N'Đường trắng tinh luyện', '2024-01-01', '2024-12-31', N'Gram', 20000),
('NL1', N'Cà phê', 5, N'Cà phê nguyên chất', '2024-02-01', '2024-12-31', N'Gram', 150000),
('NL2', N'Sữa', 20, N'Sữa tươi', '2024-03-01', '2024-08-01', N'Liter', 20000);
GO

-- Chèn dữ liệu bảng DrinkDetail
INSERT INTO drink_detail VALUES 
('D001', 'NL0', 0.02),
('D002', 'NL1', 0.05),
('D002', 'NL2', 0.03),
('D003', 'NL0', 0.03),
('D003', 'NL2', 0.04);
GO

-- Chèn dữ liệu bảng Ban (Table)
INSERT INTO tabless VALUES 
('B001', 4, N'Tầng 1', N'Trống'),
('B002', 2, N'Tầng 1', N'Có khách'),
('B003', 6, N'Tầng 2', N'Trống'),
('B004', 4, N'Tầng 2', N'Có khách');
GO

-- Chèn dữ liệu bảng Customer
INSERT INTO customer VALUES 
('Cus0', N'Nguyễn Văn A', '0787945874', 100);
GO

-- Chèn dữ liệu bảng Promotion
INSERT INTO promotion VALUES 
('KM1', N'Khuyến mãi Tết', 20, '2024-01-01', '2024-02-01'),
('KM2', N'Khuyến mãi Hè', 15, '2024-06-01', '2024-06-30'),
('KM3', N'Khuyến mãi Noel', 25, '2024-12-01', '2024-12-25');
GO

