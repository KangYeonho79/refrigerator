package refrigerator.yeonho.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import refrigerator.yeonho.dto.RecipeDto;
import refrigerator.yeonho.dto.RecipeRatingDto;
import refrigerator.yeonho.entity.Fridge;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.entity.RecipeRating;
import refrigerator.yeonho.repository.RecipeRatingRepository;
import refrigerator.yeonho.repository.RecipeRepository;
import refrigerator.yeonho.service.FridgeService;
import refrigerator.yeonho.service.RecipeRatingService;
import refrigerator.yeonho.service.RecommendService;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/refrigerator")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecommendService recommendService;
    private final RecipeRatingService recipeRatingService;
    private final RecipeRatingRepository recipeRatingRepository;
    private final FridgeService fridgeService;


    @GetMapping("/total_recipe")
    public String getAllRecipes(Model model) {
        List<RecipeEntity> recipes = recipeRepository.findAll();
        List<RecipeDto> recipeDtos = recipes.stream()
                .map(RecipeDto::toRecipeDto)
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipeDtos);
        return "refrigerator/total_recipe";
    }

    @GetMapping("/detail_recipe/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);
        RecipeDto recipeDto = RecipeDto.toRecipeDto(recipeEntity.get());
        model.addAttribute("recipe", recipeDto);

        return "refrigerator/detail_recipe";
    }

    @PostMapping("/detail_recipe/{id}")
    public String addStar(@RequestParam("id") Long id,
                         @RequestParam("rating") Integer rating){

        log.info("id={}",id);
        log.info("rating={}", rating);
        recipeRatingService.recipeRatingStringSave(currentUsername(),id,rating);
        return "refrigerator/total_recipe";
    }


    @GetMapping("/rec_recipe")
    public String recommend(Model model) {

        String currentUsername = currentUsername();
        log.info("name={}", currentUsername);
        model.addAttribute("currentUsername", currentUsername);

        List<RecipeEntity> recommendedRecipe = recommendService.recommendRecipe(currentUsername);

        List<RecipeDto> recipeDtos = recommendedRecipe.stream()
                .map(RecipeDto::toRecipeDto)
                .collect(Collectors.toList());

        List<RecipeEntity> limitedRecipes = recommendedRecipe.subList(0, 10);
        model.addAttribute("recipes", limitedRecipes);
        return "refrigerator/rec_recipe";

    }

    @GetMapping("/add_image")
    public String addImage() {
        recommendService.recommendRecipe(currentUsername());
        return "refrigerator/add_image";
    }

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/add_image")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request,Model model) throws IOException {

        UUID uuid = UUID.randomUUID();
        String randomString = uuid.toString();

        if (!file.isEmpty()) {
            String fullPath = fileDir +randomString+ file.getOriginalFilename();
            String savedName=randomString+ file.getOriginalFilename();
            log.info("파일 저장 fullPath={}", fullPath);
            log.info("파일 저장 이름={}", savedName);
            file.transferTo(new File(fullPath));
            model.addAttribute("savedName", savedName);

            PythonInterpreter pythonInterpreter = new PythonInterpreter();
            try {
                // Python 스크립트 경로 설정
                String scriptPath = "/Users/yeonhokang/Desktop/coding/yeonho/deepLearning/refrigerator/modules/yolov5Run.py";

                // Python 스크립트 실행
                pythonInterpreter.execfile(scriptPath);

                // Python 함수 호출
                PyFunction pyFunction = (PyFunction) pythonInterpreter.get("deep_learning", PyFunction.class);
                PyObject pyObject = pyFunction.__call__(new PyString(fullPath));


                log.info("값은={}", pyObject.toString());
                model.addAttribute("ingredientName", pyObject.toString());
            } finally {
                pythonInterpreter.close();
            }
        }
        return "refrigerator/image_upload";
    }

    @GetMapping("/fridge_save")
    public String showFridgeSavePage(Model model) {
        model.addAttribute("currentUsername", currentUsername());
        List<Fridge> fridges = fridgeService.findFridge(currentUsername());
        model.addAttribute("fridges", fridges);

        return "refrigerator/fridge_save";
    }


    @PostMapping("/fridge_save")
    public String fridgeSave(@RequestParam("ingredientName") String ingredient,Model model) {
        String currentUsername = currentUsername();
        fridgeService.save(currentUsername, ingredient);

        return "redirect:/refrigerator/fridge_save";

    }


    @GetMapping("/my_favorite")
    public String myFavorite(Model model) {
        List<RecipeRating> findRating = recipeRatingRepository.findByloginId(currentUsername());
        log.info("findRating={}", findRating);

        List<RecipeRatingDto> rating = findRating.stream()
                .map(RecipeRatingDto::toRecipeRatingDto)
                .collect(Collectors.toList());

        model.addAttribute("rating", rating);

        return "refrigerator/my_favorite";
    }

    @GetMapping("/total_recipe/{ingredient}")
    public String ingredientRecipe(@PathVariable("ingredient") String ingredient, Model model) {
        log.info("여기까지는 실행된다.");
        List<RecipeEntity> findRecipe = recipeRepository.findByMainIngredient(ingredient);
        model.addAttribute("findRecipe", findRecipe);
        model.addAttribute("main", ingredient);
        return "refrigerator/deep_recipe";
    }


    private static String currentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return currentUsername;
    }
}
