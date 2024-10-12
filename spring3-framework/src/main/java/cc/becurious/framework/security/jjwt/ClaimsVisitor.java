package cc.becurious.framework.security.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SupportedJwtVisitor;

public class ClaimsVisitor extends SupportedJwtVisitor<Claims> {

    @Override
    public Claims onVerifiedClaims(Jws<Claims> jws) {
        return jws.getPayload();
    }
}
