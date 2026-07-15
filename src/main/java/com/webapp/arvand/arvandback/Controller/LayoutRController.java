package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Dto.BannerDto;
import com.webapp.arvand.arvandback.Service.LayoutInterface;
import com.webapp.arvand.arvandback.Dto.TheBestDto;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/layout")
public class LayoutRController {
    @Autowired
    private LayoutInterface layoutInterface;

    @PostMapping("/mobileHero")
    public ResponseEntity<ApiResponse<List<BannerDto>>> mobileHero() throws Exception{
        return layoutInterface.getBanner();
    }
    @PostMapping("/the-best")
    public ResponseEntity<ApiResponse<List<TheBestDto>>> theBest(@RequestBody String type) throws Exception{
        return layoutInterface.getTheBest(type);
    }
}
