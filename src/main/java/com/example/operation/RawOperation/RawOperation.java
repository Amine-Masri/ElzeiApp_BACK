package com.example.operation.RawOperation;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_operation")
public class RawOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "solde_a")
    private double soldeA;

    @Column(name = "date")
    private String date;

    @Column(name = "date_valeur")
    private String dateValeur;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montant")
    private double montant;

    @Column(name = "type") // Column for operation type (C/D)
    private String type;

    @Column(name = "solde_n")
    private double soldeN;

    @Lob
    @Column(name = "justificatif")
    private byte[] justificatif;
    
    @Column(name = "solde")
    private double solde;
    // Constructors, getters, and setters

    public RawOperation() {
    }

    public RawOperation(int id, double soldeA, String date, String dateValeur, String libelle, double montant, String type, double soldeN, byte[] justificatif, double solde) {
        this.id = id;
        this.soldeA = soldeA;
        this.date = date;
        this.dateValeur = dateValeur;
        this.libelle = libelle;
        this.montant = montant;
        this.type = type;
        this.soldeN = soldeN;
        this.justificatif = justificatif;
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateValeur() {
        return dateValeur;
    }

    public void setDateValeur(String dateValeur) {
        this.dateValeur = dateValeur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getSoldeA() {
        return soldeA;
    }

    public void setSoldeA(double soldeA) {
        this.soldeA = soldeA;
    }

    public double getSoldeN() {
        return soldeN;
    }

    public void setSoldeN(double soldeN) {
        this.soldeN = soldeN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public byte[] getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(byte[] justificatif) {
        this.justificatif = justificatif;
    }
    public double getSolde() {
    	return solde;
    }
    public void setSolde(double solde) {
    	this.solde = solde;
    }
    @Override
    public String toString() {
        return "RawOperation{" +
                "id=" + id +
                ", soldeA=" + soldeA +
                ", date='" + date + '\'' +
                ", dateValeur='" + dateValeur + '\'' +
                ", libelle='" + libelle + '\'' +
                ", montant=" + montant +
                ", type='" + type + '\'' +
                ", soldeN=" + soldeN +
                ", solde=" + solde +
                '}';
    }
    // Getters and setters...
}
