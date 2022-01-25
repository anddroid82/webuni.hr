package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.CompanyType;
import hu.webuni.hr.andro.model.Education;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Position;
import hu.webuni.hr.andro.repository.CompanyRepository;
import hu.webuni.hr.andro.repository.CompanyTypeRepository;
import hu.webuni.hr.andro.repository.EmployeeRepository;
import hu.webuni.hr.andro.repository.PositionRepository;

@Service
public class InitDbService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	public void clearDb() {
		employeeRepository.deleteAll();
		positionRepository.deleteAll();
		companyRepository.deleteAll();
		companyTypeRepository.deleteAll();
	}
	
	@Transactional
	public void insertTestData() {
		clearDb();
		employeeRepository.truncateTable();
		
		Position p1=new Position(1L,"Rendszergazda",Education.ERETTSEGI,400000);
		p1=positionRepository.save(p1);
		Position p2=new Position(2L,"Grafikus",Education.ERETTSEGI,350000);
		p2=positionRepository.save(p2);
		Position p3=new Position(3L,"PHP programozó",Education.FOISKOLA,400000);
		p3=positionRepository.save(p3);
		Position p4=new Position(4L,"Java fejlesztő",Education.FOISKOLA,450000);
		p4=positionRepository.save(p4);
		Position p5=new Position(5L,"DevOps mérnök",Education.EGYETEM,600000);
		p5=positionRepository.save(p5);
		Position p6=new Position(6L,"Angular fejlesztő",Education.EGYETEM,800000);
		p6=positionRepository.save(p6);
		Position p7=new Position(7L,"Full stack fejlesztő",Education.FOISKOLA,700000);
		p7=positionRepository.save(p7);
		Position p8=new Position(8L,"Rendszer tervező",Education.EGYETEM,650000);
		p8=positionRepository.save(p8);
		
		Employee e1=employeeRepository.save(new Employee(1L, "Teszt Elek", p1, 450000, LocalDateTime.of(2019, 10, 2, 0, 0),null));
		Employee e2=employeeRepository.save(new Employee(2L, "Nap Pali", p2, 600000, LocalDateTime.of(2015, 4, 12, 0, 0),null));
		Employee e3=employeeRepository.save(new Employee(3L, "Tra Pista", p8, 800000, LocalDateTime.of(2011, 2, 23, 0, 0),null));
		Employee e4=employeeRepository.save(new Employee(4L, "Kovács Kázmér", p6, 700000, LocalDateTime.of(2014, 3, 13, 0, 0),null));
		Employee e5=employeeRepository.save(new Employee(5L, "Fodor Elek", p5, 550000, LocalDateTime.of(2016, 8, 14, 0, 0),null));
		Employee e6=employeeRepository.save(new Employee(6L, "Lukács Tamás", p1, 380000, LocalDateTime.of(2010, 2, 26, 0, 0),null));
		
		CompanyType ct1=new CompanyType(1L,"Kft.");
		ct1=companyTypeRepository.save(ct1);
		CompanyType ct2=new CompanyType(2L,"Zrt.");
		ct2=companyTypeRepository.save(ct2);
		CompanyType ct3=new CompanyType(3L,"Nyrt.");
		ct3=companyTypeRepository.save(ct3);
		CompanyType ct4=new CompanyType(4L,"Bt.");
		ct4=companyTypeRepository.save(ct4);
		
		Company c1 = new Company(1L, "Teszt Cég 01", "Teszt Cím 01",ct1);
		c1=companyRepository.save(c1);
		c1.addEmployee(e1);
		c1.addEmployee(e2);
		
		Company c2 = new Company(2L, "Teszt Cég 02", "Teszt Cím 02",ct2);
		c2=companyRepository.save(c2);
		c2.addEmployee(e3);
		c2.addEmployee(e4);
		
		Company c3 = new Company(3L, "Teszt Cég 03", "Teszt Cím 03",ct3);
		c3=companyRepository.save(c3);
		c3.addEmployee(e5);
		c3.addEmployee(e6);
	}
}
