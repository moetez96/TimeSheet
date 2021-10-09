package tn.esprit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

    @Autowired
    EntrepriseRepository entrepriseRepoistory;
    @Autowired
    DepartementRepository deptRepoistory;

    public int ajouterEntreprise(Entreprise entreprise) {
        entrepriseRepoistory.save(entreprise);
        return entreprise.getId();
    }

    public int ajouterDepartement(Departement dep) {
        deptRepoistory.save(dep);
        return dep.getId();
    }

    public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
        //Le bout Master de cette relation N:1 est departement
        //donc il faut rajouter l'entreprise a departement
        // ==> c'est l'objet departement(le master) qui va mettre a jour l'association
        //Rappel : la classe qui contient mappedBy represente le bout Slave
        //Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
        if (entrepriseRepoistory.findById(entrepriseId).isPresent()) {
            Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
            Departement depManagedEntity = deptRepoistory.findById(depId).orElse(null);
            if (entrepriseManagedEntity != null && depManagedEntity != null) {
                depManagedEntity.setEntreprise(entrepriseManagedEntity);
                deptRepoistory.save(depManagedEntity);
            }
        }
    }

    public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
        List<String> depNames = new ArrayList<>();
        Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
        if (entrepriseManagedEntity != null) {
            for (Departement dep : entrepriseManagedEntity.getDepartements()) {
                depNames.add(dep.getName());
            }
        }
        return depNames;
    }

    @Transactional
    public void deleteEntrepriseById(int entrepriseId) {
        entrepriseRepoistory.findById(entrepriseId).ifPresent(entreprise -> entrepriseRepoistory.delete(entreprise));
    }

    @Transactional
    public void deleteDepartementById(int depId) {
        deptRepoistory.findById(depId).ifPresent(departement -> deptRepoistory.delete(departement));

    }


    public Entreprise getEntrepriseById(int entrepriseId) {
		return entrepriseRepoistory.findById(entrepriseId).orElse(null);
    }

}
