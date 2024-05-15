package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.AccountResponseDTO;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;

public interface IAccountService {
    void createAccount(CreateUserRequest user);
    void updateAccount();
    void deleteAccount();
    AccountEntity getAccountByEmail(String email);
    AccountResponseDTO getAccountById(Long id);
    void getAllAccounts();
}
