
package com.example.demo.controller;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.service.PostService;
import com.example.demo.exception.InvalidPasswordException; // 추가된 import 문
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // RESTful 컨트롤러임을 나타냄
@RequestMapping("/posts") // 이 컨트롤러의 모든 요청이 /posts로 시작함을 지정
public class PostController {

    private final PostService postService;

    // 생성자 주입을 사용하여 PostService 주입
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 생성 엔드포인트
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto createdPost = postService.createPost(postRequestDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // 게시물 수정 엔드포인트
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        try {
            PostResponseDto updatedPost = postService.updatePost(id, postRequestDto);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시물 삭제 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @RequestBody String password) {
        try {
            postService.deletePost(id, password);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시물 조회 엔드포인트
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.getPostById(id);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }
}
