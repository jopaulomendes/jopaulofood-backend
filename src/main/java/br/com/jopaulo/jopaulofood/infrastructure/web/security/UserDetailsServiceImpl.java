package br.com.jopaulo.jopaulofood.infrastructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jopaulo.jopaulofood.domain.cliente.ClienteRepository;
import br.com.jopaulo.jopaulofood.domain.restaurante.RestauranteRepository;
import br.com.jopaulo.jopaulofood.domain.usuario.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = clienteRepository.findByEmail(username);
		
		if (usuario == null) {
			usuario = restauranteRepository.findByEmail(username);
			
			if (usuario == null) {
				throw new UsernameNotFoundException(username);
			}
		}
		
		return new LoggedUser(usuario);
	}

}
