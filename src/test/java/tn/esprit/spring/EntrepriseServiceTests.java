package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceTests {

    @Autowired
    EntrepriseServiceImpl entrepriseService;

    @Autowired
    DepartementRepository deptRepoistory;

    @Autowired
    EntrepriseRepository EntRepoistory;

    @Test
    public void ajouterEntreprise() {
        Entreprise entreprise = new Entreprise("testentreprise", "testraison");
        assertNotNull(entrepriseService.ajouterEntreprise(entreprise));
    }

    @Test
    public void ajouterDepartement() {
        Departement dep = new Departement("testnomdep");
        assertNotNull(entrepriseService.ajouterDepartement(dep));
    }

    @Test
    public void affecterDepartementAEntreprise() {
        int depId = 24;
        int entrepriseId = 14;
        assertTrue(deptRepoistory.findById(depId).isPresent());
        entrepriseService.affecterDepartementAEntreprise(depId, entrepriseId);
        Entreprise entreprise = deptRepoistory.findById(depId).get().getEntreprise();
        assertEquals(entreprise.getId(), entrepriseId);

    }

    @Test
    public void getAllDepartementsNamesByEntreprise() {
        int entrepriseId = 14;
        List<String> depNames = entrepriseService.getAllDepartementsNamesByEntreprise(entrepriseId);
        List<String> depNames2 = EntRepoistory.findById(entrepriseId).get().getDepartements().stream().map(Departement::getName)
                .collect(Collectors.toList());

        assertEquals(depNames, depNames2);

    }

    /* @Test
    public void deleteEntrepriseById() {
        int entrepriseId = 14;
        entrepriseService.deleteEntrepriseById(entrepriseId);

        assertFalse(EntRepoistory.findById(entrepriseId).isPresent());
    } */

    /* @Test
    public void deleteDepartementById() {
        int depId = 24;
        entrepriseService.deleteDepartementById(depId);
        assertFalse(deptRepoistory.findById(depId).isPresent());
    } */

    @Test
    public void getEntrepriseById() {
        int entrepriseId = 14;
        assertNotNull(entrepriseService.getEntrepriseById(entrepriseId));
    }
}

