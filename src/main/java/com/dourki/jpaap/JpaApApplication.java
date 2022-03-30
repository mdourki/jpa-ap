package com.dourki.jpaap;

import com.dourki.jpaap.entities.Patient;
import com.dourki.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(JpaApApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i = 1 ; i <= 100 ; i++)
        {
            patientRepository.save(
                    new Patient(null,"Simo",new Date(),Math.random()<0.5?true:false,(int)(Math.random()*100))
            );
        }

        Page<Patient> patients = patientRepository.findAll(PageRequest.of(0,5));
        System.out.println("+++++++++Nombre total des pages : "+patients.getTotalPages()+"+++++++++");
        System.out.println("+++++++++Nombre total des éléments : "+patients.getTotalElements()+"+++++++++");
        System.out.println("+++++++++Num page : "+patients.getNumber()+"+++++++++");
        List<Patient> content = patients.getContent();
        content.forEach(p->{
            System.out.println("++++++Patient "+p.getId()+"++++++");
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
            System.out.println(p.getScore());
        });

        System.out.println("+++++++++Find Patient By ID+++++++++");
        Patient patient = patientRepository.findById(1L).orElseThrow(()->new
                RuntimeException("Patient not found"));
        System.out.println("++++++Patient "+patient.getId()+"++++++");
        System.out.println(patient.getNom());
        System.out.println(patient.getDateNaissance());
        System.out.println(patient.isMalade());
        System.out.println(patient.getScore());

        patient.setScore(2000);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);

        System.out.println("+++++++++Liste des patients malades+++++++++");
        Page<Patient> byMalade = patientRepository.findByMalade(true,PageRequest.of(0,4));
        byMalade.forEach(p->{
            System.out.println("++++++Patient "+p.getId()+"++++++");
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
            System.out.println(p.getScore());
        });

        System.out.println("+++++++++Chercher patients par nom+++++++++");
        List<Patient> patientsList = patientRepository.chercherPatients("%i%");
        byMalade.forEach(p->{
            System.out.println("++++++Patient "+p.getId()+"++++++");
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
            System.out.println(p.getScore());
        });
    }
}
