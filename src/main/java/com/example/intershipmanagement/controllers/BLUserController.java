package com.example.intershipmanagement.controllers;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.QRCodeGenerator;
import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.services.BlService;
import com.example.intershipmanagement.services.BLUserService;
import com.google.zxing.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/bluser")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BLUserController {
    BLUserService BLUserService;
    BlService blService;
    private QRCodeGenerator qrCodeGenerator;

    @PostMapping("/add")


    public BLUser addBlUser(@RequestBody BLUser blUser) {
        return BLUserService.addBLUser(blUser);
    }

    @GetMapping("/getAlll")
    public List<BLUser> getAllBLUser() {
        return BLUserService.getAllBLUser();
    }

    @GetMapping("/get/{idBlUser}")
    public BLUser getBLUserById(@PathVariable long idBlUser) {
        return BLUserService.getBLUserById(idBlUser);
    }

    // Order 4
    @DeleteMapping("/delete/{idBLUser}")
    public void deleteBLUser(@PathVariable("idBLUser") long idBLUser) {
        BLUserService.deleteBLUser(idBLUser);
    }

    // Order 5
    @PutMapping("/update")
    public BLUser updateBLUser(@RequestBody BLUser BLUser) {
        return BLUserService.updateBLUser(BLUser);
    }

    @PostMapping("/AddAssign/{idBl}")
    public BLUser addBLUserAndAssignBl(@RequestBody BLUser BLUser, @PathVariable("idBl") long idBl) {
        return BLUserService.AddBLUserAndAssign(BLUser, idBl);
    }

//////// Valider depot :affecter un bl existant pour responsable + etat positif + date depot
@PostMapping("/ValiderDepot/{blId}/{idUser}")
    public void ValiderDepot(@PathVariable long blId ,@PathVariable long idUser){
        blService.ValiderDepot(blId,idUser);
    }

////// Valider depot :affecter un bl existant pour responsable + etat negatif + date depot
@PostMapping("/reclamerDepot/{blId}/{idUser}")
    public void reclamerDepot(@PathVariable long blId ,@PathVariable long idUser){
        blService.reclamerDepot(blId,idUser);
    }

////// Valider reception livreur :affecter un bl existant pour livreur + etat positif + date reception livreur
    @PostMapping("/ValiderLivreur/{blId}/{idUser}")
    public void ValiderLivreur(@PathVariable long blId ,@PathVariable long idUser){
        blService.ValiderLivreur(blId,idUser);
    }

///// reclamer reclamer livreur :affecter un bl existant pour livreur + etat negatif (-) + date reception livreur
    @PostMapping("/ReclamerLivreur/{blId}/{idUser}")
    public void ReclamerLivreur(@PathVariable long blId ,@PathVariable long idUser){
        blService.ReclamerLivreur(blId,idUser);
    }


////// Find all BLUser entities where the associated Utilisateur's 'nom' matches the given name
    @GetMapping("/bls-for-user/{nom}")
    public List<BL> getBLsForUser(@PathVariable String nom) {
        // Call the service to get the BL list for the given user name
        return BLUserService.getBLsForUserByNom(nom);
    }

