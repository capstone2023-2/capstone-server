package com.capstone.demo.service.problem;


import com.capstone.demo.model.domain.problem.Network;
import com.capstone.demo.repository.problem.NetworkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkService {

    private final NetworkRepository networkRepository;

    public List<Network> getProblems(){
        return networkRepository.findAll();
    }
}