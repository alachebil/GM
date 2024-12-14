package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.Reclamation;
import com.example.intershipmanagement.entities.BL;

import com.example.intershipmanagement.repositories.ReclamationRepo;
import com.example.intershipmanagement.repositories.IBLRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
@AllArgsConstructor
public class ReclamationService implements IReclamationService {
    IBLRepository blRepository;
    ReclamationRepo reclamationRepo;

    @Override
    public List<Reclamation> retrieveAllreclamation() {
        return reclamationRepo.findAll();
    }

    @Override
    public Reclamation retrievereclamation(Long reclamationId) {
        return reclamationRepo.findById(reclamationId).get();
    }

    @Override
    public Reclamation addreclamation(Reclamation a) {


        return reclamationRepo.save(a);
    }

    @Override
    public void removereclamation(Long reclamationId) {
        reclamationRepo.deleteById(reclamationId);
    }

    @Override
    public Reclamation modifyreclamation(Reclamation reclamation) {
        return reclamationRepo.save(reclamation);
    }

    @Override
    public Reclamation reclamationAndAssign(Reclamation reclamation, long IdBl) {
        BL BL = blRepository.findById(IdBl).get();
        reclamation.setBl(BL);
        return reclamationRepo.save(reclamation);
    }

    @Override
    public Map<String, Integer> statreclamationParBl() {
        Map<String, Integer> statResult = new HashMap<>();
        List<BL> Bls = blRepository.findAll();

        for (BL bl : Bls) {
            // Assuming 'reservations' is properly populated in the Evenement entity
            int reclamationCount = bl.getReclamations() != null ? bl.getReclamations().size() : 0;
            statResult.put(bl.getRef_Bl(), reclamationCount);
        }

        return statResult;
    }

/*******************************   aviiisss anaalysseee   ****************************/



    // Méthode pour analyser la tonalité d'un commentaire
    public String analyserTonalite(String contenu) {
        // Prétraitement du contenu
        String contenuNettoye = pretraiterContenu(contenu);

        // Liste de mots-clés pour la tonalité positive, négative et neutre
        String[] motsClesPositifs = {"bien", "excellent", "merveilleux", "satisfait", "heureux"};
        String[] motsClesNegatifs = {"mauvais", "décevant", "problème", "déplorable", "insatisfait"};
        String[] motsClesNeutres = {"normal", "moyen", "correct", "acceptable"};

        // Analyse de la tonalité du contenu en fonction des mots-clés
        int countPositifs = countMotsCles(contenuNettoye, motsClesPositifs);
        int countNegatifs = countMotsCles(contenuNettoye, motsClesNegatifs);
        int countNeutres = countMotsCles(contenuNettoye, motsClesNeutres);

        // Détermination de la tonalité en fonction du nombre de mots-clés détectés
        if (countPositifs > countNegatifs && countPositifs > countNeutres) {
            return "positif";
        } else if (countNegatifs > countPositifs && countNegatifs > countNeutres) {
            return "négatif";
        } else {
            return "neutre";
        }
    }

    // Méthode de prétraitement du contenu
    public String pretraiterContenu(String contenu) {
        // Supprimer la ponctuation et mettre en minuscules
        String contenuNettoye = contenu.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();

        // Supprimer les mots vides (stop words)
        String[] motsVides = {"le", "l'","la", "les", "de", "du", "des", "un", "une", "dans", "sur", "avec", "par", "pour", "qui", "que", "ce", "cette", "ces", "ceux", "mais"};
        Set<String> setMotsVides = new HashSet<>(Arrays.asList(motsVides));
        String[] mots = contenuNettoye.split("\\s+");
        StringBuilder contenuFinal = new StringBuilder();
        for (String mot : mots) {
            if (!setMotsVides.contains(mot)) {
                contenuFinal.append(mot).append(" ");
            }
        }
        contenuNettoye = contenuFinal.toString().trim();

        // Lemmatisation des mots
////        Document doc = new Document(contenuNettoye);
////        StringBuilder contenuLemm = new StringBuilder();
////        for (Sentence sent : doc.sentences()) {
////            for (String lemma : sent.lemmas()) {
////                contenuLemm.append(lemma).append(" ");
////            }
////        }
//        contenuNettoye = contenuLemm.toString().trim();

        return contenuNettoye;
    }

    // Méthode pour compter le nombre de mots-clés dans un texte
    public int countMotsCles(String contenu, String[] motsCles) {
        int count = 0;
        for (String motCle : motsCles) {
            if (contenu.contains(motCle)) {
                count++;
            }
        }
        return count;
    }













}