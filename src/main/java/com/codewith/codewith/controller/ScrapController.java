package com.codewith.codewith.controller;

import com.codewith.codewith.model.Scrap;
import com.codewith.codewith.model.UserInfo;
import com.codewith.codewith.repository.ScrapRepository;
import com.codewith.codewith.dto.ScrapRequestDto;
import com.codewith.codewith.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final ScrapRepository scrapRepository;
    private final ScrapService scrapService;
    @Resource
    private UserInfo userInfo;

    @GetMapping("/api/scrap")
    public List<Scrap> getScrap(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //String userId = (String)(session.getAttribute("userId"));
        String userId = "id";
        return scrapRepository.findAllByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("페이지가 존재하지 않습니다."));
    }


    //POST (INSERT)
    @PostMapping("/api/scrap")
    public Scrap createScrap(@RequestBody ScrapRequestDto requestDto) {
        Scrap scrap = new Scrap(requestDto);

        boolean present = scrapRepository.findByUserIdAndCourseAndStage(requestDto.getUserId(), requestDto.getCourse(), requestDto.getStage()).isPresent();
        if(present) return null;

        return scrapRepository.save(scrap);
    }

    //PUT (UPDATE)
    @PutMapping("/api/scrap/{id}")
    public Long updateScrap(@PathVariable Long id, @RequestBody ScrapRequestDto requestDto) {
        return scrapService.update(id, requestDto);
    }

    //DELETE
    @DeleteMapping("/api/scrap/{id}")
    public Long deleteScrap(@PathVariable Long id){
        scrapRepository.deleteById(id);
        return id;
    }
}
