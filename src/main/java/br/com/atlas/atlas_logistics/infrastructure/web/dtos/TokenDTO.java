package br.com.atlas.atlas_logistics.infrastructure.web.dtos;

import java.util.Date;

public record TokenDTO(String username, Boolean authenticated, Date createdAt, Date expiration, String accessToken,String refreshToken) {
}
