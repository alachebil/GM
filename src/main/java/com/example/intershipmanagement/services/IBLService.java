package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;
import com.google.zxing.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public String findMostFrequentClient();
    public String getMostFrequentArticle();
    public Map<String, Integer> getArticlesForClient(String codeClient);

    //  historique les articles et ses poid pour tout les clients
    public Map<String, Map<Integer, Map<String, Double>>> getWeeklyEstimationByClient() ;

  //  historique les articles et ses poid pour un client spécifique
    public Map<Integer, Map<String, Double>> getWeeklyEstimationForClient(String clientCode);

//Estimation pour nextweek
    public Map<String, Double> getEstimationForNextWeek(String clientCode);
    public List<BL> getBLsSortedByDateDepotAsc();


}
