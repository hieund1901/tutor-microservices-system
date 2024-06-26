package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.AccountResponseDTO;
import com.microservices.projectfinal.dto.AppointmentResponseDTO;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;

import java.io.InputStream;
import java.util.List;

public interface IAccountService {
    void createAccount(CreateUserRequest user, String userId);
    void updateAccount();
    void deleteAccount();
    AccountEntity getAccountByEmail(String email);
    AccountResponseDTO getAccountByUserId(String userId);
    void getAllAccounts();
    AccountEntity getById(Long id);
    InputStream getAvatar(String avatarPath);
    AppointmentResponseDTO getAppointments(String userId, int page, int size);
}
