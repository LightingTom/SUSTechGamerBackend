package com.example.dao;

import com.example.domain.User_game_dlc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ugdDao extends JpaRepository<User_game_dlc, Integer> {
    @Override
    User_game_dlc saveAndFlush(User_game_dlc info);

}
