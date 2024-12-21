package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.EvenementStatistics;
import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.Utilisateur;
import com.example.intershipmanagement.repositories.IBLRepository;
import com.example.intershipmanagement.repositories.IBLUserRepository;
import com.example.intershipmanagement.repositories.IUserRepo;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlService implements IBLService {
    IBLRepository IBLRepository;
    IUserRepo userRepo;
    IBLUserRepository blUserRepository;

    //private final Path fileStorageLocation;
    @Override
    public BL addBl(BL Bl) {
        return IBLRepository.save(Bl);
    }



    @Override
    /////////////  ajouter une Bl et l affecter pour user : pour le chauffeur khater houa eli iziid f les bls fel base
    public void  addBlandassignUser(BL Bl, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BLUser blUser=new BLUser();
        LocalDate dateActuelle = LocalDate.now();
        Bl.setDatechauffeur(dateActuelle);
        IBLRepository.save(Bl);
        blUser.setBl(Bl);
        blUser.setUser(user);
        blUserRepository.save(blUser);

    }

    @Override
////////////// Valider depot :affecter un bl existant pour responsable + etat positif + date depot
    public void  ValiderDepot(Long BlId, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BL bl = IBLRepository.findById(BlId).get();
        BLUser blUser=new BLUser();
        LocalDate dateActuelle = LocalDate.now();
        bl.setDateDepot(dateActuelle);
        bl.setEtatDepot(true);
        blUser.setBl(bl);
        blUser.setUser(user);
        blUserRepository.save(blUser);
    }

    @Override
    ////////////// Valider depot :affecter un bl existant pour responsable + etat negatif + date depot
    public void  reclamerDepot(Long BlId, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BL bl = IBLRepository.findById(BlId).get();
        BLUser blUser=new BLUser();
        LocalDate dateActuelle = LocalDate.now();
        bl.setDateDepot(dateActuelle);
        bl.setEtatDepot(false);
        blUser.setBl(bl);
        blUser.setUser(user);
        blUserRepository.save(blUser);
    }

    @Override
    ////////////// Valider reception livreur :affecter un bl existant pour livreur + etat positif + date reception livreur
    public void  ValiderLivreur(Long BlId, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BL bl = IBLRepository.findById(BlId).get();
        BLUser blUser=new BLUser();
        LocalDate dateActuelle = LocalDate.now();
        bl.setDateReceptionLiv(dateActuelle);
        bl.setEtatLivrer(true);
        blUser.setBl(bl);
        blUser.setUser(user);
        blUserRepository.save(blUser);
    }

    @Override
    public String findMostFrequentClient() {
        // Récupérer toutes les entrées BL
        List<BL> bls = IBLRepository.findAll();

        // Utiliser un Map pour compter les occurrences des clients
        Map<String, Long> clientCount = bls.stream()
                .collect(Collectors.groupingBy(BL::getCodeClient, Collectors.counting()));

        // Trouver le client avec le maximum d'occurrences
        return clientCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucun client trouvé"); // Si aucun client n'existe
    }


    // Trouver l'article le plus fréquent dans tout les bls
    @Override
    public String getMostFrequentArticle() {
        List<BL> blList = IBLRepository.findAll();
        Map<String, Integer> articleCount = new HashMap<>();

        // Parcours des articles dans articleScan
        for (BL bl : blList) {
            String[] lines = bl.getArticleScan().split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    String article = line.split("\\s+")[0]; // Extraction du code article
                    articleCount.put(article, articleCount.getOrDefault(article, 0) + 1);
                }
            }
        }

        // Trouver l'article le plus fréquent
        String mostFrequentArticle = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : articleCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentArticle = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return "Most Frequent Article: " + mostFrequentArticle + " (Count: " + maxCount + ")";
    }

