package eu.dreamix.four_for_belot.web.rest;

import eu.dreamix.four_for_belot.FourForBelotApp;

import eu.dreamix.four_for_belot.domain.Game;
import eu.dreamix.four_for_belot.repository.GameRepository;
import eu.dreamix.four_for_belot.service.GameService;
import eu.dreamix.four_for_belot.service.dto.GameDTO;
import eu.dreamix.four_for_belot.service.mapper.GameMapper;
import eu.dreamix.four_for_belot.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static eu.dreamix.four_for_belot.web.rest.TestUtil.sameInstant;
import static eu.dreamix.four_for_belot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GameResource REST controller.
 *
 * @see GameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FourForBelotApp.class)
public class GameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_REQUIRED_PEOPLE_COUNT = 1;
    private static final Integer UPDATED_REQUIRED_PEOPLE_COUNT = 2;

    private static final Integer DEFAULT_CURRENTLY_AVALABLE_PEOPLE = 1;
    private static final Integer UPDATED_CURRENTLY_AVALABLE_PEOPLE = 2;

    private static final ZonedDateTime DEFAULT_CREATED_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_START_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_REQUIRES_APPROVAL_FOR_JOINING = false;
    private static final Boolean UPDATED_REQUIRES_APPROVAL_FOR_JOINING = true;

    private static final Boolean DEFAULT_IS_AVAILABLE_FOR_JOINING = false;
    private static final Boolean UPDATED_IS_AVAILABLE_FOR_JOINING = true;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameService gameService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGameMockMvc;

    private Game game;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameResource gameResource = new GameResource(gameService);
        this.restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity(EntityManager em) {
        Game game = new Game()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .requiredPeopleCount(DEFAULT_REQUIRED_PEOPLE_COUNT)
            .currentlyAvalablePeople(DEFAULT_CURRENTLY_AVALABLE_PEOPLE)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .requiresApprovalForJoining(DEFAULT_REQUIRES_APPROVAL_FOR_JOINING)
            .isAvailableForJoining(DEFAULT_IS_AVAILABLE_FOR_JOINING);
        return game;
    }

    @Before
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);
        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGame.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGame.getRequiredPeopleCount()).isEqualTo(DEFAULT_REQUIRED_PEOPLE_COUNT);
        assertThat(testGame.getCurrentlyAvalablePeople()).isEqualTo(DEFAULT_CURRENTLY_AVALABLE_PEOPLE);
        assertThat(testGame.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testGame.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testGame.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testGame.isRequiresApprovalForJoining()).isEqualTo(DEFAULT_REQUIRES_APPROVAL_FOR_JOINING);
        assertThat(testGame.isIsAvailableForJoining()).isEqualTo(DEFAULT_IS_AVAILABLE_FOR_JOINING);
    }

    @Test
    @Transactional
    public void createGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game with an existing ID
        game.setId(1L);
        GameDTO gameDTO = gameMapper.toDto(game);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setName(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequiredPeopleCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setRequiredPeopleCount(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentlyAvalablePeopleIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setCurrentlyAvalablePeople(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setCreatedDateTime(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setStartDateTime(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequiresApprovalForJoiningIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setRequiresApprovalForJoining(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsAvailableForJoiningIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setIsAvailableForJoining(null);

        // Create the Game, which fails.
        GameDTO gameDTO = gameMapper.toDto(game);

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].requiredPeopleCount").value(hasItem(DEFAULT_REQUIRED_PEOPLE_COUNT)))
            .andExpect(jsonPath("$.[*].currentlyAvalablePeople").value(hasItem(DEFAULT_CURRENTLY_AVALABLE_PEOPLE)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(sameInstant(DEFAULT_CREATED_DATE_TIME))))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(sameInstant(DEFAULT_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(sameInstant(DEFAULT_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].requiresApprovalForJoining").value(hasItem(DEFAULT_REQUIRES_APPROVAL_FOR_JOINING.booleanValue())))
            .andExpect(jsonPath("$.[*].isAvailableForJoining").value(hasItem(DEFAULT_IS_AVAILABLE_FOR_JOINING.booleanValue())));
    }

    @Test
    @Transactional
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.requiredPeopleCount").value(DEFAULT_REQUIRED_PEOPLE_COUNT))
            .andExpect(jsonPath("$.currentlyAvalablePeople").value(DEFAULT_CURRENTLY_AVALABLE_PEOPLE))
            .andExpect(jsonPath("$.createdDateTime").value(sameInstant(DEFAULT_CREATED_DATE_TIME)))
            .andExpect(jsonPath("$.startDateTime").value(sameInstant(DEFAULT_START_DATE_TIME)))
            .andExpect(jsonPath("$.endDateTime").value(sameInstant(DEFAULT_END_DATE_TIME)))
            .andExpect(jsonPath("$.requiresApprovalForJoining").value(DEFAULT_REQUIRES_APPROVAL_FOR_JOINING.booleanValue()))
            .andExpect(jsonPath("$.isAvailableForJoining").value(DEFAULT_IS_AVAILABLE_FOR_JOINING.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findOne(game.getId());
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requiredPeopleCount(UPDATED_REQUIRED_PEOPLE_COUNT)
            .currentlyAvalablePeople(UPDATED_CURRENTLY_AVALABLE_PEOPLE)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .requiresApprovalForJoining(UPDATED_REQUIRES_APPROVAL_FOR_JOINING)
            .isAvailableForJoining(UPDATED_IS_AVAILABLE_FOR_JOINING);
        GameDTO gameDTO = gameMapper.toDto(updatedGame);

        restGameMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGame.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGame.getRequiredPeopleCount()).isEqualTo(UPDATED_REQUIRED_PEOPLE_COUNT);
        assertThat(testGame.getCurrentlyAvalablePeople()).isEqualTo(UPDATED_CURRENTLY_AVALABLE_PEOPLE);
        assertThat(testGame.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testGame.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testGame.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testGame.isRequiresApprovalForJoining()).isEqualTo(UPDATED_REQUIRES_APPROVAL_FOR_JOINING);
        assertThat(testGame.isIsAvailableForJoining()).isEqualTo(UPDATED_IS_AVAILABLE_FOR_JOINING);
    }

    @Test
    @Transactional
    public void updateNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGameMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);
        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Get the game
        restGameMockMvc.perform(delete("/api/games/{id}", game.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Game.class);
        Game game1 = new Game();
        game1.setId(1L);
        Game game2 = new Game();
        game2.setId(game1.getId());
        assertThat(game1).isEqualTo(game2);
        game2.setId(2L);
        assertThat(game1).isNotEqualTo(game2);
        game1.setId(null);
        assertThat(game1).isNotEqualTo(game2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameDTO.class);
        GameDTO gameDTO1 = new GameDTO();
        gameDTO1.setId(1L);
        GameDTO gameDTO2 = new GameDTO();
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
        gameDTO2.setId(gameDTO1.getId());
        assertThat(gameDTO1).isEqualTo(gameDTO2);
        gameDTO2.setId(2L);
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
        gameDTO1.setId(null);
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gameMapper.fromId(null)).isNull();
    }
}
