package tn.esprit.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

import javax.validation.constraints.AssertTrue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceTests {

    @Autowired
    TimesheetServiceImpl timesheetService;

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    DepartementRepository deptRepoistory;

    @Autowired
    EmployeRepository employeRepository;

    @Test
    public void ajouterMission() {
        Mission mission = new Mission("test mission", "test description");
        int id = timesheetService.ajouterMission(mission);
        assertTrue(missionRepository.findById(id).isPresent());
    }

    @Test
    public void affecterMissionADepartement() {
        int missionId = 7;
        int depId = 24;
        timesheetService.affecterMissionADepartement(missionId, depId);
        List<Integer> missionList = deptRepoistory.findById(depId).get().getMissions().stream().map(Mission::getId).collect(Collectors.toList());
        assertTrue(missionList.contains(missionId));

    }

    @Test
    public void ajouterTimesheet() {
        int missionId = 7;
        int employeId = 8;
        Date dateDebut = new Date();
        Date dateFin = new Date();
        timesheetService.ajouterTimesheet(missionId, employeId, dateDebut, dateFin);
        assertNotNull(timesheetRepository.getTimesheetsByMissionAndDate(employeRepository.findById(employeId).get(),
                missionRepository.findById(missionId).get(), dateDebut, dateFin));
    }


    // TODO: to complete
    @Test
    public void validerTimesheet() {
        int missionId = 7;
        int employeId = 8;
        Date dateDebut = new Date();
        Date dateFin = new Date();
        int validateurId = 10;
        timesheetService.validerTimesheet(missionId, employeId, dateDebut, dateFin, validateurId);
        TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
        System.out.println(timesheetRepository.findBytimesheetPK(timesheetPK).isValide());
        assertTrue(timesheetRepository.findBytimesheetPK(timesheetPK).isValide());
    }

    @Test
    public void findAllMissionByEmployeJPQL() {
        int employeId = 8;
        List<Integer> missionList = timesheetService.findAllMissionByEmployeJPQL(employeId).stream().map(Mission::getId).collect(Collectors.toList());
        assertEquals(missionList, timesheetService.findAllMissionByEmployeJPQL(employeId).stream().map(Mission::getId).collect(Collectors.toList()));
    }

    @Test
    public void getAllEmployeByMission() {
        int missionId = 7;
        List<Integer> employeList = timesheetService.getAllEmployeByMission(missionId).stream().map(Employe::getId).collect(Collectors.toList());
        assertEquals(employeList, timesheetRepository.getAllEmployeByMission(missionId).stream().map(Employe::getId).collect(Collectors.toList()));
    }
}
