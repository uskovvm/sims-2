package com.carddex.sims2.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
