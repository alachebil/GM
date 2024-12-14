package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.EvenementStatistics;
import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.repositories.IBLRepository;
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
    //private final Path fileStorageLocation;
    @Override
    public BL addBl(BL Bl) {
        return IBLRepository.save(Bl);
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



