package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.Reclamation;

import java.util.List;
import java.util.Map;

public interface IReclamationService {
    public List<Reclamation> retrieveAllreclamation();
    public Reclamation retrievereclamation (Long avis);
    public Reclamation addreclamation (Reclamation a);
    public void removereclamation (Long reclamation);
    public Reclamation modifyreclamation (Reclamation reclamation);
    public Reclamation reclamationAndAssign(Reclamation reclamation, long IdBl);
    public List<Reclamation> retrieveReclamationByBl(Long BlId);
    public Map<String, Integer> statreclamationParBl();


    public int countMotsCles(String contenu, String[] motsCles);
    public String pretraiterContenu(String contenu);
    public String analyserTonalite(String contenu);
}
