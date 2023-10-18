package com.capstone.demo.service;

import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.domain.Vote;
import com.capstone.demo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostService postService;
    private final UserService userService;

    public Boolean updateVote(int upOrDown, Long postId, String email){

        Post post = postService.findById(postId);
        User voter = userService.findByEmail(email);

        Vote vote = voteRepository.findByPostAndVoter(post, voter)
                .map(v -> v.update(upOrDown))
                .orElse(Vote.builder()
                        .voter(voter)
                        .post(post)
                        .upOrDown(upOrDown)
                        .build());

        post.getVotes().add(vote);
        voteRepository.save(vote);

        return true;
    }
}
