package com.salas.service;

import com.salas.model.ResetMail;

public interface IResetMailService {
    ResetMail findByRandom(String random);;
    void save(ResetMail resetMail);
    void delete(ResetMail resetMail);
}
