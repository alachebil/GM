package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;

import java.util.List;
import java.util.Map;

public interface IBLUserService {
    BLUser addBLUser(BLUser BLUser);
    List<BLUser> getAllBLUser();
    BLUser getBLUserById(long idReserv);
    void deleteBLUser(long idReserv);
    BLUser updateBLUser(BLUser BLUser);
    BLUser AddBLUserAndAssign(BLUser BLUser, long IdBl);

    public List<BL> getBLsForUserByNom(String nom);
    public String reserver(Long IdBl, BLUser BLUser);
    /*public List<Object[]> getMonthlyBLUserCountsByYear(int year);*/
    public Map<String, Integer> statBLUserParEvenement();



}
