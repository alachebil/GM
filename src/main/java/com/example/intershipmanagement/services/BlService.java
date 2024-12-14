package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.EvenementStatistics;
import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.Utilisateur;
import com.example.intershipmanagement.repositories.IBLRepository;
import com.example.intershipmanagement.repositories.IBLUserRepository;
import com.example.intershipmanagement.repositories.IUserRepo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void  addBlandassignUser(BL Bl, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BLUser blUser=new BLUser();
        IBLRepository.save(Bl);
        blUser.setBl(Bl);
        blUser.setUser(user);
        blUserRepository.save(blUser);

    }


    public void  AssignBlUser(Long BlId, Long userId) {
        Utilisateur user=userRepo.findById(userId).get();
        BL bl = IBLRepository.findById(BlId).get();
        BLUser blUser=new BLUser();
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



    public BL findById(Long id) {
        return IBLRepository.findById(id).orElseThrow(() -> new RuntimeException("Événement non trouvé"));
    }


    public List<EvenementStatistics> getEvenementStatistics() {
        List<BL> BLS = IBLRepository.findAll();

        return BLS.stream()
                .map(dataObject -> new EvenementStatistics(dataObject.getRef_Bl(), dataObject.getBLUsers().size()))
                .collect(Collectors.toList());
    }



//    @Override
//    public List<BL> getBlsByUserOrderByParticipation(Long userId) {
//        return IBLRepository.findBlsByUserOrderByParticipation(userId);
//
//    }
}



