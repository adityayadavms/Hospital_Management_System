package com.adityayadavlearning.springboot.hospitalManagement;

import com.adityayadavlearning.springboot.hospitalManagement.dto.BloodGroupCountResponseEntity;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.entity.type.BloodGroupType;
import com.adityayadavlearning.springboot.hospitalManagement.repository.PatientRepository;
import com.adityayadavlearning.springboot.hospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class PatientTest {

    private final PatientRepository patientRepository;
    private final PatientService patientService;


    @Autowired
    public PatientTest(PatientRepository patientRepository, PatientService patientService) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    }

    @Test
    @Commit
    public void testAddPatientWithData() {
        // Create a patient with complete data
        Patient patient = new Patient();
        patient.setName("Test Patient");
        patient.setBirthDate(LocalDate.of(1990, 1, 1));
        patient.setEmail("test@example.com");
        patient.setGender("Male");

        System.out.println("Creating patient: " + patient);

        // Save patient
        Patient savedPatient = patientRepository.save(patient);

        // Verify
        assertNotNull(savedPatient.getId());
        assertEquals("Test Patient", savedPatient.getName());
        assertEquals("test@example.com", savedPatient.getEmail());

        System.out.println("✅ Patient saved with ID: " + savedPatient.getId());
    }

    @Test
    @Commit
    public void testFindAllPatients() {
        // Add multiple patients
        Patient p1 = new Patient();
        p1.setName("Alice Brown");
        p1.setEmail("alice@example.com");
        p1.setGender("Female");
        patientRepository.save(p1);

        Patient p2 = new Patient();
        p2.setName("Charlie Green");
        p2.setEmail("charlie@example.com");
        p2.setGender("Male");
        patientRepository.save(p2);

        // Get all patients
        List<Patient> patients = patientRepository.findAll();

        // Verify
        assertFalse(patients.isEmpty());
        System.out.println("Found " + patients.size() + " patients:");
        patients.forEach(p -> System.out.println(" - " + p.getName() + " (" + p.getEmail() + ")"));
    }

    @Test
    @Commit
    public void testServiceWithRealData() {
        // Create test data
        Patient testPatient = new Patient();
        testPatient.setName("Service Test");
        testPatient.setEmail("service@test.com");
        testPatient.setGender("Other");
        testPatient.setBirthDate(LocalDate.of(1990, 1, 1));

        Patient saved = patientRepository.save(testPatient);
        Long patientId = saved.getId();

        System.out.println("Testing service with patient ID: " + patientId);

        // Use service to retrieve
        Patient retrieved = patientService.getPatientById(patientId);

        // Verify
        assertNotNull(retrieved);
        assertEquals(patientId, retrieved.getId());
        assertEquals("Service Test", retrieved.getName());

        System.out.println("✅ Service test successful! Retrieved: " + retrieved);
    }

    @Test
    public void testPatientCount() {
        long countBefore = patientRepository.count();
        System.out.println("Patients in database: " + countBefore);

        // Add one more
        Patient newPatient = new Patient();
        newPatient.setName("Count Test");
        newPatient.setEmail("count@test.com");
        newPatient.setGender("Male");
        patientRepository.save(newPatient);

        long countAfter = patientRepository.count();
        assertEquals(countBefore + 1, countAfter);
        System.out.println("✅ Patient count increased from " + countBefore + " to " + countAfter);
    }

    @Test
        public void testTransactionMethod(){
            List<Patient> patientList = patientRepository.findAllPatientWithAppointment();

            System.out.println(patientList);
//
//            List<Object[]> bloodGroupList = patientRepository.countEachBloodGroupType();
//            for(Object[] objects: bloodGroupList){
//                System.out.println(objects[0]+":"+objects[1]);
//            }

//        int rowupdated= patientRepository.updateNameWithId("Anjali Singh", 1L);
//        System.out.println(rowupdated);

//        List<BloodGroupCountResponseEntity> bloodGroupList = patientRepository.countEachBloodGroupType();
//            for(BloodGroupCountResponseEntity bloodGroupResponse : bloodGroupList){
//               System.out.println(bloodGroupResponse);
//          }
        }
}