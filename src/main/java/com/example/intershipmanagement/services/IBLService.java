package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;

import java.util.List;

public interface IBLService {
    BL addBl(BL bl);
    List<BL> getAllBl();
    BL getBlById(long idBl);
    void deleteBl(long idBl);
    BL updateBl(BL Bl);
//    public List<BL> getEventsByUserOrderByParticipation(Long userId);
/// testtt

}
