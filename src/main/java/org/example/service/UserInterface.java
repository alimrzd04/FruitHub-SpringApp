package org.example.service;

import org.example.dto.LoginDto;
import org.example.dto.RegisterDto;

public interface UserInterface {

  String registerUser(RegisterDto registerDto);
  Object loginUser(LoginDto loginDto);
}
