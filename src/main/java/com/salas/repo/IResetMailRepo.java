package com.salas.repo;

import com.salas.model.ResetMail;

public interface IResetMailRepo extends IGenericRepo<ResetMail, Integer>{

    ResetMail findByRandom(String random);
}
