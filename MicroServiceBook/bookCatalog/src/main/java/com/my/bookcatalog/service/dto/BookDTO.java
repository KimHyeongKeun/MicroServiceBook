package com.my.bookcatalog.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.my.bookcatalog.domain.Book} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookDTO implements Serializable {

    private Long id;

    private String bookId;

    private String rentCnt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getRentCnt() {
        return rentCnt;
    }

    public void setRentCnt(String rentCnt) {
        this.rentCnt = rentCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDTO)) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", bookId='" + getBookId() + "'" +
            ", rentCnt='" + getRentCnt() + "'" +
            "}";
    }
}
