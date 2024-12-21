package com.example.intershipmanagement;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.Utilisateur;
import com.example.intershipmanagement.repositories.IBLRepository;
import com.example.intershipmanagement.repositories.IUserRepo;
import com.example.intershipmanagement.services.BlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
// test fdecedzc
@ExtendWith(MockitoExtension.class)
class IntershipManagementApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	IBLRepository blRepository;

	@InjectMocks
	BlService blService;

	BL bl = new BL(1L, "ref123", "client1", "article1", LocalDate.now(), LocalDate.now(), LocalDate.now(), true, false, null, null, null);

	List<BL> blList = new ArrayList<>() {{
		add(new BL(2L, "ref124", "client2", "article2", LocalDate.now(), LocalDate.now(), LocalDate.now(), false, true, null, null, null));
		add(new BL(3L, "ref125", "client3", "article3", LocalDate.now(), LocalDate.now(), LocalDate.now(), true, true, null, null, null));
	}};

	@Test
	void testRetrieveBL() {
		Mockito.when(blRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bl));
		BL retrievedBL = blService.getBlById(1L);
		Assertions.assertNotNull(retrievedBL);
		Assertions.assertEquals("ref123", retrievedBL.getRefBl());
	}

	@Test
	void testRetrieveAllBLs() {
		Mockito.when(blRepository.findAll()).thenReturn(blList);
		List<BL> retrievedBLs = blService.getAllBl();
		Assertions.assertNotNull(retrievedBLs);
		Assertions.assertEquals(2, retrievedBLs.size());
		Assertions.assertEquals("ref124", retrievedBLs.get(0).getRefBl());
	}

	@Test
	void testAddBL() {
		BL newBL = new BL();
		newBL.setRefBl("ref126");
		newBL.setCodeClient("client4");
		newBL.setArticleScan("article4");
		Mockito.when(blRepository.save(Mockito.any())).thenReturn(newBL);
		BL savedBL = blService.addBl(newBL);
		Assertions.assertNotNull(savedBL);
		Assertions.assertEquals("ref126", savedBL.getRefBl());
	}

	@Test
	void testModifyBL() {
		Mockito.when(blRepository.save(Mockito.any())).thenReturn(bl);
		bl.setRefBl("updatedRef123");
		BL modifiedBL = blService.updateBl(bl);
		Assertions.assertNotNull(modifiedBL);
		Assertions.assertEquals("updatedRef123", modifiedBL.getRefBl());
	}

	@Test
	void testRemoveBL() {
		Mockito.doNothing().when(blRepository).deleteById(Mockito.anyLong());
		blService.deleteBl(1L);
		Mockito.verify(blRepository, Mockito.times(1)).deleteById(1L);
	}
}
