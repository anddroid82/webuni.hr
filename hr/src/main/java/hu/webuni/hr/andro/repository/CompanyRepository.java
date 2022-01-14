package hu.webuni.hr.andro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.andro.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	@Query("select distinct c from Employee e join e.company c where e.payment>:payment")
	List<Company> findByEmployeePaymentGreaterThan(int payment);
	
	@Query("select c1 from Company c1 where c1.id in (select e.company from Employee e group by e.company having count(e.company)>:ct)")
	List<Company> findByEmployeeCountGreaterThan(long ct);
	
	@Query("select avg(e.payment) as avgpayment, e.rank from Employee e group by e.company, e.rank having e.company=:c order by avg(e.payment) desc")
	List<Object[]> getAvgPaymentByRankOfCompany(Company c);
	
}
