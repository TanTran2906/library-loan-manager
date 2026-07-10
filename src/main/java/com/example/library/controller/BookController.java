package com.example.library.controller;

import com.example.library.dto.BookForm;
import com.example.library.service.BookService;
import com.example.library.service.DuplicateIsbnException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q, Model model) {
        model.addAttribute("books", bookService.findAll(q));
        model.addAttribute("q", q);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getDetail(id));
        return "books/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "books/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") BookForm form, BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "books/form";
        }
        try {
            Long id = bookService.create(form);
            redirectAttributes.addFlashAttribute("flash", "“" + form.getTitle() + "” has been added.");
            return "redirect:/books/" + id;
        } catch (DuplicateIsbnException e) {
            result.rejectValue("isbn", "duplicate", "A book with this ISBN already exists.");
            return "books/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("form", bookService.getForm(id));
        return "books/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("form") BookForm form, BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "books/form";
        }
        try {
            bookService.update(id, form);
            redirectAttributes.addFlashAttribute("flash", "Changes to “" + form.getTitle() + "” have been saved.");
            return "redirect:/books/" + id;
        } catch (DuplicateIsbnException e) {
            result.rejectValue("isbn", "duplicate", "A book with this ISBN already exists.");
            return "books/form";
        }
    }
}
