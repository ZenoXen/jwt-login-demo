package com.example.jwtdemo.bo;

import lombok.Data;

/**
 * @author ZhaoHe(hezhao @ dianhun.cn)
 * @date 2022/7/20 17:54
 */

@Data
public class LoginRequest {
    private String username;
    private String password;
}
