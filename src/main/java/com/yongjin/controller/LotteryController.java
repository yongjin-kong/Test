package com.yongjin.controller;

import com.yongjin.common.Prize;
import com.yongjin.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lottery")
public class LotteryController {
    @Autowired
    private LotteryService lotteryService;

    //Lottery draw
    @PostMapping("/draw/{userId}")
    public String draw(@PathVariable int userId) {
        return lotteryService.lotteryDraw(userId);
    }

    //Check user winning prizes
    @GetMapping("/userPrizes/{userId}")
    public Set<String> getUserPrizes(@PathVariable int userId) {
        return lotteryService.getUserPrizes(userId);
    }

    //Check user remaining lottery draw attempts
    @GetMapping("/userDrawTimes/{userId}")
    public int getUserDrawTimes(@PathVariable int userId) {
        return lotteryService.getUserDrawTimes(userId);
    }

    //Create/set user draw attempts
    @PostMapping("/user/{userId}/draw-times/{drawTimes}")
    public String setUserDrawTimes(@PathVariable int userId, @PathVariable int drawTimes) {
        lotteryService.setUserDrawTimes(userId, drawTimes);
        return "User draw times set successfully";
    }

    //Add new prizes
    @PostMapping("/addPrize")
    public String addPrize(@RequestBody Prize prize) {
        lotteryService.addPrize(prize);
        return "Prize added successfully";
    }

    //List out all prizes
    @GetMapping("/prizes")
    public List<Object> getUserPrizes() {
        return lotteryService.getPrizes();
    }
}
