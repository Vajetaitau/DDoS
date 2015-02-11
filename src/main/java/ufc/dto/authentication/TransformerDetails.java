package ufc.dto.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class TransformerDetails extends UsernamePasswordAuthenticationToken {

	private final String info;

	public TransformerDetails(Object principal, Object credentials, String info) {
		super(principal, credentials);
		this.info = info;
	}

	public TransformerDetails(Object principal, Object credentials,
			String info, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

}
