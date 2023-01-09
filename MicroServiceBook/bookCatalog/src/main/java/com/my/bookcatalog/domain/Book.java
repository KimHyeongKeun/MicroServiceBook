package com.my.bookcatalog.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "rent_cnt")
    private String rentCnt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
        return this.bookId;
    }

    public Book bookId(String bookId) {
        this.setBookId(bookId);
        return this;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getRentCnt() {
        return this.rentCnt;
    }

    public Book rentCnt(String rentCnt) {
        this.setRentCnt(rentCnt);
        return this;
    }

    public void setRentCnt(String rentCnt) {
        this.rentCnt = rentCnt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", bookId='" + getBookId() + "'" +
            ", rentCnt='" + getRentCnt() + "'" +
            "}";
    }
}
