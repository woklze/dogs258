package com.example.dogwalking;
import org.springframework.web.bind.annotation.*;
import com.example.dogwalking.dto.GeoResult;

@RestController
@RequestMapping("/api/maps")
public class MapsController {
    private final YandexMapsService mapsService;
    public MapsController(YandexMapsService mapsService) {
        this.mapsService = mapsService;
    }
    @GetMapping("/geocode")
    public GeoResult geocode(@RequestParam String address) {
        return mapsService.geocode(address);
    }
}