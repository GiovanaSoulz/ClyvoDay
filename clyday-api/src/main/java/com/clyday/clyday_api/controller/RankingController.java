package com.clyday.clyday_api.controller;

import com.clyday.clyday_api.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/ranking")
    public String ranking(Model model) {

        model.addAttribute(
                "ranking",
                rankingService.rankingSemanal()
        );

        return "ranking";
    }
}