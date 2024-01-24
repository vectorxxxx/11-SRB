package xyz.funnyboy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 16:54:18
 */
public class JwtTest
{
    // 过期时间，毫秒，24小时
    private static long tokenExpiration = 24 * 60 * 60 * 1000;

    // 秘钥
    private static String tokenSignKey = "vectorx123";

    /**
     * 测试生成token
     */
    @Test
    public void testCreateToken() {
        final String token = Jwts.builder()
                                 // ============JWT头============
                                 // 令牌类型
                                 .setHeaderParam("typ", "JWT")
                                 // 签名算法
                                 .setHeaderParam("alg", "HS256")
                                 // ============有效载荷============
                                 // ******默认字段******
                                 // 令牌主题
                                 .setSubject("srb-user")
                                 // 签发者
                                 .setIssuer("vectorx")
                                 // 接收者
                                 .setAudience("vectorx")
                                 // 签发时间
                                 .setIssuedAt(new Date())
                                 // 过期时间
                                 .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                                 // 20秒后可用
                                 .setNotBefore(new Date(System.currentTimeMillis() + 20 * 1000))
                                 // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                                 .setId(UUID
                                         .randomUUID()
                                         .toString())
                                 // ******私有字段******
                                 .claim("nickname", "vectorx")
                                 .claim("avatar", "1.jpg")
                                 // ============签名哈希============
                                 .signWith(SignatureAlgorithm.HS256, tokenSignKey)
                                 // Base64URL算法
                                 .compact();

        System.out.println(token);
    }

    /**
     * 测试获取用户信息
     */
    @Test
    public void testGetUserInfo() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJzcmItdXNlciIsImlzcyI6InZlY3RvcngiLCJhdWQiOiJ2ZWN0b3J4IiwiaWF0IjoxNzA2MDg3MTM3LCJleHAiOjE3MDYxNzM1MzcsIm5iZiI6MTcwNjA4NzE1NywianRpIjoiNjNkYWU4ODgtMzVjNy00NDllLWFlMGYtNjE1ZDViMGZlNzU2Iiwibmlja25hbWUiOiJ2ZWN0b3J4IiwiYXZhdGFyIjoiMS5qcGcifQ.YmiURVZDKjIQ7QSk2P6z_zopz1tfnEBoHwbCrO9rcpY";

        final Jws<Claims> claims = Jwts
                .parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token);

        final Claims body = claims.getBody();

        System.out.println(body.getSubject());
        System.out.println(body.getIssuer());
        System.out.println(body.getAudience());
        System.out.println(body.getIssuedAt());
        System.out.println(body.getExpiration());
        System.out.println(body.getNotBefore());
        System.out.println(body.getId());
        System.out.println(body.get("nickname"));
        System.out.println(body.get("avatar"));

        // srb-user
        // vectorx
        // vectorx
        // Wed Jan 24 17:05:37 CST 2024
        // Thu Jan 25 17:05:37 CST 2024
        // Wed Jan 24 17:05:57 CST 2024
        // 63dae888-35c7-449e-ae0f-615d5b0fe756
        // vectorx
        // 1.jpg
    }
}
