package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BLUser;

import java.util.List;
import java.util.Map;

public interface IBLUserService {
    BLUser addBLUser(BLUser BLUser);
    List<BLUser> getAllBLUser();
    BLUser getBLUserById(long idReserv);
    void deleteBLUser(long idReserv);
    BLUser updateBLUser(BLUser BLUser);
    BLUser AddBLUserAndAssign(BLUser BLUser, long IdEvent);
    public String reserver(Long IdEvent, BLUser BLUser);
    /*public List<Object[]> getMonthlyBLUserCountsByYear(int year);*/
    public Map<String, Integer> statBLUserParEvenement();



}
