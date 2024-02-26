package com.example.operation.Operation;

import java.util.Date;

import com.example.operation.Compte.Compte;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "operations")
public class Operation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "Libelle")
    private String Libelle;

    
	@Column(name = "montant")
    private double montant;


    @Column(name = "type")
    private String type;
    
    @Column(name = "DateOP")
    private Date DateOP;
    
    @Column(name = "DateVal")
    private Date DateVal;
    
    
    @Column(name = "status")
    private String status;
    
    
 
	@Column(name = "description")
    private String description;


  
  
    
    @Column(name = "TVAdeductible")
    private String TVAdeductible;
    
    

	@Column(name = "tauxTVA")
    private double tauxTVA;
	
	
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_id") // Nom de la colonne de clé étrangère dans la table "Operation"
   
    private Compte compte;
    
    public String getTVAdeductible() {
		return TVAdeductible;
	}

	public void setTVAdeductible(String tVAdeductible) {
		TVAdeductible = tVAdeductible;
	}

	

	public double getTauxTVA() {
		return tauxTVA;
	}

	public void setTauxTVA(double tauxTVA) {
		this.tauxTVA = tauxTVA;
	}


	

	
	   public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	} 

	public String getLibelle() {
		return Libelle;
	}

	public void setLibelle(String libelle) {
		Libelle = libelle;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateOP() {
		return DateOP;
	}

	public void setDateOP(Date dateOP) {
		DateOP = dateOP;
	}

	public Date getDateVal() {
		return DateVal;
	}

	public void setDateVal(Date dateVal) {
		DateVal = dateVal;
	}


	@JsonCreator
	public Operation(@JsonProperty("id") Long id) {
	    this.id = id;
	}
	public Operation() {}
	

}
