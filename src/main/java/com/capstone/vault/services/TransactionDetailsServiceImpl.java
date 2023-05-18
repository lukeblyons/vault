package com.capstone.vault.services;

import com.capstone.vault.dtos.AccountInfoDTO;
import com.capstone.vault.dtos.TransactionDetailsDTO;
import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.entities.TransactionDetails;
import com.capstone.vault.repositories.AccountInfoRepository;
import com.capstone.vault.repositories.TransactionDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {
    private final TransactionDetailsRepository transactionDetailsRepository;
    private final AccountInfoRepository accountInfoRepository;

    @Autowired
    public TransactionDetailsServiceImpl(TransactionDetailsRepository transactionDetailsRepository, AccountInfoRepository accountInfoRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.accountInfoRepository = accountInfoRepository;
    }

    @Override
    public TransactionDetailsDTO createTransaction(TransactionDetailsDTO transactionDetailsDTO) {
        TransactionDetails transactionDetails = new TransactionDetails();
        BeanUtils.copyProperties(transactionDetailsDTO, transactionDetails);
        transactionDetails.setDateTime(LocalDateTime.now());
        TransactionDetails savedTransactionDetails = transactionDetailsRepository.save(transactionDetails);
        TransactionDetailsDTO savedTransactionDetailsDTO = new TransactionDetailsDTO();
        BeanUtils.copyProperties(savedTransactionDetails, savedTransactionDetailsDTO);
        return savedTransactionDetailsDTO;
    }

    @Override
    public TransactionDetailsDTO getTransactionById(Long id) {
        TransactionDetails transactionDetails = transactionDetailsRepository.findById(id).orElse(null);
        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO();
        BeanUtils.copyProperties(transactionDetails, transactionDetailsDTO);
        return transactionDetailsDTO;
    }

    @Override
    public TransactionDetailsDTO approveTransaction(Long id) {
        TransactionDetails transactionDetails = transactionDetailsRepository.findById(id).orElse(null);
        if (transactionDetails != null) {
            transactionDetails.setStatus("Approved");
            TransactionDetails approvedTransactionDetails = transactionDetailsRepository.save(transactionDetails);
            TransactionDetailsDTO approvedTransactionDetailsDTO = new TransactionDetailsDTO();
            BeanUtils.copyProperties(approvedTransactionDetails, approvedTransactionDetailsDTO);
            return approvedTransactionDetailsDTO;
        }
        return null;
    }

    @Override
    public TransactionDetailsDTO rejectTransaction(Long id) {
        TransactionDetails transactionDetails = transactionDetailsRepository.findById(id).orElse(null);
        if (transactionDetails != null) {
            transactionDetails.setStatus("Rejected");
            TransactionDetails rejectedTransactionDetails = transactionDetailsRepository.save(transactionDetails);
            TransactionDetailsDTO rejectedTransactionDetailsDTO = new TransactionDetailsDTO();
            BeanUtils.copyProperties(rejectedTransactionDetails, rejectedTransactionDetailsDTO);
            return rejectedTransactionDetailsDTO;
        }
        return null;
    }

    @Override
    public List<TransactionDetailsDTO> getAllTransactions() {
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        return transactionDetailsList.stream()
                .map(this::convertToTransactionDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailsDTO> getTransactionsByAccount(Long accountId) {
        AccountInfo accountInfo = accountInfoRepository.findById(accountId).orElse(null);
        if (accountInfo != null) {
            List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findByReceiverAccountOrderByDateTimeDesc(accountInfo);
            return transactionDetailsList.stream()
                    .map(this::convertToTransactionDetailsDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<TransactionDetailsDTO> getTransactionsByUser(Long userId) {
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findByReceiverAccountUserInfoIdOrSenderAccountUserInfoIdOrderByDateTimeDesc(userId, userId);
        return transactionDetailsList.stream()
                .map(this::convertToTransactionDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDetailsDTO updateTransaction(Long id, TransactionDetailsDTO transactionDetailsDTO) {
        TransactionDetails existingTransactionDetails = transactionDetailsRepository.findById(id).orElse(null);
        if (existingTransactionDetails != null) {
            BeanUtils.copyProperties(transactionDetailsDTO, existingTransactionDetails);
            TransactionDetails updatedTransactionDetails = transactionDetailsRepository.save(existingTransactionDetails);
            return convertToTransactionDetailsDTO(updatedTransactionDetails);
        }
        return null;
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionDetailsRepository.deleteById(id);
    }

    private TransactionDetailsDTO convertToTransactionDetailsDTO(TransactionDetails transactionDetails) {
        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO();
        BeanUtils.copyProperties(transactionDetails, transactionDetailsDTO);
        return transactionDetailsDTO;
    }
}
