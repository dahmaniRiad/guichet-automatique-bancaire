import BDD.Centrale;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static BDD.Centrale.tabInfo_his;

public class Impression {

    /*
    pour limpression d'un ticker selon le mode retrait ou depot et selon le montant passer comme paramètre
     */
    public static void impressionTicket(String Operation,int montant) throws IOException {
        PdfWriter writer = new PdfWriter("TICKET.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A8);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Paragraph rada=new Paragraph("RADA BANQUE").setFont(bold).setFontSize(8);
        Paragraph adresse=new Paragraph("PARIS 45 rue saint peres".toUpperCase()).setFontSize(8).setFont(font);
        Paragraph CP=new Paragraph("75006").setFontSize(8).setFont(font);
        Table automate=new Table(new float[]{1,5});
        Table ticket=new Table(new float[]{1,5});
        Table date=new Table(new float[]{1,5});
        Table heure=new Table(new float[]{1,5});
        Table carte=new Table(new float[]{1,5});
        Table operation=new Table(new float[]{1,5});
        Table label=new Table(new float[]{1,5});
        Date tmp=new Date();
        automate.addCell(new Cell().add(new Paragraph("AUTOMATE :  ").setFontSize(6)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph(String.valueOf((int)(Math.random()*1000000))).setFontSize(6)).setFont(bold).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        ticket.addCell(new Cell().add(new Paragraph("TICKET :  ").setFontSize(6)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph(String.valueOf((int)(Math.random()*1000000))).setFontSize(6)).setFont(bold).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        date.addCell(new Cell().add(new Paragraph("DATE :  ").setFontSize(6)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph(tmp.getDate()+"/0"+tmp.getMonth()+1+"/20"+(tmp.getYear()-100)).setFontSize(6)).setFont(bold).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        heure.addCell(new Cell().add(new Paragraph("HEURE :  ").setFontSize(6)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph(tmp.getHours()+":"+tmp.getMinutes()).setFontSize(6)).setFont(bold).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        carte.addCell(new Cell().add(new Paragraph("CARTE :  ").setFontSize(6)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph("XXXXXXXXXXXX"+AcceuilController.getNumeroCarte().substring(11,15)).setFontSize(6)).setFont(bold).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        operation.addCell(new Cell().add(new Paragraph(Operation.toUpperCase()+" :  ").setFontSize(10).setFont(bold)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph(montant+" EUR").setFontSize(10).setFont(bold)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        label.addCell(new Cell().add(new Paragraph("LABEL :  ").setFontSize(6)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add(new Paragraph("CB").setFontSize(6)).setFont(bold).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        Cell regrouperTable=new Cell().add(automate).add(ticket).add(date).add(heure).add(carte).add(label).setBorder(Border.NO_BORDER);


        document.setMargins(0, 0, 0, 0);
        Table radaTable=new Table(1).addCell(new Cell().add(rada).setHorizontalAlignment(HorizontalAlignment.CENTER));
        document.add(rada.setTextAlignment(TextAlignment.CENTER).setMargin(0));
        document.add(adresse.setTextAlignment(TextAlignment.CENTER).setMargins(1,0,1,0));
        document.add(CP.setTextAlignment(TextAlignment.CENTER).setMargin(0)).add(new Paragraph("")).add(new Paragraph(""));
        document.add(regrouperTable).add(new Paragraph("")).add(new Paragraph("")).add(operation).add(new Paragraph("")).add(new Paragraph(""))
                .add(new Paragraph("MERCI POUR VOTRE VISITE    A BIENTOT").setFontSize(7).setTextAlignment(TextAlignment.CENTER));
        document.close();
        Desktop desktop=Desktop.getDesktop();
        desktop.open(new File("TICKET.pdf"));
    }



    /*
    pour limpression du RIB du client qui son numero est enregistrer dans la page Acceuil
     */
    public static void impressionRibPDF() throws SQLException, IOException {
        Centrale.changerBD();
        ArrayList<String> tmp=null;
        tmp=Centrale.getRIB(AcceuilController.getNumeroCarte());
        String nom=tmp.get(0);
        String prenom=tmp.get(1);
        String adresse=tmp.get(2);
        String IBANt=tmp.get(4);
        String BICt="RADA2018PARISDESCARTES";
        String RIBt=tmp.get(5);
        //#################################################################################################################
        PdfWriter writer = new PdfWriter("RIB.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        //--------------------HEAD--------------------
        Image logo=new Image(ImageDataFactory.create("src/pictures/LOGOImpression.png"));
        logo.setWidth(70);
        logo.setHeight(50);
        Paragraph rada=new Paragraph("RADA BANQUE");
        Table head=new Table(new float[]{1,1,6});
        Cell logoCell =new Cell()
                .add(logo).setWidth(85)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);
        Cell suiteLogoCell =new Cell().
                add(new Paragraph("RADA BANQUE").setFont(bold))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);
        Cell ribIban=new Cell().add(new Paragraph("Relevé d'Identité Bancaire / IBAN "))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        head.addCell(logoCell).addCell(suiteLogoCell).addCell(ribIban).setWidthPercent(100)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        //--------------------MIDDLE--------------------
        Table middle =new Table(2);
        Cell middleCell=new Cell().add(nom.toUpperCase()+" "+prenom.toUpperCase()).add("").add("45 Rue des saint peres").add("75006 paris".toUpperCase()).setHorizontalAlignment(HorizontalAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
        middle.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).addCell(middleCell);
        //--------------------IBAN--------------------
        Table IBAN=new Table(new float[]{1,7});
        IBAN.addCell(new Cell().add("IBAN ' :  ").setWidth(45).setBorder(Border.NO_BORDER)).setMarginTop(20)
                .addCell(new Cell().add(IBANt.toUpperCase()).setFont(bold).setTextAlignment(TextAlignment.CENTER).setWidth(300));
        //--------------------BIC--------------------
        Table BIC=new Table(new float[]{1,7});
        BIC.addCell(new Cell().add("BIC ² :   ").setWidth(45).setBorder(Border.NO_BORDER)).setMarginTop(10)
                .addCell(new Cell().add(BICt.toUpperCase()).setFont(bold).setTextAlignment(TextAlignment.CENTER).setWidth(300));
        //--------------------IBAN--------------------
        Table RIB=new Table(new float[]{1,7});
        RIB.addCell(new Cell().add("RIB ³ :   ").setWidth(45).setBorder(Border.NO_BORDER)).setMarginTop(10)
                .addCell(new Cell().add(RIBt.toUpperCase()).setFont(bold).setTextAlignment(TextAlignment.CENTER).setWidth(300));
        //--------------------Definition the index------------------------
        Paragraph index=new Paragraph("' International Bank Account Number     ² Bank Identifier Code     ³ Relevé d'Identitié Bancaire ").setMarginTop(5);
        //--------------------Regrouper----------------------------
        Cell regrouper=new Cell().add(head).add(middle).add(IBAN).add(BIC).add(RIB).add(index).setBorder(Border.NO_BORDER);
        //--------------------Couper-----------------------------
        Table couper=new Table(new float[]{1,8});
        couper.addCell(new Cell().add(new Image(ImageDataFactory.create("src/pictures/ciseaux.png")).setWidth(25).setHeight(20)).setBorder(Border.NO_BORDER))
                .addCell(new Cell().add("---------------------------------------------------------------------------------------------------------------------------------")
                        .setBorder(Border.NO_BORDER));
        document.setMargins(20, 20, 20, 20);
        document.add(regrouper);
        document.add(couper);
        document.add(regrouper);
        document.add(couper);
        document.add(regrouper);
        document.close();
        Desktop desktop=Desktop.getDesktop();
        desktop.open(new File("RIB.pdf"));
    }


    /*
    pour limpression du ReleverBancaire du client qui son numero est enregistrer dans la page Acceuil
     */
    public static void impressionReleverBancaire() throws SQLException, IOException {
        Centrale.changerBD();
        ArrayList<String> tmp1=null;
        tmp1=Centrale.getRIB(AcceuilController.getNumeroCarte());
        String nom=tmp1.get(0);
        String prenom=tmp1.get(1);
        ArrayList<Historique> tmp=new ArrayList<Historique>();
        Centrale.historique(AcceuilController.getNumeroCarte());
        for(int i=0;i<Centrale.tabInfo_his.size();i++){
            tmp.add(new Historique(tabInfo_his.get(i)[0], tabInfo_his.get(i)[1], tabInfo_his.get(i)[2]));
        }
        //#################################################################################################################
        PdfWriter writer = new PdfWriter("RELEVE-BANCAIRE.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        //--------------------HEAD--------------------
        Image logo=new Image(ImageDataFactory.create("src/pictures/LOGOImpression.png"));
        logo.setWidth(70);
        logo.setHeight(50);
        Paragraph rada=new Paragraph("RADA BANQUE");
        Table head=new Table(new float[]{1,1,6});
        Cell logoCell =new Cell()
                .add(logo).setWidth(85)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);
        Cell suiteLogoCell =new Cell().
                add(new Paragraph("RADA BANQUE").setFont(bold))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);
        Cell ribIban=new Cell().add(new Paragraph("Relevé  Bancaire / HISTORIQUE "))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        head.addCell(logoCell).addCell(suiteLogoCell).addCell(ribIban).setWidthPercent(100)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        //--------------------MIDDLE--------------------
        Table middle =new Table(2);
        Cell middleCell=new Cell().add(nom.toUpperCase()+" "+prenom.toUpperCase()).add("").add("45 Rue des saint peres").add("75006 paris".toUpperCase()).setHorizontalAlignment(HorizontalAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
        middle.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).addCell(middleCell);
        //------------TABLEAU--------------------
        Table historique=new Table(new float[]{3,4,1});
        historique.setWidthPercent(80);
        historique.setMarginTop(20);
        historique.setHorizontalAlignment(HorizontalAlignment.CENTER);
        historique.setTextAlignment(TextAlignment.CENTER);
        Paragraph paragraph=new Paragraph("RELEVER BANCAIRE").setFont(bold).setFontSize(40).setTextAlignment(TextAlignment.CENTER);
        historique.addHeaderCell("DATE D'OPERATION").addHeaderCell("OPRATION").addHeaderCell("MONTANT");
        historique.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
        for(int i=0;i<tmp.size();i++){
            historique.addCell(String.valueOf(tmp.get(i).date))
                    .addCell(String.valueOf(tmp.get(i).operation).toUpperCase())
                    .addCell((tmp.get(i).montant.equals("0"))?"///////":String.valueOf(tmp.get(i).montant));
        }
        document.add(head).add(middle).add(paragraph).add(historique);
        document.close();
        Desktop desktop=Desktop.getDesktop();
        desktop.open(new File("RELEVER-BANCAIRE.pdf"));

    }


    /*
    pour limpression du Solde du client qui son numero est enregistrer dans la page Acceuil
     */
    public static void impressionSolde()throws SQLException, IOException{

        Centrale.changerBD();
        ArrayList<String> tmp=null;
        tmp=Centrale.getRIB(AcceuilController.getNumeroCarte());
        String nom=tmp.get(0);
        String prenom=tmp.get(1);
        String adresse=tmp.get(2);
        String IBANt=tmp.get(4);
        String BICt="RADA2018PARISDESCARTES";
        String RIBt=tmp.get(5);
        //#################################################################################################################
        PdfWriter writer = new PdfWriter("SOLDE.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf,PageSize.A5.rotate());
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        //--------------------HEAD--------------------
        Image logo=new Image(ImageDataFactory.create("src/pictures/LOGOImpression.png"));
        logo.setWidth(70);
        logo.setHeight(50);
        Paragraph rada=new Paragraph("RADA BANQUE");
        Table head=new Table(new float[]{1,1,6});
        Cell logoCell =new Cell()
                .add(logo).setWidth(85)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);
        Cell suiteLogoCell =new Cell().
                add(new Paragraph("RADA BANQUE").setFont(bold))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);
        Cell ribIban=new Cell().add(new Paragraph("SOLDE "))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        head.addCell(logoCell).addCell(suiteLogoCell).addCell(ribIban).setWidthPercent(100)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        //--------------------MIDDLE--------------------
        Table middle =new Table(2);
        Cell middleCell=new Cell().add(nom.toUpperCase()+" "+prenom.toUpperCase()).add("").add("45 Rue des saint peres").add("75006 paris".toUpperCase()).setHorizontalAlignment(HorizontalAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
        middle.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).addCell(middleCell);

        Paragraph paragraph=new Paragraph("SOLDE").setFont(bold).setFontSize(30).setTextAlignment(TextAlignment.CENTER);
paragraph.setMarginTop(30);
        Table solde=new Table(new float[]{1,7});
        solde.addCell(new Cell().add("SOLDE :   ").setWidth(50).setBorder(Border.NO_BORDER)).setMarginTop(10)
                .addCell(new Cell().add(String.valueOf(Centrale.getSolde(AcceuilController.getNumeroCarte()))+" €")
                        .setFont(bold).setTextAlignment(TextAlignment.CENTER).setWidth(300));
        solde.setMarginTop(40);
        solde.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(head).add(middle).add(paragraph).add(solde);
        document.close();
        Desktop desktop=Desktop.getDesktop();
        desktop.open(new File("SOLDE.pdf"));
    }
}
