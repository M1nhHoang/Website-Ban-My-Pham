Create database dbQuanLiBanMiPham
go
use dbQuanLiBanMiPham

create table tbCategory
(
	category_id int identity(1, 1) not null primary key,
	category_name nvarchar(max) not null,
	image nvarchar(max) not null,
	description nvarchar(max),
)

create table tbProduct
(
	product_id int identity(1, 1) not null primary key,
	product_name nvarchar(max) not null,
	description nvarchar(max) not null,
	image nvarchar(max) not null,
	price float not null,
	quantity int not null,
	category_id int,

	constraint FK_categoryid_tbProduct foreign key(category_id) REFERENCES tbCategory(category_id)
		on delete
			cascade
		on update
			cascade
)

create table tbUser
(
	user_id int identity(1, 1) not null primary key,
	name nvarchar(50) not null unique,
	password nvarchar(50) not null,
	type nvarchar(25) not null
)

create table tbCart
(
	cart_id int identity(1, 1) not null primary key,
	user_id int not null,

	constraint FK_userId_tbCart foreign key(user_id) REFERENCES tbUser(user_id)
		on delete
			cascade
		on update
			cascade
)

create table tbOrder
(
	oder_id int identity(1, 1) not null primary key,
	cart_id int not null,
	product_id int not null,
	quantity int not null,
	createAt date not null,

	constraint FK_cartId_tbOrder foreign key(cart_id) REFERENCES tbCart(cart_id)
		on delete
			cascade
		on update
			cascade,

	constraint FK_productId_tbOrder foreign key(product_id) REFERENCES tbProduct(product_id)
		on delete
			cascade
		on update
			cascade
)

-- Insert data into tbCategory
INSERT INTO tbCategory (category_name, image, description)
VALUES
    ('Skincare', 'cat-4.jpg', null),
    ('Makeup', 'cat-4.jpg', null),
    ('Haircare', 'cat-4.jpg', null),
    ('Fragrance', 'cat-4.jpg', null),
    ('Body Care', 'cat-4.jpg', null);

-- Insert data into tbProduct
INSERT INTO tbProduct (product_name, description, image, price, quantity, category_id)
VALUES
    ('Cleanser', 'Gentle facial cleanser for all skin types', 'product-8.jpg', 25.99, 50, 1),
    ('Mascara', 'Lengthening and volumizing mascara', 'product-8.jpg', 12.99, 30, 2),
    ('Shampoo', 'Moisturizing shampoo for dry hair', 'product-8.jpg', 15.99, 20, 3),
    ('Perfume', 'Floral and citrus fragrance', 'product-8.jpg', 49.99, 10, 4),
    ('Body Lotion', 'Hydrating body lotion with shea butter', 'product-8.jpg', 19.99, 40, 5);

-- Insert data into tbUser
INSERT INTO tbUser (name, password, type)
VALUES
    ('JohnDoe', 'password123', 'Customer'),
    ('JaneSmith', 'pass456', 'Admin'),
    ('AmyJohnson', 'securePW', 'Customer'),
    ('DavidBrown', '123abc', 'Customer'),
    ('AdminUser', 'adminpass', 'Admin');

-- Insert data into tbCart
INSERT INTO tbCart (user_id)
VALUES
    (1),
    (3),
    (2),
    (4),
    (5);

-- Insert data into tbOrder
INSERT INTO tbOrder (cart_id, product_id, quantity, createAt)
VALUES
    (1, 2, 2, '2023-05-01'),
    (2, 1, 1, '2023-05-02'),
    (3, 5, 3, '2023-05-03'),
    (4, 3, 2, '2023-05-04'),
    (5, 4, 1, '2023-05-05');

go
-- procedure
Create or Alter PROC [dbo].[psGetSanPham](
	@category_id int
)
AS
BEGIN Tran
	if (@category_id is not null)
		select * from tbProduct sp
		left join tbCategory dm on dm.category_id = sp.category_id
		where sp.category_id = @category_id
	else 
		select * from tbProduct sp
		left join tbCategory dm on dm.category_id = sp.category_id
	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psGetChiTietSanPham](
	@product_id int
)
AS
BEGIN Tran
	select * from tbProduct sp
	where sp.product_id = @product_id
	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psGetDanhMuc]
AS
BEGIN Tran
	select * from tbCategory dm
	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psSuaSanPham](
	@maSanPham int,
	@tenSanPham varchar(200),
	@donGia numeric(18, 0), 
	@soLuong numeric(18, 0), 
	@hinhAnhn varchar(50), 
	@moTa nvarchar(MAX), 
	@maDanhMuc int)
