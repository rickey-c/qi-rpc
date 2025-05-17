package com.rickey.rpc.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户实体
 * @Author: rickey-c
 * @Date: 2025/5/17 21:27
 */
@Data
@AllArgsConstructor
public class User implements Serializable {
    private String name;
}
