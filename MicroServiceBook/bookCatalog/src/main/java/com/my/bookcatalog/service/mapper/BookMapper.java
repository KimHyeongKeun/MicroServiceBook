package com.my.bookcatalog.service.mapper;

import com.my.bookcatalog.domain.Book;
import com.my.bookcatalog.service.dto.BookDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {}