//    @PostMapping("/BLUser/{idBl}")
//    public String reserver(@PathVariable Long idBl, @RequestBody BLUser BLUser) {
//
//        return BLUserService.reserver(idBl, BLUser);
//    }

    @GetMapping("/pdf")
    public void exportBlPdf(HttpServletResponse response) throws IOException, DocumentException {
        // Set the content type and attachment header
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"bluser.pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Inclure un logo
        Image logo = Image.getInstance("src/main/java/com/example/intershipmanagement/assets/logo.png");
        logo.scalePercent(10);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        // Définir des polices et des couleurs
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(255, 0, 0));
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        Paragraph title = new Paragraph("Liste des Réservations", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(4); // 4 colonnes (Nom de l'événement, Nom de la réservation, Date de réservation, QR Code)
        table.setWidthPercentage(100);
        float[] columnWidths = {1.5f, 2f, 1f, 1f}; // Largeurs des colonnes
        table.setWidths(columnWidths);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Nom de l'événement", headingFont));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Nom de la réservation", headingFont));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Date de réservation", headingFont));
        PdfPCell cell4 = new PdfPCell(new Paragraph("QR Code", headingFont));

        cell1.setBackgroundColor(new BaseColor(255, 0, 0));
        cell2.setBackgroundColor(new BaseColor(255, 0, 0));
        cell3.setBackgroundColor(new BaseColor(255, 0, 0));
        cell4.setBackgroundColor(new BaseColor(255, 0, 0));

        cell1.setPadding(10);
        cell2.setPadding(10);
        cell3.setPadding(10);
        cell4.setPadding(10);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        // Récupérer la liste des réservations
        List<BLUser> BLUsers = BLUserService.getAllBLUser();

        // Vérifier que la liste des réservations n'est pas nulle
        if (BLUsers != null) {
            // Group blusers by bl name
            Map<String, List<BLUser>> groupedBLUsers = new HashMap<>();
            for (BLUser BLUser : BLUsers) {
                String Ref_Bl = BLUser.getBl() != null ? BLUser.getBl().getRefBl() : "Non spécifié";
                groupedBLUsers.computeIfAbsent(Ref_Bl, k -> new ArrayList<>()).add(BLUser);
            }

            // Iterate over each event name and create a section in the PDF
            for (Map.Entry<String, List<BLUser>> entry : groupedBLUsers.entrySet()) {
                String Ref_Bl = entry.getKey();
                List<BLUser> BLUserList = entry.getValue();

                // Add event name as a title
                PdfPCell eventTitleCell = new PdfPCell(new Paragraph(Ref_Bl, titleFont));
                eventTitleCell.setColspan(4);
                eventTitleCell.setPadding(10);
                table.addCell(eventTitleCell);

                // Add blusers under this event
                for (BLUser BLUser : BLUserList) {
                    PdfPCell bluserCell1 = new PdfPCell(new Paragraph(Ref_Bl, textFont));
//                    PdfPCell bluserCell2 = new PdfPCell(new Paragraph(BLUser.getNom_reserv(), textFont));
//                    PdfPCell bluserCell3 = new PdfPCell(new Paragraph(BLUser.getDate_reser().toString(), textFont));

                    bluserCell1.setPadding(10);
//                    bluserCell2.setPadding(10);
//                    bluserCell3.setPadding(10);

                    // Call generateQRCodeImageBytes method and insert the QR code image into the cell
                    PdfPCell qrCodeCell = new PdfPCell();
                    qrCodeCell.setPadding(10);
                    try {
                        byte[] qrCodeImageBytes = generateQRCodeImageBytes(BLUser.getBl().getId());
                        if (qrCodeImageBytes != null) {
                            Image qrCodeImage = Image.getInstance(qrCodeImageBytes);
                            qrCodeImage.scalePercent(50); // Scale the QR code image
                            qrCodeCell.addElement(qrCodeImage);
                        }
                    } catch (IOException | WriterException e) {
                        e.printStackTrace();
                    }

                    // Add cells to the table
                    table.addCell(bluserCell1);
//                    table.addCell(bluserCell2);
//                    table.addCell(bluserCell3);
                    table.addCell(qrCodeCell);
                }
            }
        }

        document.add(table);
        document.close();
    }

    // Méthode pour générer les bytes de l'image du QR code
    private byte[] generateQRCodeImageBytes(long blId) throws WriterException, IOException {
        BL BL = blService.getBlById(blId);
        String fileAttributes = getFileAttributesString(BL);
        return qrCodeGenerator.generateQRCodeImage(fileAttributes);
    }

    // Méthode getFileAttributesString non modifiée
    private String getFileAttributesString(BL Bl) {
        StringBuilder sb = new StringBuilder();
        sb.append("BL ID: ").append(Bl.getId()).append("\n");
        sb.append("BL Nom: ").append(Bl.getRefBl()).append("\n");
        sb.append("BL Lieu: ").append(Bl.getArticleScan()).append("\n");
        sb.append("BL Type: ").append(Bl.getCodeClient()).append("\n");
        sb.append("BL début: ").append(Bl.isEtatDepot()).append("\n");
        sb.append("BL fin: ").append(Bl.getDateReceptionLiv()).append("\n");
        int blusersCount = Bl.getBLUsers().size(); // Utiliser la méthode size() pour obtenir le nombre de réservations
        sb.append("BLUsers Count: ").append(blusersCount).append("\n");

        return sb.toString();
    }

    @GetMapping("/statBLUserParBL")
    public Map<String, Integer> statBLUserParBL(){
        return BLUserService.statBLUserParEvenement();
    }

   /* @GetMapping("/reservations")
    public ResponseEntity<Map<String, Integer>> getBLUserStats() {
        return ResponseEntity.ok(reservService.getBLUserStatsForCurrentYear());
    }*/
}
