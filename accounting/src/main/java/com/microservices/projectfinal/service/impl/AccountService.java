package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.mapper.AccountMapper;
import com.microservices.projectfinal.repository.AccountRepository;
import com.microservices.projectfinal.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    @Override
    public void createAccount(CreateUserRequest user) {
        AccountEntity account = accountMapper.fromRegisterToAccountEntity(user);
        accountRepository.save(account);
    }

    @Override
    public void updateAccount() {

    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public AccountEntity getAccount(String email) {
        var account = accountRepository.findByEmail(email);
        return account.orElseThrow(
                () -> new RuntimeException("Account not found")
        );
    }

    @Override
    public void getAllAccounts() {

    }
}
