package tn.esprit.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceTests {

    @Autowired
    EmployeServiceImpl employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    DepartementRepository deptRepoistory;

    @Autowired
    ContratRepository contratRepoistory;

    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    EntrepriseRepository entrepriseRepoistory;

    @Autowired
    MissionRepository missionRepository;

    @Test
    public void ajouterEmploye() {
        Employe employe = new Employe("testnom", "testprenom", "test@test.com", true, Role.CHEF_DEPARTEMENT);
        int id = employeService.ajouterEmploye(employe);
        assertTrue(employeRepository.findById(id).isPresent());
    }

    @Test
    public void mettreAjourEmailByEmployeId() {
        String email = "test1@test.com";
        int employeId = 8;
        employeService.mettreAjourEmailByEmployeId(email, employeId);
        assertEquals(employeRepository.findById(employeId).get().getEmail(), email);
    }

    @Test
    public void affecterEmployeADepartement() {
        int employeId = 8;
        int depId = 24;
        assertTrue(employeRepository.findById(employeId).isPresent());
        employeService.affecterEmployeADepartement(employeId, depId);
        Employe employe = employeRepository.findById(employeId).get();
        List<Integer> employeList = deptRepoistory.findById(depId).get().getEmployes()
                .stream().map(Employe::getId)
                .collect(Collectors.toList());
        assertTrue(employeList.contains(employe.getId()));
    }

    @Test
    public void desaffecterEmployeDuDepartement() {
        int employeId = 8;
        int depId = 24;
        assertTrue(employeRepository.findById(employeId).isPresent());
        employeService.desaffecterEmployeDuDepartement(employeId, depId);
        Employe employe = employeRepository.findById(employeId).get();
        List<Integer> employeList = deptRepoistory.findById(depId).get().getEmployes()
                .stream().map(Employe::getId)
                .collect(Collectors.toList());
        assertTrue(employeList.contains(employe.getId()));
    }

    @Test
    public void ajouterContrat() {
        Contrat contrat = new Contrat(new Date(), "CDI", 3500);
        int id = employeService.ajouterContrat(contrat);
        assertTrue(contratRepoistory.findById(id).isPresent());
    }

    @Test
    public void affecterContratAEmploye() {
        int contratId = 10;
        int employeId = 8;
        assertTrue(employeRepository.findById(employeId).isPresent());
        employeService.affecterContratAEmploye(contratId, employeId);
        Contrat contrat = contratRepoistory.findById(contratId).get();
        assertEquals(contrat.getEmploye().getId(),employeId);
    }

    @Test
    public void getEmployePrenomById() {
        int employeId = 8;
        String prenom = employeService.getEmployePrenomById(employeId);
        Employe employe = employeRepository.findById(employeId).get();
        assertEquals(employe.getPrenom(), prenom);
    }
    /* @Test
    public void deleteEmployeById() {
        int employeId = 32;
        employeService.deleteEmployeById(employeId);
        assertFalse(employeRepository.findById(employeId).isPresent());
    } */
    /* @Test
    public void deleteContratById() {
        int contratId = 10;
        employeService.deleteContratById(contratId);
        assertFalse(contratRepoistory.findById(contratId).isPresent());
    } */
    @Test
    public void getNombreEmployeJPQL() {
        List<Employe> employeList = employeService.getAllEmployes();
        int nb = employeService.getNombreEmployeJPQL();
        assertEquals(employeList.size(), nb);
    }

    @Test
    public void getAllEmployeNamesJPQL() {
        List<String> list = employeService.getAllEmployeNamesJPQL();
        List<String> list1 = employeService.getAllEmployes().stream().map(Employe::getNom).collect(Collectors.toList());
        assertEquals(list, list1);
    }

    @Test
    public void getAllEmployeByEntreprise() {
        assertTrue(entrepriseRepoistory.findById(10).isPresent());
        Entreprise entreprise = entrepriseRepoistory.findById(10).get();
        assertNotNull(employeService.getAllEmployeByEntreprise(entreprise));
    }

    @Test
    public void mettreAjourEmailByEmployeIdJPQL() {
        String email = "test2@test.com";
        int employeId = 8;
        employeService.mettreAjourEmailByEmployeIdJPQL(email, employeId);
        assertEquals(employeRepository.findById(employeId).get().getEmail(), email);
    }
    /* @Test
    public void deleteAllContratJPQL() {
        employeService.deleteAllContratJPQL();
        assertTrue(contratRepoistory.count() < 0);
    }
    */
    @Test
    public void getSalaireByEmployeIdJPQL() {
        int employeId = 8;
        double salaire = employeService.getSalaireByEmployeIdJPQL(employeId);
        assertTrue(employeRepository.findById(employeId).isPresent() &&
                employeRepository.findById(employeId).get().getContrat().getSalaire() == salaire);
    }

    @Test
    public void getSalaireMoyenByDepartementId() {
        int departementId = 24;
        double salaryMoy = 0.0;
        salaryMoy = employeService.getSalaireMoyenByDepartementId(departementId);
        assertNotEquals(salaryMoy, 0.0);
    }
    /* @Test
    public void getTimesheetsByMissionAndDate() {
        Employe employe = employeRepository.findById(8).get();
        Mission mission = missionRepository.findById(6).get();
        Date dateDebut = new Date();
        Date dateFin = new Date();
        assertNotNull(employeService.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin));
    } */
    @Test
    public void getAllEmployes() {
        assertFalse(employeService.getAllEmployes().isEmpty());
    }

    @Test
    public void test() {
        Employe employe = new Employe("testnom", "testprenom", "test@test.com", true, Role.CHEF_DEPARTEMENT);

        assertNotNull(employeRepository.save(employe));
        assertTrue(employeRepository.findById(8).isPresent());
        assertTrue(deptRepoistory.findById(24).isPresent());
        assertTrue(contratRepoistory.findById(10).isPresent());
        assertTrue(missionRepository.findById(10).isPresent());
        assertTrue(missionRepository.findById(7).isPresent());
        }
    }
