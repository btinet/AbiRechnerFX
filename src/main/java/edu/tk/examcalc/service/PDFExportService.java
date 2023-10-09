package edu.tk.examcalc.service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import edu.tk.db.global.Session;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.component.Grades;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class PDFExportService {

    private ArrayList<Exam> currentExams = new ArrayList<>();
    private String dest;

    public PDFExportService(ActionEvent actionEvent) {
        Pupil currentPupil = (Pupil) Session.copy("pupil");
        if(currentPupil != null) {
            this.currentExams = currentPupil.getExams();

            while (true) {
                if(this.currentExams != null) {
                    break;
                }
            }
            savePdf(actionEvent);
        }
    }

    private void savePdf(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window stage = source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ergebnis speichern");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Dokument (*.pdf)", "*.pdf"));
        fileChooser.setInitialFileName(Session.copy("pupil")+".pdf");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Logger.getLogger(getClass().getName()).info(file.getAbsolutePath());
            this.dest = file.getAbsolutePath();
        }

        Document document = null;
        PdfFont headline = null;
        PdfFont normal = null;
        PdfFont italic = null;
        try {
            headline = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
            normal = PdfFontFactory.createFont(StandardFonts.COURIER);
            italic = PdfFontFactory.createFont(StandardFonts.COURIER_OBLIQUE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PdfDocument pdf = new PdfDocument(new PdfWriter(dest))) {
            document = new Document(pdf).setTextAlignment(TextAlignment.JUSTIFIED);
            document.add(new Paragraph());
            Pupil currentPupil = (Pupil) Session.copy("pupil");
            if(currentPupil != null) {
                Paragraph title = new Paragraph("Punkteübersicht und Zusatzprüfungen");
                title.setFont(headline);
                title.setFontSize(16);

                Table table = new Table(new float[8]).useAllAvailableWidth();
                table.setMarginTop(25);
                table.setMarginBottom(25);
                table.setFontSize(8);
                table.setFont(normal);

                Cell cell = new Cell(1, 8).add(new Paragraph("Prüfungsergebnisse"));
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(new DeviceRgb(220, 220, 220));
                table.addCell(cell);
                table.addCell(new Cell(1,3));
                table.addCell("Gesamtpunktzahl");
                table.addCell(new Cell(1,4));
                table.startNewRow();

                table.addCell(new Cell(1,3).add(new Paragraph("Kursblock")));
                table.addCell(currentPupil.getCoursePoints().toString());

                Integer sumPoints = currentPupil.getCoursePoints();

                table.addCell(new Cell(1,4));
                table.addCell(new Cell(1,8));

                table.addCell(new Cell(1,4).add(new Paragraph("Prüfungsblock")));
                table.addCell(new Cell(1,2).add(new Paragraph("Zusatzprüfung")));
                table.addCell(new Cell(1,2).add(new Paragraph("Endergebnis")));

                table.addCell("Nr.");
                table.addCell("Fach");
                table.addCell("Punkte");
                table.addCell("Zwischensumme");
                table.addCell("notwendige Punkte");
                table.addCell("Zwischensumme");
                table.addCell("Gesamtpunktzahl");
                table.addCell("Endnote");

                for (Exam exam : this.currentExams) {
                    sumPoints += (exam.getPoints()*4);
                }

                Double grade = 0.0;
                boolean lowerEnd = false;
                int prevKey = 0;
                int currentKey = 0;
                int nextKey = 0;

                ArrayList<Integer> grades = new ArrayList<>(Grades.GRADE.keySet());
                Collections.sort(grades);
                for (Integer entry : grades) {
                    if(sumPoints > entry) {
                        prevKey = entry;
                    }
                    if(sumPoints >= entry) {
                        currentKey = entry;
                        lowerEnd = true;
                    }
                    if(lowerEnd && sumPoints < entry) {
                        grade = Grades.GRADE.get(currentKey);
                        nextKey = entry;
                        break;
                    }
                    if(sumPoints >= 823) {
                        grade = 1.0;
                        nextKey = currentKey;
                    }
                }

                for (Exam exam : this.currentExams) {
                    table.addCell(String.valueOf(exam.getExamNumber()));
                    table.addCell(exam.getSchoolSubject().getLabel());
                    table.addCell(exam.getPoints().toString());
                    table.addCell(String.valueOf(exam.getPoints()*4));
                    if(exam.getExamNumber() != null && exam.getExamNumber() <= 3) {
                        int lowerZwischenSumme = exam.getPoints()*4 +prevKey-sumPoints;
                        int zwischenSumme = exam.getPoints()*4 + nextKey-sumPoints;
                        double lowerX = (double) lowerZwischenSumme*3/4 -(exam.getPoints()*2);
                        double x = (double) zwischenSumme *3/4 -(exam.getPoints()*2);

                        if(x < 15 && sumPoints < 823) {
                            DecimalFormat df = new DecimalFormat("#.#");
                            table.addCell(String.valueOf((int)x));
                            table.addCell(String.valueOf(zwischenSumme));
                            table.addCell(String.valueOf(nextKey));
                            table.addCell(String.valueOf(df.format(grade - .1)));
                        } else {
                            table.addCell(new Cell(1,4));
                        }
                    } else {
                        table.addCell(new Cell(1,4));
                    }
                }

                table.addCell(new Cell(1,3).add(new Paragraph("Gesamtpunktzahl")));
                table.addCell(sumPoints.toString());
                table.addCell(new Cell(1,4));



                table.addCell(new Cell(1,3).add(new Paragraph("Endnote")));
                assert grade != null;
                table.addCell(grade.toString());
                table.addCell(new Cell(1,4));


                document.add(title);
                document.add(new Paragraph(currentPupil + ", geboren am "+ currentPupil.getBirthDate()).setFont(normal).setFontSize(11));
                if(currentPupil.getTutor() != null) {
                    document.add(new Paragraph("Tutorium: " + currentPupil.getTutor().toString()).setFont(normal).setFontSize(11));
                }
                document.add(table);


            }

            Alert pdfSavedAltert = new Alert(Alert.AlertType.INFORMATION);
            Stage alertStage = (Stage) pdfSavedAltert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(MainApplication.appImage);
            pdfSavedAltert.setTitle("Akte exportieren");
            pdfSavedAltert.setHeaderText("Punkteübersicht exportieren");
            pdfSavedAltert.setContentText("PDF-Dokument gespeichert.");
            pdfSavedAltert.show();

        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).info(e.getMessage());
        } catch (NullPointerException ignored) {
        } finally {
            if (document != null) document.close();
        }
    }

}
