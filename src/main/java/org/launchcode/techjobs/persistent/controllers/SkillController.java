package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    SkillRepository skillRepository;

    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("title", "Skills");
        model.addAttribute("skills", skillRepository.findAll());

        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute("title", "Add Skill");
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Skill");
            model.addAttribute(new Skill());
            return "skills/add";
        }

        skillRepository.save(newSkill);
        return "redirect:";
    }
    @GetMapping("view/{employerId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {


        Optional<Skill> result = skillRepository.findById(skillId);
        if (result.isEmpty()) {
            model.addAttribute("skill", "Invalid Skill ID: " + skillId);
        } else {
            Skill skill = result.get();
            model.addAttribute("title", "Skills: " + skill.getName());
//            model.addAttribute("employers", employer.get)
        }
        //}
        return "skills/view";
    }
}
