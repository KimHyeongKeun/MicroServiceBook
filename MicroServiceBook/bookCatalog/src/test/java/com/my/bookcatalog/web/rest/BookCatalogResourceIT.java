package com.my.bookcatalog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.my.bookcatalog.IntegrationTest;
import com.my.bookcatalog.domain.BookCatalog;
import com.my.bookcatalog.repository.BookCatalogRepository;
import com.my.bookcatalog.service.criteria.BookCatalogCriteria;
import com.my.bookcatalog.service.dto.BookCatalogDTO;
import com.my.bookcatalog.service.mapper.BookCatalogMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BookCatalogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookCatalogResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/book-catalogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookCatalogRepository bookCatalogRepository;

    @Autowired
    private BookCatalogMapper bookCatalogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookCatalogMockMvc;

    private BookCatalog bookCatalog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookCatalog createEntity(EntityManager em) {
        BookCatalog bookCatalog = new BookCatalog().title(DEFAULT_TITLE).author(DEFAULT_AUTHOR).description(DEFAULT_DESCRIPTION);
        return bookCatalog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookCatalog createUpdatedEntity(EntityManager em) {
        BookCatalog bookCatalog = new BookCatalog().title(UPDATED_TITLE).author(UPDATED_AUTHOR).description(UPDATED_DESCRIPTION);
        return bookCatalog;
    }

    @BeforeEach
    public void initTest() {
        bookCatalog = createEntity(em);
    }

    @Test
    @Transactional
    void createBookCatalog() throws Exception {
        int databaseSizeBeforeCreate = bookCatalogRepository.findAll().size();
        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);
        restBookCatalogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeCreate + 1);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createBookCatalogWithExistingId() throws Exception {
        // Create the BookCatalog with an existing ID
        bookCatalog.setId(1L);
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        int databaseSizeBeforeCreate = bookCatalogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookCatalogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookCatalogs() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookCatalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getBookCatalog() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get the bookCatalog
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL_ID, bookCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookCatalog.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getBookCatalogsByIdFiltering() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        Long id = bookCatalog.getId();

        defaultBookCatalogShouldBeFound("id.equals=" + id);
        defaultBookCatalogShouldNotBeFound("id.notEquals=" + id);

        defaultBookCatalogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookCatalogShouldNotBeFound("id.greaterThan=" + id);

        defaultBookCatalogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookCatalogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where title equals to DEFAULT_TITLE
        defaultBookCatalogShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookCatalogList where title equals to UPDATED_TITLE
        defaultBookCatalogShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookCatalogShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookCatalogList where title equals to UPDATED_TITLE
        defaultBookCatalogShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where title is not null
        defaultBookCatalogShouldBeFound("title.specified=true");

        // Get all the bookCatalogList where title is null
        defaultBookCatalogShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBookCatalogsByTitleContainsSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where title contains DEFAULT_TITLE
        defaultBookCatalogShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bookCatalogList where title contains UPDATED_TITLE
        defaultBookCatalogShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where title does not contain DEFAULT_TITLE
        defaultBookCatalogShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bookCatalogList where title does not contain UPDATED_TITLE
        defaultBookCatalogShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where author equals to DEFAULT_AUTHOR
        defaultBookCatalogShouldBeFound("author.equals=" + DEFAULT_AUTHOR);

        // Get all the bookCatalogList where author equals to UPDATED_AUTHOR
        defaultBookCatalogShouldNotBeFound("author.equals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByAuthorIsInShouldWork() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where author in DEFAULT_AUTHOR or UPDATED_AUTHOR
        defaultBookCatalogShouldBeFound("author.in=" + DEFAULT_AUTHOR + "," + UPDATED_AUTHOR);

        // Get all the bookCatalogList where author equals to UPDATED_AUTHOR
        defaultBookCatalogShouldNotBeFound("author.in=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByAuthorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where author is not null
        defaultBookCatalogShouldBeFound("author.specified=true");

        // Get all the bookCatalogList where author is null
        defaultBookCatalogShouldNotBeFound("author.specified=false");
    }

    @Test
    @Transactional
    void getAllBookCatalogsByAuthorContainsSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where author contains DEFAULT_AUTHOR
        defaultBookCatalogShouldBeFound("author.contains=" + DEFAULT_AUTHOR);

        // Get all the bookCatalogList where author contains UPDATED_AUTHOR
        defaultBookCatalogShouldNotBeFound("author.contains=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByAuthorNotContainsSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where author does not contain DEFAULT_AUTHOR
        defaultBookCatalogShouldNotBeFound("author.doesNotContain=" + DEFAULT_AUTHOR);

        // Get all the bookCatalogList where author does not contain UPDATED_AUTHOR
        defaultBookCatalogShouldBeFound("author.doesNotContain=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where description equals to DEFAULT_DESCRIPTION
        defaultBookCatalogShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the bookCatalogList where description equals to UPDATED_DESCRIPTION
        defaultBookCatalogShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBookCatalogShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the bookCatalogList where description equals to UPDATED_DESCRIPTION
        defaultBookCatalogShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where description is not null
        defaultBookCatalogShouldBeFound("description.specified=true");

        // Get all the bookCatalogList where description is null
        defaultBookCatalogShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllBookCatalogsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where description contains DEFAULT_DESCRIPTION
        defaultBookCatalogShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the bookCatalogList where description contains UPDATED_DESCRIPTION
        defaultBookCatalogShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBookCatalogsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        // Get all the bookCatalogList where description does not contain DEFAULT_DESCRIPTION
        defaultBookCatalogShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the bookCatalogList where description does not contain UPDATED_DESCRIPTION
        defaultBookCatalogShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookCatalogShouldBeFound(String filter) throws Exception {
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookCatalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookCatalogShouldNotBeFound(String filter) throws Exception {
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBookCatalog() throws Exception {
        // Get the bookCatalog
        restBookCatalogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBookCatalog() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();

        // Update the bookCatalog
        BookCatalog updatedBookCatalog = bookCatalogRepository.findById(bookCatalog.getId()).get();
        // Disconnect from session so that the updates on updatedBookCatalog are not directly saved in db
        em.detach(updatedBookCatalog);
        updatedBookCatalog.title(UPDATED_TITLE).author(UPDATED_AUTHOR).description(UPDATED_DESCRIPTION);
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(updatedBookCatalog);

        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(count.incrementAndGet());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(count.incrementAndGet());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(count.incrementAndGet());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookCatalogWithPatch() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();

        // Update the bookCatalog using partial update
        BookCatalog partialUpdatedBookCatalog = new BookCatalog();
        partialUpdatedBookCatalog.setId(bookCatalog.getId());

        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookCatalog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookCatalog))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateBookCatalogWithPatch() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();

        // Update the bookCatalog using partial update
        BookCatalog partialUpdatedBookCatalog = new BookCatalog();
        partialUpdatedBookCatalog.setId(bookCatalog.getId());

        partialUpdatedBookCatalog.title(UPDATED_TITLE).author(UPDATED_AUTHOR).description(UPDATED_DESCRIPTION);

        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookCatalog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookCatalog))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(count.incrementAndGet());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(count.incrementAndGet());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(count.incrementAndGet());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookCatalog() throws Exception {
        // Initialize the database
        bookCatalogRepository.saveAndFlush(bookCatalog);

        int databaseSizeBeforeDelete = bookCatalogRepository.findAll().size();

        // Delete the bookCatalog
        restBookCatalogMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookCatalog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
