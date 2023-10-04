package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 들고 있음
//JpaRepository를 상속했기 때문에 '@Repository' 어노테이션을 안 써도 된다.
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 규칙 -> Username 문법
    // select * from user where username = 1?
    public User findByUsername(String username);
}
