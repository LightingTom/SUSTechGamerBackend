package com.example.dao;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserDao extends JpaRepository<User,Integer> {
    //login
    List<User> findUsersByNameAndPassword(String name,String password);

    //findAll
    List<User> findAll();

    //sign in
    @Override
    User saveAndFlush(User user);

    @Modifying
    @Query("update User u set u.password = ?2 where u.uid= ?1")
    void updatePasswordByID(Integer id, String password);

}
