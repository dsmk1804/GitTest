package com.kcs.org.controller;

import com.kcs.org.dto.FreeBoardDto;
import com.kcs.org.entity.FreeBoard;
import com.kcs.org.repository.FreeBoardRepository;
import com.kcs.org.service.FreeBoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/FreeBoard")
public class FreeBoardController {

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Autowired
    FreeBoardService freeBoardService;

    @DeleteMapping("Delete")
    public @ResponseBody String delete(FreeBoardDto freeBoardDto){
        freeBoardRepository.deleteById(freeBoardDto.getIdx());
        return "success";
    }

    @GetMapping("View")
    public String view(@ModelAttribute @Valid FreeBoardDto freeBoardDto, BindingResult bindingResult,
                    Model model){
        System.out.println("idx="+ freeBoardDto.getIdx());
        FreeBoardDto dto = freeBoardService.getRow(freeBoardDto);
        model.addAttribute("freeBoardDto",dto);
        return "freeboard/view";
    }

    @GetMapping("UpdateForm")
    public String UpdateForm(@ModelAttribute @Valid FreeBoardDto freeBoardDto, BindingResult bindingResult,
                            Model model){
        System.out.print(freeBoardDto);
        FreeBoardDto dto = freeBoardService.getRow(freeBoardDto);
        model.addAttribute("freeBoardDto",dto);
        return "freeboard/updateform";
    }

    @GetMapping("WriteForm")
    public String writeForm(@ModelAttribute @Valid FreeBoardDto freeBoardDto, BindingResult bindingResult){
        return "freeboard/writeform";
    }

    @PostMapping("WriteForm")
    public String pwriteForm(@ModelAttribute @Valid FreeBoardDto freeBoardDto, BindingResult bindingResult,
                             Model model,
                             HttpServletRequest request,
                             HttpSession session,
                             Authentication authentication) {
        System.out.println("title = " + request.getParameter("title"));
        System.out.println("username = " + session.getAttribute("username"));

        System.out.println("authentication = " + authentication);
        System.out.println("authentication = " + authentication.getPrincipal());
        if(authentication !=null){
            User user = (User) authentication.getPrincipal();
            freeBoardDto.setName(user.getUsername());

        }

        if (bindingResult.hasErrors()) {
            System.out.println("사이즈 문제 있음!");

            return "freeboard/writeform";
        } else {
            System.out.println(freeBoardDto);
            boolean result = freeBoardService.insert(freeBoardDto);
            if (result)
                return "redirect:/FreeBoard";
        }
            return "freeboard/writeform";
    }

    @PostMapping("UpdateForm")
    public String pUpdateForm(@ModelAttribute @Valid FreeBoardDto freeBoardDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("freeboarddto", dto);
            return "freeboard/updateform";
        } else {
            boolean result = freeBoardService.insert(freeBoardDto);
            if (result)
                return "redirect:/FreeBoard";
        }
        return "freeboard/updateform";
    }

    @GetMapping("")
    public String index(Model model, @PageableDefault(
            size = 5,
            sort = "idx",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "") String searchText){
        Page<FreeBoard> pagelist = freeBoardService.list(searchText,searchText, pageable);

        List<FreeBoardDto> dtolist = new ArrayList<>();
        for( FreeBoard fb :pagelist){
            FreeBoardDto dto = FreeBoardDto.of(fb);
            dtolist.add(dto);
        }
        model.addAttribute("curPage",page+1);
        model.addAttribute("totalElements",pagelist.getTotalElements());
        model.addAttribute("totalPages",pagelist.getTotalPages());

        model.addAttribute("list",pagelist);
        return "freeboard/index";

    }
}
