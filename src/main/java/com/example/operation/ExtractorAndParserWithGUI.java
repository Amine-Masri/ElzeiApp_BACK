package com.example.operation;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.operation.RawOperation.RawOperation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExtractorAndParserWithGUI {
    private static DefaultTableModel tableModel;
    private static List<RawOperation> extractedData;
    private static File selectedFile; // Added class-level variable
    public static void main(String[] args) {
    	try {
            // Create a file chooser for initial PDF selection
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            // Check if a file was selected
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();

                Object[] options = {"Credit Agricole", "Societe Generale"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Choose an option:",
                        "Option Selection",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    processOption(selectedFile, 156, 1421, 2076, 1249);
                } else if (choice == JOptionPane.NO_OPTION) {
                    processOption(selectedFile, 112, 1393, 2250, 1361);
                }

                // Show file chooser for modifying the extracted file
                modifyExtractedFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void processOption(File selectedFile, int x, int y, int width, int height) {
        try {
            // Load the PDF document
            PDDocument document = PDDocument.load(selectedFile);

            // Create PDFRenderer object
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Iterate over all pages and convert each to an image
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                // Render the page as an image
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300);

                // Process the parsed image with specific dimensions
                processImage(image, x, y, width, height);
            }

            // Close the PDF document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String performOCR(BufferedImage image, int x, int y, int width, int height) {
        try {
            // Specify the path to the Tesseract OCR executable
            String tesseractPath = "D:\\Programs\\PFE\\Tesseract-OCR\\tessdata";

            // Create Tesseract instance
            ITesseract tesseract = new Tesseract();

            // Set the path to Tesseract OCR executable
            tesseract.setDatapath(tesseractPath);

            // Crop the image to the specified region
            BufferedImage croppedImage = image.getSubimage(x, y, width, height);

            // Perform OCR on the cropped image
            return tesseract.doOCR(croppedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return "OCR Error";
        }
    }

    private static void processImage(BufferedImage image, int x, int y, int width, int height) {
        try {
            // Perform OCR on the cropped image
            String result = performOCR(image, x, y, width, height);

            // Display a dialog with options to choose the output format
            Object[] formatOptions = {"Text", "Excel", "CSV"};
            int formatChoice = JOptionPane.showOptionDialog(
                    null,
                    "Choose the output format:",
                    "Output Format Selection",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    formatOptions,
                    formatOptions[0]);

            // Process the selected output format
            switch (formatChoice) {
                case JOptionPane.YES_OPTION:
                    // Save the extracted text to a text file
                    String textFilePath = selectedFile.getParent() + File.separator +
                            selectedFile.getName().replace(".pdf", "") + "_extracted.txt";
                    saveTextToFile(result, textFilePath);
                    System.out.println("Text file saved to: " + textFilePath);
                    break;
                case JOptionPane.NO_OPTION:
                    // Save the extracted text to an Excel file
                    String excelFilePath = selectedFile.getParent() + File.separator +
                            selectedFile.getName().replace(".pdf", "") + "_extracted.xlsx";
                    saveTextToExcel(result, excelFilePath);
                    System.out.println("Excel file saved to: " + excelFilePath);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    // Save the extracted text to a CSV file
                    saveDataToCSV(result);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modifyExtractedFile() {
        // Create a new file chooser for modifying the extracted file
        JFileChooser modifyFileChooser = new JFileChooser();
        modifyFileChooser.setDialogTitle("Select Extracted File to Modify");
        modifyFileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int modifyResult = modifyFileChooser.showOpenDialog(null);
        if (modifyResult == JFileChooser.APPROVE_OPTION) {
            File modifyFile = modifyFileChooser.getSelectedFile();

            // Perform modifications or open the file for editing
            // For simplicity, let's just display the content in a dialog
            displayFileContent(modifyFile);
        }
    }

    private static void displayFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            JTextArea textArea = new JTextArea(content.toString());
            textArea.setEditable(true); // Allow modification
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));

            int option = JOptionPane.showConfirmDialog(
                    null,
                    scrollPane,
                    "Extracted File Content",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                // If OK is pressed, save the modified content back to the file
                saveTextToFile(textArea.getText(), file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void saveTextToFile(String text, String outputPath) {
        try {
            // Save the extracted text to a text file
            FileWriter writer = new FileWriter(outputPath);
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveTextToExcel(String text, String excelFilePath) {
        try {
            // Create a new Excel workbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            // Create a new sheet
            XSSFSheet sheet = workbook.createSheet("Sheet1");

            // Split lines based on newline character
            String[] lines = text.split("\\r?\\n");

            // Iterate through lines and add rows to the sheet
            for (int rowIndex = 0; rowIndex < lines.length; rowIndex++) {
                String line = lines[rowIndex];

                // Create a new row
                XSSFRow row = sheet.createRow(rowIndex);

                // Split columns based on "|" delimiter
                String[] columns = line.split("\\|");

                // Iterate through columns and add cells to the row
                for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                    XSSFCell cell = row.createCell(colIndex);
                    cell.setCellValue(columns[colIndex]);
                }
            }

            // Write the workbook to the specified file
            try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOutputStream);
                System.out.println("Excel file saved to: " + excelFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveDataToCSV(String data) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save CSV File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getName().endsWith(".csv")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
                }

                try (FileWriter writer = new FileWriter(fileToSave)) {
                    writer.write(data);
                }

                System.out.println("CSV file saved to: " + fileToSave.getAbsolutePath());
            } else {
                System.out.println("CSV file saving canceled.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
