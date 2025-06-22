package br.com.ff.arch_beaver.modules.user.presentation.dto;

import lombok.*;

import java.net.InetAddress;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionDTO {

    private Long userId;
    private String token;
    private InetAddress address;
    private String device;
    private String manufacturer;
    private String model;
    private String versionSo;
    private String versionApp;

}
