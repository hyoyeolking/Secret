
package com.example.demo.service;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.entity.Post;
import com.example.demo.exception.InvalidPasswordException;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 게시물 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시물 수정
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) throws InvalidPasswordException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        
        // 비밀번호 확인
        if (!post.getPassword().equals(postRequestDto.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        
        // 게시물 내용 수정
        post.update(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시물 삭제
    public void deletePost(Long id, String password) throws InvalidPasswordException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        
        // 비밀번호 확인
        if (!post.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }
        
        // 게시물 삭제
        postRepository.delete(post);
    }

    // 게시물 조회
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        return new PostResponseDto(post);
    }
}
