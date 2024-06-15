package com.assgn2.assgn2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assgn2.assgn2.models.Rectangles;
import com.assgn2.assgn2.models.RectanglesRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RectanglesController {

    @Autowired
    private RectanglesRepository rectanglesRepo;

    @GetMapping("/rectangles/view")
    public String getAllRectangles(Model model) {
        System.out.println("Getting All Rectangles");
        List<Rectangles> rectangles = rectanglesRepo.findAll();
        model.addAttribute("rectangles", rectangles);
        return "rectangles/showAll"; // Ensure 'showAll.html' is in 'src/main/resources/templates/'
    }

    @PostMapping("/rectangles/add")
    public String addRectangle(@RequestParam Map<String, String> newRectangle, HttpServletResponse response) {
        System.out.println("Add Rectangle");
        String rectangleName = newRectangle.get("name");
        int rectangleHeight = Integer.parseInt(newRectangle.get("height"));
        int rectangleWidth = Integer.parseInt(newRectangle.get("width"));
        String rectangleColor = newRectangle.get("color");

        rectanglesRepo.save(new Rectangles(rectangleName, rectangleWidth, rectangleHeight, rectangleColor));
        response.setStatus(201);
        return "rectangles/rectangleAdded"; // 'rectangleAdded.html' is in 'src/main/resources/templates/'
    }

    @GetMapping("rectangles/modify")
    public String modifyRectangle(@RequestParam("rid") int rid, Model model) {
        Rectangles rectangle = rectanglesRepo.findById(rid).orElse(null);
        model.addAttribute("rectangle", rectangle);
        return "rectangles/displayUpdatedRectanglesForm"; // Ensure 'modify.html' is in 'src/main/resources/templates/'
    }

    @GetMapping("/rectangles/display")
public String viewRectangle(@RequestParam("rid") int rid, Model model) {
    Rectangles rectangle = rectanglesRepo.findById(rid).orElse(null);
    model.addAttribute("rectangle", rectangle);
    return "rectangles/viewRectangle"; // Ensure 'viewRectangle.html' is in 'src/main/resources/templates/rectangles/'
}
    @GetMapping("/rectangle-form")
    public String viewForm() {
        return "rectangles/rectangleForm"; // Ensure 'rectangleForm.html' is in 'src/main/resources/templates/'
    }

    // @GetMapping("/displayUpdatedRectanglesForm")
    // public String viewUpdatedRectangleForm(@RequestParam("rid") int rid, Model model) {
    //     Rectangles rectangle = rectanglesRepo.findById(rid).orElse(null);
    //     model.addAttribute("rectangle", rectangle);
    //     return "displayUpdatedRectanglesForm"; // Ensure 'displayUpdatedRectanglesForm.html' is in 'src/main/resources/templates/'
    // }

    @PostMapping("/rectangles/update")
    public String updateRectangle(@RequestParam Map<String, String> rectangleFormUpdated) {
        int rid = Integer.parseInt(rectangleFormUpdated.get("rid"));
        Rectangles rectangle = rectanglesRepo.findById(rid).orElseThrow();
        if (rectangle != null) {
            rectangle.setName(rectangleFormUpdated.get("name"));
            rectangle.setWidth(Integer.parseInt(rectangleFormUpdated.get("width")));
            rectangle.setHeight(Integer.parseInt(rectangleFormUpdated.get("height")));
            rectangle.setColor(rectangleFormUpdated.get("color"));
            rectanglesRepo.save(rectangle);
        }
        return "redirect:/rectangles/view";
    }

    @Transactional
    @PostMapping("/rectangles/delete")
    public String deleteRectangle(@RequestParam int rid) {
        System.out.println("Deleting Rectangle " + rid);
        rectanglesRepo.deleteById(rid);
        return "redirect:/rectangles/view";
    }
}
