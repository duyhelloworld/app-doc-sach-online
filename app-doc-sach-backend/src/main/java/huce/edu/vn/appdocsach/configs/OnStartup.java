package huce.edu.vn.appdocsach.configs;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.repositories.database.CategoryRepo;
import huce.edu.vn.appdocsach.repositories.database.UserRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IBookService;
import huce.edu.vn.appdocsach.services.abstracts.core.IChapterService;
import huce.edu.vn.appdocsach.services.abstracts.core.ICommentService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.services.abstracts.file.IResourceService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnStartup implements CommandLineRunner {

	private final UserRepo userRepo;

	private final CategoryRepo categoryRepo;

	private final IBookService bookService;

	private final IChapterService chapterService;

	private final ICommentService commentService;
	
	private final ICloudinaryService cloudinaryService;

	private final PasswordEncoder passwordEncoder;

	private final IResourceService resourceService;

	@Override
	public void run(String... args) throws Exception {
		final String adminUsername = "admin";	
			
		// Init test user
		if (userRepo.count() == 0) {
			// Save avatar
			String defaultAvatarFileName = "default-avatar.png";
			String defaultAvatarUrl = cloudinaryService.save(resourceService.readStatic(defaultAvatarFileName), defaultAvatarFileName);
			String defaultPass = passwordEncoder.encode("12345678");
			
			User u1 = new User(adminUsername, "Adminitrastor", "admin@gmail.com", defaultAvatarUrl,
					defaultPass, TokenProvider.LOCAL, Role.ADMIN);
			User u2 = new User("duyhelloworld", "Pham Duy", "codedaovoiduy@gmail.com",
					defaultAvatarUrl, defaultPass, TokenProvider.LOCAL, Role.USER);
			User u3 = new User("wanhoang777", "Hoang Quan", "wanhoang777@gmail.com",
				defaultAvatarUrl, defaultPass, TokenProvider.LOCAL, Role.USER);
			userRepo.saveAll(List.of(u1, u2, u3));
			log.info("Saved user account : {}, {}", u2.getUsername(), u3.getUsername());
			log.info("Saved admin account : {}", u1.getUsername());
		}
		
		// Admin sẽ là người thêm các sách, thể loại mẫu
		User adminUser = userRepo.findByUsername(adminUsername).get();
		UserDetails admin = AuthenticatedUser.of(adminUser);
		var token = new UsernamePasswordAuthenticationToken(admin,null, admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

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
			log.info("Category inited : {}", categories);
		}

		// Init book
		if (bookService.isEmpty()) {
			CreateBookDto bookDto1 = new CreateBookDto();
			bookDto1.setTitle("Grand Blue");
			bookDto1.setDescription(
			"Câu chuyện xoay quanh Kitahara Iori được bắt đầu một cuộc sống mới tại một trường đại học gần biển ở thành phố Izu. ở đó cậu gặp được một cô gái thích lặn và tiếp theo đó sẽ diễn biến thế nào đọc rồi sẽ rõ =)).");
			bookDto1.setAuthor("Inoue Kenji");
			bookDto1.setCategoryIds(List.of(1, 2, 3, 4, 5));
			
			String fileName1 = "grand-blue.jpg";
			int bookId1 = bookService.addBook(
					new MockMultipartFile(fileName1, resourceService.readStatic(fileName1)),
						bookDto1);
			log.info("Added book {} : ", bookId1, bookDto1.getTitle(), fileName1);

			CreateBookDto bookDto2 = new CreateBookDto();
			bookDto2.setTitle("Sousou no frieren");
			bookDto2.setDescription(
							"Tổ đội anh hùng đã đánh bại được quỷ vương và kết thúc cuộc hành trình của họ. Nhưng thế chưa phải là hết, cuộc đời của cô nàng pháp sư Elf này sẽ còn rất dài, hơn cả những người đồng đội cũ của cô, một cuộc phiêu lưu mới để cô trải qua nhiều cung bậc cảm xúc, cũng như là học hỏi thêm về con người");
			bookDto2.setAuthor("Yamada Kanehito");
			bookDto2.setCategoryIds(List.of(3, 8, 6));

			String fileName2 = "sousou-no-frieren.jpg";
			int bookId = bookService.addBook(
					new MockMultipartFile(fileName2, resourceService.readStatic(fileName2)), bookDto2);
			log.info("Added book {} : ", bookId, bookDto1.getTitle(), fileName2);
		}

		if (chapterService.isEmpty()) {
			log.info("Empty 'Chapter' table. Let's insert manual some init data");
		}
		
		if (commentService.isEmpty()) {
			log.info("Empty 'Comment' table. Let's insert manual some init data");
		}
	}
}
