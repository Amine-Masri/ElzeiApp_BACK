package com.example.operation.Compte;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.operation.Classe.Classe;
import com.example.operation.Classe.ClasseRepo;

import jakarta.transaction.Transactional;
@Service
public class CompteService implements CompteServicee {
	@Autowired
	 CompteRepo compRepo ;
	@Autowired
    private ClasseRepo classRepo;


	 @Transactional
	    public Compte getCompteById(long id) {
	        return compRepo.findById(id).orElse(null);
	    }

	 // Méthode pour récupérer la liste de comptes pour une classe donnée
	    public List<Compte> getComptesByClasseId(Long classeId) {
	        Classe classe = classRepo.findById(classeId).orElse(null);
	        if (classe != null) {
	            return compRepo.findComptesByClasse(classe);
	        }
	        return null;
	    }

		@Override
		@Transactional
		public Compte save(Compte compte) {
			compRepo.save(compte);
			return compte;
		}

		@Override
		public Compte findById(Long id) {
			if(compRepo.findById(id).isPresent()){
				return compRepo.findById(id).get();
			}
			return null;
		}
		@Override
	    public Classe getClassById(Long id) {

	        Optional<Classe> classeOptional = classRepo.findById(id);
	        if (classeOptional.isPresent()) {
	            return classeOptional.get();
	        } else {
	            // Gérez le cas où aucune classe n'a été trouvée avec l'ID donné.
	            // Vous pouvez retourner null ou jeter une exception appropriée selon vos besoins.
	            return null;
	        }
	    }

		@Override
		public void delete(Long id) {
			Compte compte = findById(id);
			compRepo.delete(compte);
		}
		@Override
		@Transactional
	  public List<Compte> findAll(){

			return compRepo.findAll();
		}

		    public Compte update(Long id, Compte updatedCompte) {
		    	Compte existingCompte = findById(id);
		    	if (existingCompte != null) {
			        // Mettre à jour les propriétés de la classe existante avec les nouvelles valeurs
			    	existingCompte.setDescription(updatedCompte.getDescription());
			    	existingCompte.setLibele(updatedCompte.getLibele());
			    	existingCompte.setCode(updatedCompte.getCode());
			    	existingCompte.setClasse(updatedCompte.getClasse());

			        // Enregistrer la compte mise à jour dans la base de données
			    	return  compRepo.save(existingCompte);

		        }
		        return null;
		    }

		    @Transactional
		    public CompteResponseDTO mapCompteToResponseDTO(Compte compte) {
		        CompteResponseDTO responseDTO = new CompteResponseDTO();
		        responseDTO.setId(compte.getId());
		        responseDTO.setDescription(compte.getDescription());
		        responseDTO.setLibele(compte.getLibele());
		        responseDTO.setCode(compte.getCode());

		        // Inclure l'ID de la classe associée au compte
		        if (compte.getClasse() != null) {
		            responseDTO.setClasse_id(compte.getClasse().getId());

		        }

		        return responseDTO;
		    }


		    @Override
		    @Transactional
		    public List<CompteResponseDTO> findAllResponseDTO() {
		        List<Compte> comptes = compRepo.findAll();
		        List<CompteResponseDTO> responseDTOs = new ArrayList<>();
		        for (Compte compte : comptes) {
		            CompteResponseDTO responseDTO = mapCompteToResponseDTO(compte);
		            responseDTOs.add(responseDTO);
		        }
		        return responseDTOs;
		    }

	}
