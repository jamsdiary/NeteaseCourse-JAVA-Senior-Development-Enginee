package com.study.architecture.web;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 生成和解析token的工具类
 * 
 * @author Tony
 *
 */
public class JwtTokenProvider {
	SecretKeySpec key;

	/**
	 * @param key
	 *            密钥
	 */
	public JwtTokenProvider(String key) {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS512.getJcaName());
		this.key = secretKeySpec;
	}

	/**
	 * 生成token
	 * 
	 * @return
	 */
	public String createToken(Claims claims) {
		String compactJws = Jwts.builder().setClaims(claims).compressWith(CompressionCodecs.DEFLATE)
				.signWith(SignatureAlgorithm.HS512, key).compact();
		return compactJws;
	}

	/** token转换为 */
	public Claims parseToken(String token) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
