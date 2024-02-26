package com.example.operation.Justificatif;

import com.example.operation.Classe.Classe;
import com.example.operation.Operation.Operation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Entity
@Table(name = "justificatif")
@Data
@AllArgsConstructor 
@NoArgsConstructor


public class Justificatif {

	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(name = "description")
	    private String description;
	    @Lob
	    @Column(name = "fichier",length = 1000)
	    private byte[] fichier;
	    
	    
	    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "operation_id") // Nom de la colonne de clé étrangère dans la table "comptes"
	   
	    private Operation operation;
	    public Operation getOperation() {
			return operation;
		}
		public void setOperation(Operation operation) {
			this.operation = operation;
		}
		// Ajouter le constructeur avec le builder manuellement
	    public static JustificatifBuilder builder() {
	        return new JustificatifBuilder();
	    }
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public byte[] getFichier() {
			return fichier;
		}
		public void setFichier(byte[] fichier) {
			this.fichier = fichier;
		}
		
		 public static class JustificatifBuilder {
		        private Long id;
		        private String description;
		        private byte[] fichier;
		        private Operation operation;
		        JustificatifBuilder() {
		        }

		        public JustificatifBuilder id(Long id) {
		            this.id = id;
		            return this;
		        }

		        public JustificatifBuilder description(String description) {
		            this.description = description;
		            return this;
		        }

		        public JustificatifBuilder fichier(byte[] fichier) {
		            this.fichier = fichier;
		            return this;
		        }
		        public JustificatifBuilder operation(Operation operation) {
				    this.operation = operation;
				    return this;
				}

		        public Justificatif build() {
		            return new Justificatif(this.id, this.description, this.fichier,this.operation);
		        }
		    }
		

		    // Constructeur avec le builder
		 private Justificatif(Long id, String description, byte[] fichier, Operation operation) {
			    this.id = id;
			    this.description = description;
			    this.fichier = fichier;
			    this.operation = operation;
			}
}
