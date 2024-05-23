package huce.edu.vn.appdocsach.cachings.impl;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import huce.edu.vn.appdocsach.cachings.IBookRedisService;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.utils.AppLogger;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookRedisService implements IBookRedisService {

    RedisTemplate<String, Object> template;

    ObjectMapper objectMapper;

    AppLogger<BookRedisService> logger;

    public BookRedisService(RedisTemplate<String, Object> template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
        this.logger = new AppLogger<>(BookRedisService.class);
    }
    
    @Override
    public void clear() {
        RedisConnectionFactory connectionFactory = template.getConnectionFactory();
        if (connectionFactory != null) {
            connectionFactory.getConnection().serverCommands();
        } else {
            logger.error("Connect fail to redis");
        }
    }

    @Override
    public PagingResponse<SimpleBookDto> getAllBookSimple(FindBookDto findBookDto) {
        String key = getKey(findBookDto);
        String json = (String) template.opsForValue().get(key);
        PagingResponse<SimpleBookDto> response = null;
        try {
            if (StringUtils.hasLength(json)) {
                response = objectMapper.readValue(json, new TypeReference<PagingResponse<SimpleBookDto>>(){});
            }
        } catch (JsonProcessingException e) {
            logger.error("Error while read from redis : key = ", key, "error = ", e.getMessage());
        }
        return response;
    }

    private String getKey(FindBookDto findBookDto) {
        // format theo kiểu : {tên}:{số trang}:{kích thước trang}
        return String.format("all_books:%d:%d", findBookDto.getPageNumber(), findBookDto.getPageSize());
    }

    @Override
    public void save(PagingResponse<SimpleBookDto> bookDto, FindBookDto findBookDto) {
        String key = getKey(findBookDto);
        try {
            template.opsForValue().set(key, objectMapper.writeValueAsString(bookDto));
        } catch (JsonProcessingException e) {
            logger.error("Error when save temporal data to redis : ", e.getMessage());            
        }
    }
}