////les articles pour client donné trie par le plus demandé
    @Override
    public Map<String, Integer> getArticlesForClient(String codeClient) {
        List<BL> blList = IBLRepository.findAll();
        Map<String, Integer> articleCount = new HashMap<>();

        // Filtrer par codeClient et collecter les articles
        for (BL bl : blList) {
            if (bl.getCodeClient().equalsIgnoreCase(codeClient)) {
                String[] lines = bl.getArticleScan().split("\n");
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        String article = line.split("\\s+")[0]; // Extraction du code article
                        articleCount.put(article, articleCount.getOrDefault(article, 0) + 1);
                    }
                }
            }
        }

        // Trier par nombre de demandes (valeur décroissante)
        return articleCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

 //// historique: Il regroupe les articles par client, par semaine, et calcule la quantité totale de chaque article en termes de poids.
    @Override
    public Map<String, Map<Integer, Map<String, Double>>> getWeeklyEstimationByClient()  {
        // Récupérer toutes les entrées de la base de données
        List<BL> blList = IBLRepository.findAll();

        // Structure pour stocker les résultats
        Map<String, Map<Integer, Map<String, Double>>> estimation = new HashMap<>();

        for (BL bl : blList) {
            String codeClient = bl.getCodeClient();
            LocalDate date = bl.getDatechauffeur();
            int weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfYear()); // Numéro de semaine

            // Initialiser les maps si nécessaire
            estimation.putIfAbsent(codeClient, new HashMap<>());
            estimation.get(codeClient).putIfAbsent(weekNumber, new HashMap<>());

            // Découper les articles
            String[] articles = bl.getArticleScan().split("\n");
            for (String articleLine : articles) {
                if (articleLine.trim().isEmpty()) continue;

                String[] parts = articleLine.trim().split("\\s+");
                if (parts.length >= 2) {
                    String articleName = parts[0]; // Nom de l'article
                    String weightString = parts[1].replace("KG", "").trim(); // Extraire le poids sans l'unité

                    try {
                        double weight = Double.parseDouble(weightString);

                        // Ajouter le poids pour cet article
                        estimation.get(codeClient).get(weekNumber)
                                .merge(articleName, weight, Double::sum);
                    } catch (NumberFormatException e) {
                        // Gestion d'une erreur de conversion
                        System.err.println("Format de poids invalide : " + weightString);
                    }
                }
            }
        }

        return estimation;
    }



    ////////  historique les articles et ses poid pour un client spécifique
    @Override
    public Map<Integer, Map<String, Double>> getWeeklyEstimationForClient(String clientCode) {
        // Récupérer toutes les entrées de la base de données pour le client donné
        List<BL> blList = IBLRepository.findBLByCodeClient(clientCode);

        // Structure pour stocker les résultats
        Map<Integer, Map<String, Double>> estimation = new HashMap<>();

        for (BL bl : blList) {
            LocalDate date = bl.getDatechauffeur();
            int weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfYear()); // Numéro de semaine

            // Initialiser la map pour cette semaine si nécessaire
            estimation.putIfAbsent(weekNumber, new HashMap<>());

            // Découper les articles
            String[] articles = bl.getArticleScan().split("\n");
            for (String articleLine : articles) {
                if (articleLine.trim().isEmpty()) continue;

                String[] parts = articleLine.trim().split("\\s+");
                if (parts.length >= 2) {
                    String articleName = parts[0]; // Nom de l'article
                    String weightString = parts[1].replace("KG", "").trim(); // Extraire le poids sans l'unité

                    try {
                        double weight = Double.parseDouble(weightString);

                        // Ajouter le poids pour cet article
                        estimation.get(weekNumber).merge(articleName, weight, Double::sum);
                    } catch (NumberFormatException e) {
                        // Gestion d'une erreur de conversion
                        System.err.println("Format de poids invalide : " + weightString);
                    }
                }
            }
        }

        return estimation;
    }



    @Override
    public Map<String, Double> getEstimationForNextWeek(String clientCode) {
        // Retrieve all BL entries for the given client from the database
        List<BL> blList = IBLRepository.findBLByCodeClient(clientCode);

        // Structure to store the results for the last 4 weeks
        Map<String, Double> lastFourWeeksEstimation = new HashMap<>();

        // Get the current week and year
        LocalDate now = LocalDate.now();
        int currentWeek = now.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        int currentYear = now.getYear();

        // Structure to store the weights of articles
        Map<String, List<Double>> articleWeights = new HashMap<>();

        // Collect weights of articles for the last 4 weeks
        for (BL bl : blList) {
            LocalDate date = bl.getDatechauffeur();
            int weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
            int year = date.getYear();

            // Check if the date is within the last 4 weeks
            if (year == currentYear && weekNumber >= currentWeek - 3 && weekNumber <= currentWeek
                    || year == currentYear - 1 && currentWeek <= 3 && weekNumber >= 52 - (3 - currentWeek)) {
                // Split the articleScan field to extract article data
                String[] articles = bl.getArticleScan().split("\n");
                for (String articleLine : articles) {
                    if (articleLine.trim().isEmpty()) continue;

                    String[] parts = articleLine.trim().split("\\s+");
                    if (parts.length >= 2) {
                        String articleName = parts[0]; // Article name
                        String weightString = parts[1].replace("KG", "").trim(); // Extract weight without the unit

                        try {
                            double weight = Double.parseDouble(weightString);

                            // Add this weight to the list of weights for this article
                            articleWeights.putIfAbsent(articleName, new ArrayList<>());
                            articleWeights.get(articleName).add(weight);
                        } catch (NumberFormatException e) {
                            // Handle invalid weight format
                            System.err.println("Invalid weight format: " + weightString);
                        }
                    }
                }
            }
        }

        // Calculate the average weights for each article
        for (Map.Entry<String, List<Double>> entry : articleWeights.entrySet()) {
            String articleName = entry.getKey();
            List<Double> weights = entry.getValue();

            double sum = weights.stream().mapToDouble(Double::doubleValue).sum();
            double averageWeight = sum / weights.size();

            // Add the average weight to the result
            lastFourWeeksEstimation.put(articleName, averageWeight);
        }

        return lastFourWeeksEstimation;
    }


    @Override
    public List<BL> getBLsSortedByDateDepotAsc() {
        return IBLRepository.findAllByOrderByDateDepotDesc();
    }

    @Override
    ////////////// Valider reception livreur :affecter un bl existant pour livreur + etat positif + date reception livreur
    public void  ReclamerLivreur(Long BlId, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BL bl = IBLRepository.findById(BlId).get();
        BLUser blUser=new BLUser();
        LocalDate dateActuelle = LocalDate.now();
        bl.setDateReceptionLiv(dateActuelle);
        bl.setEtatLivrer(false);
        blUser.setBl(bl);
        blUser.setUser(user);
        blUserRepository.save(blUser);
    }

    @Override
    public List<BL> getAllBl() {
        return IBLRepository.findAll();
    }

    @Override
    public BL getBlById(long idBl) {
        return IBLRepository.findById(idBl).get();
    }


    @Override
    public void deleteBl(long idBl) {
        IBLRepository.deleteById(idBl);

    }

    @Override
    public BL updateBl(BL Bl) {
        return IBLRepository.save(Bl);
    }

    @Override
    public List<BL> findBLByRef_Bl(String Ref_Bl) {
        return IBLRepository.findBLByRefBl(Ref_Bl);
    }

    @Override
    public List<BL> findBLByCodeClient(String code) {
        return IBLRepository.findBLByCodeClient(code);
    }

    public List<BL> findBLByRefBlOrCodeClient(String Ref_Bl,String code) {
        return IBLRepository.findBLByRefBlOrCodeClient(Ref_Bl,code);
    }



    public List<EvenementStatistics> getBlStatistics() {
        List<BL> BLS = IBLRepository.findAll();

        return BLS.stream()
                .map(dataObject -> new EvenementStatistics(dataObject.getRefBl(), dataObject.getBLUsers().size()))
                .collect(Collectors.toList());
    }


//// decoding qrcode ////////////////////////////////////
@Override
    public String decodeQrCode(File qrCodeImage) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);

        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap, new HashMap<>());
        return result.getText(); // Decoded text from QR code
    }

    @Override
    public BL parseQrCodeContent(String decodedText) {
        BL newBl = new BL();

        String[] lines = decodedText.split("\n");
        StringBuilder articleScanBuilder = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("refBl:")) {
                newBl.setRefBl(line.split(":")[1].trim());
            } else if (line.startsWith("codeClient:")) {
                newBl.setCodeClient(line.split(":")[1].trim());
            } else if (!line.startsWith("articleScan:") && !line.contains("ARTCLE")) {
                // Collect only valid article scan data, skip the header
                articleScanBuilder.append(line.trim()).append("\n");
            }
        }

        // Set articleScan field (multi-line content)
        newBl.setArticleScan(articleScanBuilder.toString().trim());

        return newBl;
    }


}





