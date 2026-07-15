# فروشگاه چرم آروند - سیستم کامل تجارت الکترونیکی

## 📋 فهرست محتویات

1. [بخش‌های پیاده‌شده](#بخش‌های-پیاده‌شده)
2. [API Documentation](#api-documentation)
3. [روند استفاده](#روند-استفاده)
4. [نحوه راه‌اندازی](#نحوه-راه‌اندازی)
5. [ساختار پایگاه داده](#ساختار-پایگاه-داده)

---

## ✨ بخش‌های پیاده‌شده

### 1. **سیستم احراز هویت و لاگین** ✅
- صفحه ورود (`/login`)
- صفحه ثبت نام (`/signup`)
- استفاده از JWT برای احراز هویت
- مدیریت token در localStorage

**API Endpoints:**
```
POST /api/auth/login         - ورود کاربر
POST /api/auth/register      - ثبت نام کاربر جدید
POST /api/auth/logout        - خروج کاربر
```

### 2. **سبد خریدی** ✅
- نمایش کالاهای موجود در سبد
- افزودن/حذف محصول
- تغییر تعداد
- محاسبه خودکار قیمت کل

**API Endpoints:**
```
GET    /api/cart                    - دریافت سبد خریدی
POST   /api/cart/add                - افزودن محصول به سبد
PUT    /api/cart/update/{itemId}    - به‌روزرسانی تعداد
DELETE /api/cart/remove/{itemId}    - حذف محصول
DELETE /api/cart/clear              - خالی کردن سبد
```

**مثال درخواست افزودن به سبد:**
```bash
POST /api/cart/add?productId=1&quantity=2&variant=قهوه‌ای
```

### 3. **سیستم سفارشات** ✅
- ثبت سفارش از سبد خریدی
- مشاهده تاریخچه سفارشات
- پیگیری وضعیت سفارش
- لغو سفارش (در شرایط معین)

**API Endpoints:**
```
POST   /api/orders                  - ثبت سفارش جدید
GET    /api/orders                  - لیست سفارشات کاربر
GET    /api/orders/{orderId}        - جزئیات سفارش
GET    /api/orders/all              - تمام سفارشات بدون صفحه‌بندی
DELETE /api/orders/{orderId}        - لغو سفارش
PUT    /api/orders/{orderId}/status - تغییر وضعیت (Admin)
```

**مثال ثبت سفارش:**
```json
POST /api/orders
{
    "recipientName": "علی احمدی",
    "phoneNumber": "09123456789",
    "shippingAddress": "تهران - خیابان ولیعصر",
    "shippingCost": 30000,
    "discountAmount": 0,
    "notes": "لطفاً در ساعات کاری تحویل دهید"
}
```

### 4. **نظرات و امتیازات محصولات** ✅
- ثبت نظر و امتیاز برای محصول
- مشاهده نظرات تایید شده
- ویرایش و حذف نظر خود
- محاسبه میانگین امتیاز

**API Endpoints:**
```
POST   /api/reviews                         - ثبت نظر جدید
GET    /api/reviews/product/{productId}    - نظرات محصول
PUT    /api/reviews/{reviewId}             - ویرایش نظر
DELETE /api/reviews/{reviewId}             - حذف نظر
GET    /api/reviews/product/{id}/rating    - میانگین امتیاز
GET    /api/reviews/product/{id}/count     - تعداد نظرات
GET    /api/reviews/user                   - نظرات کاربر
```

**مثال ثبت نظر:**
```json
POST /api/reviews
{
    "productId": 5,
    "rating": 4,
    "title": "محصول خوب",
    "comment": "کیفیت خوب و قیمت مناسب داشت"
}
```

### 5. **صفحات Frontend** ✅
- **صفحه جزئیات محصول** (`/product/{id}`)
  - نمایش تصاویر محصول
  - قیمت و تخفیف
  - نظرات و امتیازات
  - انتخاب رنگ و تعداد
  - افزودن به سبد

- **صفحه سبد خریدی** (`/cart`)
  - نمایش کالاهای سبد
  - تغییر تعداد
  - حذف محصول
  - خلاصه هزینه‌ها

- **صفحه چکاوت** (`/checkout`)
  - تکمیل اطلاعات تحویل
  - انتخاب روش ارسال
  - مشاهده خلاصه سفارش
  - ثبت سفارش

- **داشبورد کاربر** (`/dashboard`)
  - مشاهده سفارشات
  - اطلاعات پروفایل
  - تاریخچه خریدی
  - نظرات ثبت شده

---

## 🔌 API Documentation

### درخواست‌ها (Requests)

#### سرصفحه‌های مورد نیاز:
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

#### مثال‌های کامل:

**1. ورود کاربر:**
```bash
curl -X POST http://localhost:8091/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "ali@example.com",
    "password": "password123"
  }'
```

**2. افزودن به سبد:**
```bash
curl -X POST http://localhost:8091/api/cart/add?productId=1&quantity=2&variant=قهوه‌ای \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**3. مشاهده سبد:**
```bash
curl -X GET http://localhost:8091/api/cart \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**4. ثبت سفارش:**
```bash
curl -X POST http://localhost:8091/api/orders \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "recipientName": "علی احمدی",
    "phoneNumber": "09123456789",
    "shippingAddress": "تهران - خیابان ولیعصر",
    "shippingCost": 30000,
    "discountAmount": 0
  }'
```

### پاسخ‌ها (Responses)

**پاسخ موفق:**
```json
{
  "success": true,
  "message": "عملیات موفقیت‌آمیز بود",
  "data": {
    "id": 1,
    "totalPrice": 150000,
    ...
  }
}
```

**پاسخ خطا:**
```json
{
  "success": false,
  "message": "پیام خطا",
  "errorType": "NOT_FOUND"
}
```

---

## 🛠️ روند استفاده

### جریان خرید مرحله به مرحله:

```
1. ثبت نام / ورود
   └─> POST /api/auth/register یا /api/auth/login

2. مرور محصولات
   └─> GET /api/products یا /product/{id}

3. افزودن به سبد
   └─> POST /api/cart/add

4. مشاهده سبد
   └─> GET /api/cart

5. رفتن به صفحه چکاوت
   └─> صفحه /checkout

6. ثبت سفارش
   └─> POST /api/orders

7. مشاهده سفارش
   └─> GET /api/orders/{orderId}

8. ثبت نظر (اختیاری)
   └─> POST /api/reviews
```

---

## 📦 نحوه راه‌اندازی

### 1. نیازمندی‌ها:
- Java 11+
- PostgreSQL
- Maven
- Redis (برای caching)

### 2. تنظیمات پایگاه داده:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/arvanddb
spring.datasource.username=arvanduser
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 3. نصب dependencies:
```bash
mvn clean install
```

### 4. اجرای برنامه:
```bash
mvn spring-boot:run
```

### 5. صفحات دسترسی:
- صفحه اصلی: `http://localhost:8091/`
- ورود: `http://localhost:8091/login`
- ثبت نام: `http://localhost:8091/signup`
- سبد خریدی: `http://localhost:8091/cart`
- چکاوت: `http://localhost:8091/checkout`
- داشبورد: `http://localhost:8091/dashboard`

---

## 📊 ساختار پایگاه داده

### جداول جدید:

#### جدول `orders`:
```sql
- id (PK)
- user_id (FK)
- total_price
- shipping_cost
- discount_amount
- final_price
- status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- shipping_address
- phone_number
- recipient_name
- notes
- created_at
- updated_at
```

#### جدول `order_items`:
```sql
- id (PK)
- order_id (FK)
- product_id (FK)
- quantity
- unit_price
- total_price
- variant
```

#### جدول `carts`:
```sql
- id (PK)
- user_id (FK, UNIQUE)
- total_price
- item_count
- created_at
- updated_at
```

#### جدول `cart_items`:
```sql
- id (PK)
- cart_id (FK)
- product_id (FK)
- quantity
- unit_price
- total_price
- variant
```

#### جدول `reviews`:
```sql
- id (PK)
- product_id (FK)
- user_id (FK)
- rating (1-5)
- title
- comment
- helpful_count
- status (PENDING, APPROVED, REJECTED, HIDDEN)
- created_at
- updated_at
- UNIQUE(product_id, user_id)
```

---

## 🔒 امنیت

- تمام API‌ها نیاز به احراز هویت JWT دارند
- رمز عبورها با bcrypt رمزگذاری می‌شوند
- Spring Security برای مدیریت دسترسی‌ها
- CSRF Protection فعال است

---

## 📱 قابلیت واکنش‌پذیری

تمام صفحات برای دستگاه‌های موبایل، تبلت و دسکتاپ بهینه شده‌اند.

---

## 🚀 مراحل بعدی

- [ ] اتصال درگاه پرداخت (زرین‌پال، بانک رسالت)
- [ ] سیستم نوتیفیکیشن (ایمیل، SMS)
- [ ] کدهای تخفیف
- [ ] سیستم رتبه‌بندی فروشنده
- [ ] پشتیبانی چند زبان
- [ ] سیستم سؤالات متداول
- [ ] نسخه اپلیکیشن موبایلی

---

## 📞 پشتیبانی

برای سؤالات یا مشکلات، لطفاً با تیم توسعه تماس بگیرید.

---

**آخرین به‌روزرسانی:** 2026-07-15

**نسخه:** 1.0.0
