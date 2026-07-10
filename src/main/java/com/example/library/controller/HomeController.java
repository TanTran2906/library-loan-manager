package com.example.library.controller;

import com.example.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    /** How many catalogue rows the hero previews before the "Browse" CTA. */
    private static final int HERO_PREVIEW_ROWS = 8;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stats", bookService.getStats());
        model.addAttribute("books", bookService.findPreview(HERO_PREVIEW_ROWS));
        return "index";
    }
}
