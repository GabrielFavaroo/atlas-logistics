package br.com.atlas.atlas_logistics.infrastructure;

import java.util.Date;

public record TokenDTO(String username, Boolean autheticated, Date createdAt, Date expiration, String accessToken,String refreshToken) {
}
