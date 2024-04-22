package huce.edu.vn.appdocsach.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.repositories.UserRepo;
import huce.edu.vn.appdocsach.services.auth.JwtService;
import huce.edu.vn.appdocsach.services.core.BookService;
import huce.edu.vn.appdocsach.services.core.ChapterService;
import huce.edu.vn.appdocsach.services.core.CommentService;

@Component
public class OnStartup implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BookService bookService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private AppLogger<OnStartup> logger = new AppLogger<>(OnStartup.class);

    @Override
    public void run(String... args) throws Exception {

        // init example category
        if (categoryRepo.count() == 0) {

            Category c1 = new Category();
            c1.setName("Comedy");
            c1.setDescription(
                    "Thể loại có nội dung trong sáng và cảm động, thường có các tình tiết gây cười, các xung đột nhẹ nhàng");

            Category c2 = new Category();
            c2.setName("Ecchi");
            c2.setDescription("Thường có những tình huống nhạy cảm nhằm lôi cuốn người xem");

            Category c3 = new Category();
            c3.setName("Slice of life");
            c3.setDescription("Nói về cuộc sống đời thường");

            Category c4 = new Category();
            c4.setName("Seinen");
            c4.setDescription(
                    "Thể loại của manga thường nhằm vào những đối tượng nam 18 đến 30 tuổi, nhưng người xem có thể lớn tuổi hơn, với một vài bộ truyện nhắm đến các doanh nhân nam quá 40. Thể loại này có nhiều phong cách riêng biệt , nhưng thể loại này có những nét riêng biệt, thường được phân vào những phong cách nghệ thuật rộng hơn và phong phú hơn về chủ đề, có các loại từ mới mẻ tiên tiến đến khiêu dâm");

            Category c5 = new Category();
            c5.setName("Anime");
            c5.setDescription("Truyện đã được chuyển thể thành film Anime");

            Category c6 = new Category();
            c6.setName("Fantasy");
            c6.setDescription(
                    "Thể loại xuất phát từ trí tưởng tượng phong phú, từ pháp thuật đến thế giới trong mơ thậm chí là những câu chuyện thần tiênThể loại xuất phát từ trí tưởng tượng phong phú, từ pháp thuật đến thế giới trong mơ thậm chí là những câu chuyện thần tiên");

            Category c7 = new Category();
            c7.setName("Supernatural");
            c7.setDescription(
                    "Thể hiện những sức mạnh đáng kinh ngạc và không thể giải thích được, chúng thường đi kèm với những sự kiện trái ngược hoặc thách thức với những định luật vật lý");

            Category c8 = new Category();
            c8.setName("Drama");
            c8.setDescription(
                    "Thể loại mang đến cho người xem những cảm xúc khác nhau: buồn bã, căng thẳng thậm chí là bi phẫn");

            List<Category> categories = List.of(c1, c2, c3, c4, c5, c6, c7, c8);
            categoryRepo.saveAll(categories);
            logger.info("Category inited : {}", categories);
        }

        // Init book
        if (bookService.isEmpty()) {
            CreateBookDto bookDto1 = CreateBookDto.builder()
                    .title("Grand Blue")
                    .description(
                            "Câu chuyện xoay quanh Kitahara Iori được bắt đầu một cuộc sống mới tại một trường đại học gần biển ở thành phố Izu. ở đó cậu gặp được một cô gái thích lặn và tiếp theo đó sẽ diễn biến thế nào đọc rồi sẽ rõ =)).")
                    .author("Inoue Kenji")
                    .categoryIds(List.of(1, 2, 3, 4, 5))
                    .build();
            String fileName1 = "grand-blue.jpg";
            int bookId1 = bookService.addBook(bookDto1, Files.readAllBytes(Path.of(AppConst.AVATAR_DIR + fileName1)),
                    fileName1);
            logger.info("Added book {} : {}", bookId1, bookDto1.getTitle());

            CreateBookDto bookDto2 = CreateBookDto.builder()
                    .title("Sousou no frieren")
                    .description(
                            "Tổ đội anh hùng đã đánh bại được quỷ vương và kết thúc cuộc hành trình của họ. Nhưng thế chưa phải là hết, cuộc đời của cô nàng pháp sư Elf này sẽ còn rất dài, hơn cả những người đồng đội cũ của cô, một cuộc phiêu lưu mới để cô trải qua nhiều cung bậc cảm xúc, cũng như là học hỏi thêm về con người")
                    .author("Yamada Kanehito")
                    .categoryIds(List.of(3, 8, 6))
                    .build();
            String fileName2 = "sousou-no-frieren.jpg";
            int bookId = bookService.addBook(bookDto2,
                    Files.readAllBytes(Path.of(AppConst.AVATAR_DIR + fileName2)), fileName2);
            logger.info("Added book {} : {}", bookId, bookDto1.getTitle());

        }
        if (chapterService.isEmpty()) {
            logger.info("Empty chapter table. Insert manual");
        }
        if (commentService.isEmpty()) {
            logger.info("Empty comment table. Insert manual");
        }

        // Init test user
        if (userRepo.count() == 0) {
            String defaultPass = passwordEncoder.encode("password");
            User u1 = new User("admin", "Adminitrastor", "admin@gmail.com", AppConst.DEFAULT_AVATAR_URL, defaultPass,
                    TokenProvider.LOCAL, Role.ADMIN);
            User u2 = new User("duyhelloworld", "Pham Duy", "codedaovoiduy@gmail.com", AppConst.DEFAULT_AVATAR_URL,
                    defaultPass, TokenProvider.LOCAL, Role.USER);
            User u3 = new User("wanhoang777", "Hoang Quan", "wanhoang777@gmail.com", AppConst.DEFAULT_AVATAR_URL,
                    defaultPass, TokenProvider.LOCAL, Role.USER);
            userRepo.saveAll(List.of(u1, u2, u3));
            logger.info("Saved user account : {}, {}", u2.getUsername(), u3.getUsername());
            logger.info("Saved admin account : {}", u1.getUsername());
        }

        // Build pre-token for admin account
        Optional<User> adminAccount = userRepo.findByUsername("admin");
        if (adminAccount.isEmpty()) {
            logger.error("Not found admin account");
        } else {
            logger.info("Admin account token : {}", jwtService.buildToken(adminAccount.get()));
        }
    }
}