AS
BEGIN Tran
	update tbProduct
	set product_name = @tenSanPham, price = @donGia, quantity = @soLuong, image = @hinhAnhn, description = @moTa, category_id = @maDanhMuc
	where product_id = @maSanPham;

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psThemSanPham](
	@tenSanPham varchar(200),
	@donGia numeric(18, 0), 
	@soLuong numeric(18, 0), 
	@hinhAnhn varchar(50), 
	@moTa nvarchar(MAX), 
	@maDanhMuc int)
AS
BEGIN Tran
	insert into tbProduct(product_name, quantity, image, price, category_id, description)
	values
		(@tenSanPham, @soLuong, @hinhAnhn, @donGia, @maDanhMuc, @moTa)

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psXoaSanPham](
	@maSanPham int
)
AS
BEGIN Tran
	delete 
	from tbProduct
	where product_id = @maSanPham

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psCreateCart](
	@user_id int
)
AS
BEGIN Tran
	IF NOT EXISTS (SELECT top 1 * FROM tbCart WHERE user_id = @user_id)
	BEGIN
		insert into tbCart (user_id) 
		values (@user_id)
	END

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psGetCartDetails](
	@user_id int
)
AS
BEGIN Tran
	select * from tbProduct as sp
	right join (
		select cgh.*, u.user_id as N'user id', u.name, u.password, u.type
		from tbOrder as cgh
			left join tbCart gh on gh.user_id = cgh.cart_id
			left join tbUser u on gh.user_id = u.user_id
		where gh.user_id = @user_id
		) as ct on ct.product_id = sp.product_id
	left join tbCategory as dm on dm.category_id = sp.category_id
	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psAddCartDetails](
	@user_id int,
	@product_id int,
	@quantity int
)
AS
BEGIN Tran
	IF EXISTS (SELECT top 1 * FROM tbOrder WHERE product_id = @product_id AND (SELECT top 1 user_id FROM tbCart WHERE user_id = @user_id) = cart_id)
	BEGIN
		UPDATE tbOrder SET quantity = quantity + @quantity
		WHERE (SELECT top 1 user_id FROM tbCart WHERE user_id = @user_id) = cart_id AND product_id = @product_id
	END
	ELSE
	BEGIN
		insert into tbOrder (cart_id, product_id, quantity, createAt)
		values ((select top 1 user_id from tbCart where user_id = @user_id), @product_id, @quantity,  GETDATE())
	END

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psRemoveCartDetails](
	@user_id int,
	@product_id int
)
AS
BEGIN Tran
	delete from tbOrder
	where (SELECT top 1 user_id FROM tbCart WHERE user_id = @user_id) = cart_id AND product_id = @product_id

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psEditCartDetails](
	@user_id int,
	@product_id int,
	@quantity int
)
AS
BEGIN Tran
	update tbOrder
	set product_id = @product_id, quantity = @quantity
	where 
		(SELECT top 1 user_id FROM tbCart WHERE user_id = @user_id) = cart_id

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

Create or Alter PROC [dbo].[psOrder](
	@user_id int,
	@product_id int,
	@quantity int
)
AS
BEGIN TRANSACTION;

DECLARE @current_quantity INT;

SELECT @current_quantity = quantity FROM tbProduct WHERE product_id = @product_id;

IF (@current_quantity < @quantity)
BEGIN
	PRINT 'Not enough stock';
	ROLLBACK TRANSACTION;
END
ELSE
BEGIN
	DELETE FROM tbOrder
	WHERE (SELECT TOP 1 user_id FROM tbCart WHERE user_id = @user_id) = cart_id 
	AND product_id = @product_id;

	UPDATE tbProduct
	SET quantity = quantity - @quantity
	WHERE product_id = @product_id;

	IF @@ERROR <> 0
		ROLLBACK TRANSACTION;
	ELSE
		COMMIT TRANSACTION;
END;
go

Create or Alter PROC [dbo].[psRegisterUserAccount](@username varchar(50), @password varchar(50))
AS
BEGIN Tran
	insert into tbUser (name, password, type)
	values
		(@username, @password, 'Guest')

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go


Create or Alter PROC [dbo].[psGetUserAccount](@username varchar(50))
AS
BEGIN Tran
	select * from tbUser where name = @username

	if (@@ERROR <> 0)
		rollback Tran
	else 
		commit Tran
go

[psAddCartDetails] 2, 4, 9
go
[psGetCartDetails] 6