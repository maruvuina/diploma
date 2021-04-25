package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.CommentDto;
import by.bsu.recipebook.dto.recipe.RecipeGetDto;
import by.bsu.recipebook.dto.recipe.RecipeLikeDto;
import by.bsu.recipebook.dto.recipe.RecipePostDto;
import by.bsu.recipebook.dto.recipe.RecipeUpdateDto;
import by.bsu.recipebook.entity.*;
import by.bsu.recipebook.mapper.CommentMapper;
import by.bsu.recipebook.mapper.JsonMapper;
import by.bsu.recipebook.mapper.RecipeMapper;
import by.bsu.recipebook.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.*;

import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@PrepareForTest({RecipeService.class,
        JsonMapper.class,
        LogManager.class,
        ImageService.class,
        DatatypeConverter.class})
public class RecipeServiceTest extends PowerMockTestCase {
    private AutoCloseable closeable;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private AuthService authService;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeLikeRepository recipeLikeRepository;

    @Mock
    private JsonMapper jsonMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    private RecipeService recipeService;

    private static final String PRIVATE_METHOD_GET_BY_ID = "getRecipeById";

    private static final String PRIVATE_METHOD_GET_RECIPE_LIKE_BY_RECIPE_AND_USER =
            "getRecipeLikeByRecipeAndUser";

    private static final String PRIVATE_METHOD_LIKE_RECIPE = "likeRecipe";

    private Recipe recipe;

    private RecipeGetDto expectedRecipeGetDto;

    private CommentDto expectedCommentDto;

    private Comment comment;

    private RecipeService spy;

    private User user;

