package com.example.miniprojecttest.heart.service;

import com.example.miniprojecttest.heart.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    public void clickHeart() {

    }
}
