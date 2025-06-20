package com.rickey.example.spring.boot.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: rickey-c
 * @Date: 2025/6/13 0:55
 */
@Data
@TableName("`user`")
public class UserPO implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
