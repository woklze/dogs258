package com.example.dogwalking;
import org.springframework.web.bind.annotation.*;
import com.example.dogwalking.dto.DogBreed;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BreedsController {

    private final BreedsService breedsService;
    public BreedsController(BreedsService breedsService) {
        this.breedsService = breedsService;
    }

    @GetMapping("/allBreeds")
    public List<String> getAllBreedNames(){
        return breedsService.getAllBreedNames();
    }

}