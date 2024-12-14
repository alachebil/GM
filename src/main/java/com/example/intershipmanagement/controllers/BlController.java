package com.example.intershipmanagement.controllers;

import com.cloudinary.Cloudinary;
import com.example.intershipmanagement.entities.EvenementStatistics;
import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.QRCodeGenerator;
import com.example.intershipmanagement.repositories.IBLRepository;
import com.example.intershipmanagement.services.BlService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@Slf4j
@RestController
    @RequestMapping("api/bl")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class BlController {
    BlService blService;
    private QRCodeGenerator qrCodeGenerator;
    private IBLRepository evenementRepository;

    /* private static String UPLOADED_FOLDER = "./main/java/com/example/intershipmanagement/assets/";*/

    @Autowired
    private Cloudinary cloudinary;
    private static final String UPLOAD_DIR = "./main/java/com.example.intershipmanagement/assets";



    @PostMapping("/add")
    public BL addBl(@RequestBody BL BL){
        return blService.addBl(BL);
    }


    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam("id") long id) {
        try {
            BL BL = blService.getBlById(id);
            String fileAttributes = getFileAttributesString(BL);

            byte[] qrCodeImage = qrCodeGenerator.generateQRCodeImage(fileAttributes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return ResponseEntity.ok().headers(headers).body(qrCodeImage);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String getFileAttributesString(BL BL) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bl ID: ").append(BL.getId()).append("\n");
        sb.append("Bl Nom: ").append(BL.getRef_Bl()).append("\n");
//        sb.append("Bl Lieu: ").append(BL.getLieu()).append("\n");
//        sb.append("Bl Type: ").append(BL.getType()).append("\n");
//        sb.append("Date début: ").append(BL.getDate_deb()).append("\n");
//        sb.append("Date fin: ").append(BL.getDate_fin()).append("\n");
        int reservationsCount = BL.getBLUsers().size(); // Utiliser la méthode size() pour obtenir le nombre de réservations
        sb.append("Reservations Count: ").append(reservationsCount).append("\n");

        return sb.toString();
    }

    // Order 2
    @GetMapping("/getAll")
    public List<BL> getAllBl(){
        System.out.println("im here");
        for(BL e: blService.getAllBl())
        {
            System.out.println("reservation"+ e.getBLUsers());
        }
        return blService.getAllBl();
    }

    // Order 3
    @GetMapping("/get/{idBl}")
    public BL getBl(@PathVariable long idBl){
        return blService.getBlById(idBl);
    }

    // Order 4
    @DeleteMapping("/delete/{idBl}")
    public void deleteBl(@PathVariable("idBl") long idTicket){
        blService.deleteBl(idTicket);
    }

    // Order 5
    @PutMapping("/update")
    public BL updateBl(@RequestBody BL Bl){
        return blService.updateBl(Bl);
    }

   ///upload Image///
   /*@PostMapping("/upload")
   public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

       if (file.isEmpty()) {
           redirectAttributes.addFlashAttribute("message", "Veuillez sélectionner un fichier à télécharger");
           return "redirect:uploadStatus";
       }

       try {
           byte[] bytes = file.getBytes();
           Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
           Files.write(path, bytes);

           redirectAttributes.addFlashAttribute("message",
                   "Vous avez réussi à télécharger '" + file.getOriginalFilename() + "'");

       } catch (IOException e) {
           e.printStackTrace();
       }

       return "redirect:/uploadStatus";
   }

    @RequestMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }*/


    @GetMapping("/statistics")
    public ResponseEntity<List<EvenementStatistics>> getDataObjectStatistics() {
        List<EvenementStatistics> statistics = blService.getEvenementStatistics();

        return ResponseEntity.ok(statistics);
    }


//
//    @GetMapping("/eventsByUser/{userId}")
//    public ResponseEntity<List<BL>> getBlsByUserOrderByParticipation(@PathVariable("userId") Long userId) {
//        List<BL> BLS = blService.getBlsByUserOrderByParticipation(userId);
//
//        return ResponseEntity.ok(BLS);
//    }
}







