package io.greengame.greengameio.controller;

import io.greengame.greengameio.entity.GameResult;
import io.greengame.greengameio.services.GameResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameResultController {

    private final GameResultService gameResultService;

    @GetMapping("/xp/{userId}")
    double getXpByUserId(@PathVariable Long userId) {
        return gameResultService.getXpByUserId(userId);
    }

    @PostMapping("updateUserXP/{userId}/{xp}")
    void updateUserXP(@PathVariable Long userId,@PathVariable double xp) {
        gameResultService.updateUserXP(userId, xp);
    }

    @PostMapping("snake/{userId}/{snakeScore}")
    GameResult updateSnakeResult(@PathVariable Long userId, @PathVariable int snakeScore) {
        return gameResultService.updateSnakeResult(userId, snakeScore);
    }

    @PostMapping("lightsOut/{userId}/{lightsOutScore}")
    GameResult updateLightsOutResult(@PathVariable Long userId,@PathVariable int lightsOutScore) {
        return gameResultService.updateLightsOutResult(userId, lightsOutScore);
    }

    @GetMapping("/fruitCatcher/{userId}")
    int getFruitCatcherScoreByUserId(@PathVariable Long userId) {
        return gameResultService.getFruitCatcherScoreByUserId(userId);
    }

    @PostMapping("fruitCatcher/{userId}/{fruitCatcherScore}")
    GameResult updateFruitCatcherResult(@PathVariable Long userId,@PathVariable int fruitCatcherScore) {
        return gameResultService.updateFruitCatcherResult(userId, fruitCatcherScore);
    }

}