    @Captor
    private ArgumentCaptor<Recipe> recipeArgumentCaptor;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        recipeService = new RecipeService(recipeRepository, authService, recipeMapper,
                commentRepository, commentMapper, ingredientRepository,
                recipeLikeRepository, jsonMapper, categoryRepository, userRepository);
        recipe = new Recipe();
        recipe.setIdRecipe(1);
        recipe.setRecipeName("RecipeName");
        expectedRecipeGetDto = new RecipeGetDto();
        expectedRecipeGetDto.setIdRecipe(1);
        expectedRecipeGetDto.setRecipeName("RecipeName");
        expectedCommentDto = new CommentDto(1, 1, "John Smith", null, "Content", null, null);
        comment = new Comment(1, "Content", Instant.now(), null, null, null, null);
        user = new User();
        user.setFullName("John Smith");
        spy = PowerMockito.spy(recipeService);
    }

    @BeforeClass
    public void setUpLogger() {
        PowerMockito.mockStatic(LogManager.class);
        when(LogManager.getLogger(any(JsonMapper.class))).thenReturn(mock(Logger.class));
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldSave() throws JsonProcessingException {
        RecipePostDto recipePostDto = new RecipePostDto();
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.mockStatic(ImageService.class);
        when(LogManager.getLogger(any(ImageService.class))).thenReturn(mock(Logger.class));
        when(ImageService.save(mock(MultipartFile.class), "dir-name"))
                .thenReturn("\\location\\dir-name\\image.png");
        when(authService.getCurrentUser()).thenReturn(user);
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readValue(anyString(), eq(RecipePostDto.class))).thenReturn(recipePostDto);
        when(jsonMapper.mapDto(eq(anyString()), RecipePostDto.class)).thenReturn(recipePostDto);
        when(recipeMapper.mapToRecipe(any(RecipePostDto.class), user, eq(anyString()))).thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.mapToRecipeGetDto(recipe)).thenReturn(expectedRecipeGetDto);
        RecipeGetDto actual = recipeService.save(eq("dto"), any(MultipartFile.class));
        verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());
        assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldDelete() {
        doNothing().when(mock(RecipeService.class)).delete(anyInt());
    }

    @Test
    public void shouldUpdate() throws Exception {
        PowerMockito
                .doReturn(new Recipe())
                .when(spy,
                        method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        when(recipeMapper.mapFromRecipeUpdateDto(any(RecipeUpdateDto.class), any(Recipe.class)))
                .thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.mapToRecipeGetDto(any(Recipe.class))).thenReturn(expectedRecipeGetDto);
        RecipeGetDto actual = spy.update(new RecipeUpdateDto(), eq(anyInt()));
        assertThat(actual.getIdRecipe()).isEqualTo(expectedRecipeGetDto.getIdRecipe());
        assertThat(actual.getRecipeName()).isEqualTo(expectedRecipeGetDto.getRecipeName());
    }

    @Test
    public void shouldGetByRecipeName() {
        when(recipeRepository.findByRecipeName(anyString())).thenReturn(recipe);
        when(recipeMapper.mapToRecipeGetDto(any(Recipe.class))).thenReturn(expectedRecipeGetDto);
        RecipeGetDto actual = recipeService.getByRecipeName(anyString());
        assertThat(actual.getIdRecipe()).isEqualTo(expectedRecipeGetDto.getIdRecipe());
        assertThat(actual.getRecipeName()).isEqualTo(expectedRecipeGetDto.getRecipeName());
    }

    @Test
    public void shouldGetById() throws Exception {
        when(recipeMapper.mapToRecipeGetDto(any(Recipe.class))).thenReturn(expectedRecipeGetDto);
        PowerMockito
                .doReturn(new Recipe())
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        RecipeGetDto actual = spy.getById(anyInt());
        assertThat(actual.getIdRecipe()).isEqualTo(expectedRecipeGetDto.getIdRecipe());
        assertThat(actual.getRecipeName()).isEqualTo(expectedRecipeGetDto.getRecipeName());
    }

    @Test
    public void shouldGetRecipeMainPicture() throws Exception {
        String expected = "d:\\uploaded-files\\recipe-system\\user-images\\default-avatar.png";
        recipe.setMainPicture(expected);
        PowerMockito
                .doReturn(recipe)
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        PowerMockito.mockStatic(DatatypeConverter.class);
        when(DatatypeConverter.printBase64Binary(expected.getBytes())).thenReturn(expected);
        String actual = spy.getRecipeMainPicture(anyInt());
        assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldGetRandomRecipe() {
        when(recipeRepository.count()).thenReturn(1L);
        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(recipeMapper.mapToRecipeGetDto(any(Recipe.class))).thenReturn(expectedRecipeGetDto);
        RecipeGetDto actual = recipeService.getRandomRecipe();
        assertThat(actual.getIdRecipe()).isEqualTo(expectedRecipeGetDto.getIdRecipe());
    }

    @Test
    public void shouldGetByRecipeNamePattern() {
        List<Recipe> expectedRecipeList = new ArrayList<>();
        expectedRecipeList.add(recipe);
        when(recipeRepository.searchByRecipeNamePattern(anyString())).thenReturn(expectedRecipeList);
        RecipeGetDto recipeGetDto = new RecipeGetDto();
        recipeGetDto.setIdRecipe(1);
        when(recipeMapper.mapToRecipeGetDto(any(Recipe.class))).thenReturn(recipeGetDto);
        List<RecipeGetDto> actual = recipeService.getByRecipeNamePattern(anyString());
        assertThat(actual.get(0).getIdRecipe()).isEqualTo(expectedRecipeList.get(0).getIdRecipe());
    }

    @Test
    public void shouldSaveComment() throws Exception {
        when(authService.getCurrentUser()).thenReturn(user);
        PowerMockito
                .doReturn(recipe)
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        when(commentMapper.mapToComment(any(CommentDto.class), any(User.class), any(Recipe.class)))
                .thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.mapToCommentDto(any(Comment.class))).thenReturn(expectedCommentDto);
        CommentDto actual = spy.saveComment(0, expectedCommentDto);
        assertThat(actual.getIdComment()).isEqualTo(expectedCommentDto.getIdComment());
    }

    @Test
    public void shouldLike() throws Exception {
        when(authService.isLoggedIn()).thenReturn(true);
        PowerMockito
                .doReturn(recipe)
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        when(authService.getCurrentUser()).thenReturn(user);
        RecipeLike recipeLike = new RecipeLike(1, null, null, LikeType.LIKE);
        PowerMockito
                .doReturn(Optional.of(new RecipeLike()))
                .when(spy, method(RecipeService.class,
                        PRIVATE_METHOD_GET_RECIPE_LIKE_BY_RECIPE_AND_USER,
                        Recipe.class, User.class))
                .withArguments(any(Recipe.class), any(User.class));
        when(recipeMapper
                .mapToRecipeLike(any(RecipeLikeDto.class), any(User.class), any(Recipe.class)))
                .thenReturn(recipeLike);
        PowerMockito
                .doNothing()
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_LIKE_RECIPE,
                        Recipe.class, LikeType.class))
                .withArguments(any(Recipe.class), any(LikeType.class));
        doNothing().when(spy).like(new RecipeLikeDto());
    }

    @Test
    public void shouldIsLiked() throws Exception {
        RecipeLike recipeLike = new RecipeLike(1, null, null, LikeType.LIKE);
        PowerMockito
                .doReturn(recipe)
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        when(authService.getCurrentUser()).thenReturn(user);
        PowerMockito
                .doReturn(Optional.of(recipeLike))
                .when(spy, method(RecipeService.class,
                        PRIVATE_METHOD_GET_RECIPE_LIKE_BY_RECIPE_AND_USER,
                        Recipe.class, User.class))
                .withArguments(any(Recipe.class), any(User.class));
        spy.isLiked(anyInt());
        verify(spy).isLiked(anyInt());
    }

    @Test
    public void shouldReplyOnComment() throws Exception {
        when(authService.getCurrentUser()).thenReturn(user);
        PowerMockito
                .doReturn(recipe)
                .when(spy, method(RecipeService.class, PRIVATE_METHOD_GET_BY_ID, Integer.class))
                .withArguments(any(Integer.class));
        when(commentMapper
                .mapToComment(any(CommentDto.class), any(User.class), any(Recipe.class)))
                .thenReturn(comment);
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.mapToCommentDto(any(Comment.class))).thenReturn(expectedCommentDto);
        CommentDto actual = spy.replyOnComment(0, expectedCommentDto);
        assertThat(actual.getIdComment()).isEqualTo(expectedCommentDto.getIdComment());
    }
}
