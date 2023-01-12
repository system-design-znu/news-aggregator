package com.znu.news.data.local.db.entity;

import com.znu.news.model.DomainMapper;
import com.znu.news.model.User;

public class UserEntityMapper implements DomainMapper<UserEntity, User> {

    @Override
    public User mapToDomainModel(UserEntity model) {
        return new User(
                model.getName()
                , model.getEmail()
                , model.getPassword()
        );
    }

    @Override
    public UserEntity mapFromDomainModel(User user) {
        return new UserEntity(
                user.getName()
                , user.getEmail()
                , user.getPassword()
        );
    }
}
