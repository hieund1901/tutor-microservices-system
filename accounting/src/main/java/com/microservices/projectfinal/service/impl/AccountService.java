package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.AccountResponseDTO;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.mapper.AccountMapper;
import com.microservices.projectfinal.repository.AccountRepository;
import com.microservices.projectfinal.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public AccountEntity getAccountByEmail(String email) {
        var account = accountRepository.findByEmail(email);
        return account.orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        var account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );
        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .birth(account.getBirth())
                .gender(String.valueOf(account.getGender()))
                .address(account.getAddress())
                .avatarPath(account.getAvatarPath())
                .createdAt(account.getCreatedAt())
                .modifiedAt(account.getModifiedAt())
                .build();
    }

    @Override
    public void getAllAccounts() {

    }
}
