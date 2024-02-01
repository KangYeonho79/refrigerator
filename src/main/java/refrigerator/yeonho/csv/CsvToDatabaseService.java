package refrigerator.yeonho.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import refrigerator.yeonho.dto.RecipeDto;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.repository.RecipeRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvToDatabaseService implements ApplicationRunner {

    private final RecipeRepository recipeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // CSV 파일을 처리합니다.
        processCsvFile("/static/recipe.csv");
    }

    public void processCsvFile(String csvFilePath) {
        Resource resource = new ClassPathResource(csvFilePath);
        try (InputStream inputStream = resource.getInputStream()) {
            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                List<String[]> rows = reader.readAll();

                // 첫 번째 행은 헤더로 가정하고 제외
                if (!rows.isEmpty()) {
                    rows.remove(0);
                }

                for (String[] row : rows) {
                    RecipeDto dto = new RecipeDto();
                    dto.setCookName(row[0]);
                    dto.setMainIngredient(row[1]);
                    dto.setServing(row[2]);
                    dto.setTakingTime(row[3]);
                    dto.setIngredients(row[4]);
                    dto.setHowtocook(row[5]);
                    dto.setImage(row[6]);

                    RecipeEntity entity = convertDtoToEntity(dto);
                    recipeRepository.save(entity);
                }
            }
        } catch (IOException | CsvException e) {
            log.error("CSV 파일 처리 중 오류 발생", e);
        }
    }

    private RecipeEntity convertDtoToEntity(RecipeDto dto) {
        RecipeEntity entity = new RecipeEntity();
        entity.setCookName(dto.getCookName());
        entity.setMainIngredient(dto.getMainIngredient());
        entity.setServing(dto.getServing());
        entity.setTakingTime(dto.getTakingTime());
        entity.setIngredients(dto.getIngredients());
        entity.setHowtocook(dto.getHowtocook());
        entity.setImage(dto.getImage());

        return entity;
    }
}
