
package com.example.demo.exception;

// 잘못된 비밀번호 예외 클래스
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
