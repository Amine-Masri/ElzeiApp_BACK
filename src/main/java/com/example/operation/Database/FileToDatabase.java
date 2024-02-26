package com.example.operation.Database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.example.operation.RawOperation.*;

public class FileToDatabase {

    private static final String DELIMITER = "\\|";

    public static void main(String[] args) {
        // Create a file chooser for selecting the input file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Load Hibernate configuration and create a session
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

                // Begin a transaction
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                // Read the file and insert data into the database
                insertDataFromFile(session, selectedFile);

                // Commit the transaction
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertDataFromFile(Session session, File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                // Create RawOperation object from the line
                RawOperation rawOperation = createRawOperation(fields);

                // Check if a record with the same date and libelle already exists
                RawOperation existingRecord = getExistingRecord(session, rawOperation);

                // If a record exists, update it; otherwise, insert a new record
                if (existingRecord != null) {
                    updateRecord(existingRecord, rawOperation);
                } else {
                    session.save(rawOperation);
                }
            }

            System.out.println("Data inserted into the database successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RawOperation getExistingRecord(Session session, RawOperation rawOperation) {
        // Query to check if a record with the same date and libelle exists
        String query = "FROM RawOperation WHERE date = :date AND libelle = :libelle";
        return (RawOperation) session.createQuery(query)
                .setParameter("date", rawOperation.getDate())
                .setParameter("libelle", rawOperation.getLibelle())
                .setMaxResults(1)
                .uniqueResult();
    }

    private static void updateRecord(RawOperation existingRecord, RawOperation newRecord) {
        // Update the existing record with the values from the new record
        existingRecord.setSoldeA(newRecord.getSoldeA());
        existingRecord.setDateValeur(newRecord.getDateValeur());
        existingRecord.setMontant(newRecord.getMontant());
        existingRecord.setType(newRecord.getType());
        existingRecord.setSoldeN(newRecord.getSoldeN());
        // Update any other fields as needed
    }

    private static RawOperation createRawOperation(String[] fields) {
        try {
            String date = fields[0].isEmpty() ? null : fields[0].trim();
            String dateValeur = fields[1].isEmpty() ? null : fields[1].trim();

            String libelle = fields.length > 2 ? fields[2].trim() : "";
            double montant;
            String type;

            // Determine the position of the double value
            if (fields.length > 3 && isDouble(fields[3])) {
                montant = Double.parseDouble(fields[3]);
                type = "D";
            } else if (fields.length > 4 && isDouble(fields[4])) {
                montant = Double.parseDouble(fields[4]);
                type = "C";
            } else {
                // Set default values if no valid double value found
                montant = 0.0;
                type = "";
            }
            double solde = fields.length > 5 && isDouble(fields[5]) ? Double.parseDouble(fields[5]) : 0.0;

            return new RawOperation(0, 0.0, date, dateValeur, libelle, montant, type, 0.0, null, solde);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Return a default RawOperation with date set to null in case of parsing error
            return new RawOperation(0, 0.0, null, null, "", 0.0, "", 0.0, null, 0.0);
        }
    }




    private static LocalDate parseDate(String dateString) {
        try {
            if (dateString.isEmpty() || dateString.trim().equalsIgnoreCase("NOUVEAU SOLDE AU")) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Return null if parsing fails
            return null;
        }
    }


    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
/*
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.example.operation.RawOperation.*;

public class FileToDatabase {

    private static final String DELIMITER = ",";

    public static void main(String[] args) {
        // Create a file chooser for selecting the input file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Load Hibernate configuration and create a session
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

                // Begin a transaction
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                try {
                    // Read the file and insert data into the database
                    insertDataFromFile(session, selectedFile);

                    // Commit the transaction only after reading the entire file
                    transaction.commit();
                    System.out.println("Data inserted into the database successfully!");

                    // Insert the justificatif file once at the end
                    insertJustificatifFile(session);
                } catch (Exception e) {
                    // Rollback the transaction if an exception occurs
                    transaction.rollback();
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertDataFromFile(Session session, File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                // Create RawOperation object from the line
                RawOperation rawOperation = createRawOperation(fields);

                // Check if a record with the same date and libelle already exists
                RawOperation existingRecord = getExistingRecord(session, rawOperation);

                // If a record exists, update it; otherwise, insert a new record
                if (existingRecord != null) {
                    updateRecord(existingRecord, rawOperation);
                } else {
                    // Prompt user to select a file for justificatif
                    rawOperation.setJustificatif(uploadJustificatifFile());

                    session.save(rawOperation);
                }
            }

            System.out.println("Data inserted into the database successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RawOperation getExistingRecord(Session session, RawOperation rawOperation) {
        // Query to check if a record with the same date and libelle exists
        String query = "FROM RawOperation WHERE date = :date AND libelle = :libelle";
        return (RawOperation) session.createQuery(query)
                .setParameter("date", rawOperation.getDate())
                .setParameter("libelle", rawOperation.getLibelle())
                .setMaxResults(1)
                .uniqueResult();
    }

    private static void updateRecord(RawOperation existingRecord, RawOperation newRecord) {
        // Update the existing record with the values from the new record
        existingRecord.setSoldeA(newRecord.getSoldeA());
        existingRecord.setDateValeur(newRecord.getDateValeur());
        existingRecord.setMontant(newRecord.getMontant());
        existingRecord.setType(newRecord.getType());
        existingRecord.setSoldeN(newRecord.getSoldeN());
        // Update any other fields as needed
    }

    private static RawOperation createRawOperation(String[] fields) {
        try {
            String date = fields[0].isEmpty() ? null : fields[0].trim();
            String dateValeur = fields[1].isEmpty() ? null : fields[1].trim();

            String libelle = fields.length > 2 ? fields[2].trim() : "";
            double montant;
            String type;

            // Determine the position of the double value
            if (fields.length > 3 && isDouble(fields[3])) {
                montant = Double.parseDouble(fields[3]);
                type = "D";
            } else if (fields.length > 4 && isDouble(fields[4])) {
                montant = Double.parseDouble(fields[4]);
                type = "C";
            } else {
                // Set default values if no valid double value found
                montant = 0.0;
                type = "";
            }

            return new RawOperation(0, 0.0, date, dateValeur, libelle, montant, type, 0.0, null);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Return a default RawOperation with date set to null in case of parsing error
            return new RawOperation(0, 0.0, null, null, "", 0.0, "", 0.0, null);
        }
    }

    private static byte[] uploadJustificatifFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Justificatif File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("All files", "*.*"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File justificatifFile = fileChooser.getSelectedFile();

            // Read the content of the justificatif file and return it as a byte array
            try {
                return Files.readAllBytes(justificatifFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null; // Return null if the user cancels the file selection
    }
    private static void insertJustificatifFile(Session session) {
        // Prompt user to select a file for justificatif
        byte[] justificatif = uploadJustificatifFile();
        
        if (justificatif != null) {
            // Create a new RawOperation for justificatif
            RawOperation justificatifOperation = new RawOperation();
            justificatifOperation.setJustificatif(justificatif);

            // Save the RawOperation with justificatif to the database
            session.save(justificatifOperation);
            System.out.println("Justificatif file inserted into the database successfully!");
        }
    }

    private static LocalDate parseDate(String dateString) {
        try {
            if (dateString.isEmpty() || dateString.trim().equalsIgnoreCase("NOUVEAU SOLDE AU")) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Return null if parsing fails
            return null;
        }
    }

    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}*/

