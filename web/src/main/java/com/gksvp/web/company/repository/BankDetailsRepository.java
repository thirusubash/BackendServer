package com.gksvp.web.company.repository;


import com.gksvp.web.company.entity.BankDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    Page<BankDetails> findByCompany_Id(Long companyId, Pageable pageable);
    Page<BankDetails> findByCompany_IdAndAccountNumberContainingOrCompany_IdAndIfscContainingOrCompany_IdAndUpiIdContainingOrCompany_IdAndBankNameContaining(
            Long companyId1, String accountNumber,
            Long companyId2, String ifsc,
            Long companyId3, String upiId,
            Long companyId4, String bankName,
            Pageable pageable);


}
