package com.example.dogwalking;

import com.example.dogwalking.dto.BreedCharacteristics;
import org.springframework.web.bind.annotation.*;
        import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CharacteristicsController {

    private final CharacteristicsService characteristicsService;
    public CharacteristicsController(CharacteristicsService characteristicsService) {this.characteristicsService = characteristicsService;}

    @GetMapping("/breed") /* /api/breed?name=Labrador Retriever*/
    public ResponseEntity<BreedCharacteristics> getBreedCharacteristics(@RequestParam String name) {
        Optional<BreedCharacteristics> characteristics = characteristicsService.getBreedCharacteristics(name);
        return characteristics
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}