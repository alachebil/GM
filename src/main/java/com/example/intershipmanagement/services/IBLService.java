package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;
import com.google.zxing.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IBLService {
    BL addBl(BL bl);
    List<BL> getAllBl();
    BL getBlById(long idBl);
    void deleteBl(long idBl);
    BL updateBl(BL Bl);

    List<BL> findBLByRef_Bl(String ref);
    List<BL> findBLByCodeClient(String code);



    public String decodeQrCode(File qrCodeImage)throws IOException, NotFoundException;
    public BL parseQrCodeContent(String decodedText);
    public void  addBlandassignUser(BL Bl, Long userId);
    public void  ValiderDepot(Long BlId, Long userId);
    public void  reclamerDepot(Long BlId, Long userId);
    public void  ReclamerLivreur(Long BlId, Long userId);
    public void  ValiderLivreur(Long BlId, Long userId);

    public List<BL> getBLsSortedByDateDepotAsc();
//    public List<BL> getEventsByUserOrderByParticipation(Long userId);

}
