package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;

public interface IAccountService {
    void createAccount(CreateUserRequest user);
    void updateAccount();
    void deleteAccount();
    AccountEntity getAccount(String email);
    void getAllAccounts();
}
