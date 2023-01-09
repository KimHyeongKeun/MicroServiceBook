package com.my.bookcatalog.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.my.bookcatalog.domain.Book} entity. This class is used
 * in {@link com.my.bookcatalog.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bookId;

    private StringFilter rentCnt;

    private Boolean distinct;

    public BookCriteria() {}

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bookId = other.bookId == null ? null : other.bookId.copy();
        this.rentCnt = other.rentCnt == null ? null : other.rentCnt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBookId() {
        return bookId;
    }

    public StringFilter bookId() {
        if (bookId == null) {
            bookId = new StringFilter();
        }
        return bookId;
    }

    public void setBookId(StringFilter bookId) {
        this.bookId = bookId;
    }

    public StringFilter getRentCnt() {
        return rentCnt;
    }

    public StringFilter rentCnt() {
        if (rentCnt == null) {
            rentCnt = new StringFilter();
        }
        return rentCnt;
    }

    public void setRentCnt(StringFilter rentCnt) {
        this.rentCnt = rentCnt;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bookId, that.bookId) &&
            Objects.equals(rentCnt, that.rentCnt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, rentCnt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bookId != null ? "bookId=" + bookId + ", " : "") +
            (rentCnt != null ? "rentCnt=" + rentCnt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
