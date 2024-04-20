## Tải maven
`mvn --version`



## Lấy key cloudinary
- [Đăng kí tài khoản](https://cloudinary.com/users/register_free)
- Lấy 3 giá trị, thay tương ứng vào [](./src/main/resources/application.yml)

## Thay user-password của MySQL cũng ở file trên
(Hoặc có thể chạy [create-user.sql](./src/main/resources/create-user.sql))



## Chạy app
1. Chạy bằng cli: 
-   `mvn spring-boot:run`
2. Chạy bằng giao diện:
- Mở VScode. Tải extension **Debugger for java**
- Mở [AppDocSachApplication.java](./src/main/java/huce/edu/vn/appdocsach/AppDocSachApplication.java) để run


## Dùng thử 
- Chạy `http://localhost:8080` để mở swagger
- Nếu cần xác thực thì user-pass ở [OnStartup.java](./src/main/java/huce/edu/vn/appdocsach/utils/OnStartup.java)