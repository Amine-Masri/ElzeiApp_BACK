package com.example.operation.Justificatif;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

	    private String description;
	    @Lob
	    @Column(name = "fichier",length = 1000)
	    private byte[] fichier;
	    
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

		        public Justificatif build() {
		            return new Justificatif(this.id, this.description, this.fichier);
		        }
		    }

		    // Constructeur avec le builder
		    private Justificatif(Long id, String description, byte[] fichier) {
		        this.id = id;
		        this.description = description;
		        this.fichier = fichier;
		    }
	    
}
