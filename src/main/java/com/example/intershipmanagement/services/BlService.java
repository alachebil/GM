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
import java.util.HashMap;
import java.util.List;
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
            } else {
                // Collect article scan data
                articleScanBuilder.append(line).append("\n");
            }
        }
        // Set articleScan field (multi-line content)
        newBl.setArticleScan(articleScanBuilder.toString().trim());

        return newBl;
    }

}



