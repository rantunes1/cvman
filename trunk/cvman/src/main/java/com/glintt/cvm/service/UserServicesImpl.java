package com.glintt.cvm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.Role;
import com.glintt.cvm.model.UserConnection;

@Service
@Repository
public class UserServicesImpl extends AbstractService<CVUser> implements UserServices<CVUser>, UserDetailsService,
		UsersConnectionRepository {

	private TextEncryptor textEncryptor;

	public TextEncryptor getTextEncryptor() {
		return this.textEncryptor;
	}

	@Required
	public void setTextEncryptor(TextEncryptor textEncryptor) {
		this.textEncryptor = textEncryptor;
	}

	@Override
	public Collection<CVUser> getAll() {
		return super.getAll("SELECT u FROM CVUser u ORDER BY u.name", CVUser.class);
	}

	@Override
	public CVUser getById(Long id) {
		return super.getById(id, CVUser.class);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id, CVUser.class);
	}

	@Override
	public CVUser findByUsername(String username) {
		Assert.notNull(username);
		try {
			return (CVUser) this.em.createQuery("SELECT u FROM CVUser u WHERE u.username = :username")
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException ignored) {
			return null;
		}
	}

	@Override
	public UserConnection findUserConnectionById(Long userConnectionId) {
		Assert.notNull(userConnectionId);
		return (UserConnection) (this.em.createQuery("SELECT uc FROM UserConnection uc WHERE uc.id = :userConnectionId")
				.setParameter("userConnectionId", userConnectionId).getSingleResult());
	}

	@Override
	public UserConnection findUserConnectionByUsernamePassword(String username, String encodedPassword) {
		CVUser user = findByUsername(username);
		if (user != null) {
			try {
				return (UserConnection) (this.em.createQuery("SELECT uc FROM UserConnection uc WHERE uc.userId = :userId")
						.setParameter("userId", user.getId()).getSingleResult());
			} catch (NoResultException ignored) {
			}
		}
		return null;

	}

	@Override
	@Transactional
	public void updateUserConnection(UserConnection userConnection) {
		Assert.notNull(userConnection, "user connection can not be null");
		Assert.notNull(userConnection.getId(), "can not update a new user connection");

		UserConnection existing = findUserConnectionById(userConnection.getId());
		Assert.notNull(existing, "no user connection found with id " + userConnection.getId());

		existing.setDisplayName(userConnection.getDisplayName());
		existing.setProfileURL(userConnection.getProfileURL());
		existing.setImageURL(userConnection.getImageURL());
		existing.setUserId(userConnection.getUserId());

		this.em.merge(existing);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CVUser user = findByUsername(username);

		boolean enabled = user.isEnabled();
		boolean accountNotExpired = !user.isAccountLocked();
		boolean credentialsNotExpired = !user.isCredentialsExpired();
		boolean accountNotLocked = !user.isAccountLocked();

		return new User(user.getUsername(), user.getPassword(), enabled, accountNotExpired, credentialsNotExpired,
				accountNotLocked, user.getRoles());
	}

	@Override
	@Transactional
	public void createUser(CVUser user, Role distintRole) {
		Assert.isTrue(user != null, "new user can't be null");
		Assert.isNull(user.getId(), "new user already has an id");
		Assert.isTrue(user.getUsername() != null, "new user doesn't have a username");
		Assert.isTrue(user.getPassword() != null, "new user's password is missing");
		Assert.isTrue(user.getRoles().isEmpty(), "new user already has assigned roles");

		CVUser existingUser = findByUsername(user.getUsername());
		Assert.isNull(existingUser, "a user with that username already exists");

		user.getRoles().add(Role.USER);
		if (distintRole != null) {
			user.getRoles().add(distintRole);
		}
		super.create(user);
	}

	private UserConnection findUserConnectionByProvider(String providerId, String providerUserId) {
		UserConnection userConnection = null;
		try {
			userConnection = (UserConnection) this.em
					.createQuery(
							"SELECT uc FROM UserConnection uc WHERE uc.providerId = :providerId AND uc.providerUserId = :providerUserId")
					.setParameter("providerId", providerId).setParameter("providerUserId", providerUserId).getSingleResult();

		} catch (NoResultException ignored) {
		}
		return userConnection;
	}

	@Override
	@Transactional
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		Assert.notNull(connection, "received a null connection from provider");

		ConnectionKey key = connection.getKey();
		Assert.notNull(key, "no key found for current connection");

		String providerId = key.getProviderId();
		String providerUserId = key.getProviderUserId();
		Assert.notNull(providerId, "no provider id found on the connection");
		Assert.notNull(providerUserId, "no provider user id found on the connection");

		List<String> users = new ArrayList<String>();

		UserConnection userConnection = findUserConnectionByProvider(providerId, providerUserId);
		if (userConnection == null) {
			new JPAConnectionRepository(null, this.textEncryptor).addConnection(connection);
			userConnection = findUserConnectionByProvider(providerId, providerUserId);
		}

		Assert.notNull(userConnection, "unable to get a valid user connection for user");

		Long userId = userConnection.getUserId();

		if (userId != null) {
			users.add("" + userId);
		}

		return users;
	}

	@Override
	public void connectUser(String username, String providerId, String providerUserId) {
		// todo Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		// todo Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userConnectionId) {
		return new JPAConnectionRepository(Long.valueOf(userConnectionId), getTextEncryptor());
	}

	private class JPAConnectionRepository implements ConnectionRepository {
		private Long userConnectionId;
		private final TextEncryptor textEncryptor;

		public JPAConnectionRepository(Long userConnectionId, TextEncryptor textEncryptor) {
			this.userConnectionId = userConnectionId;
			this.textEncryptor = textEncryptor;
		}

		@Override
		public MultiValueMap<String, Connection<?>> findAllConnections() {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public List<Connection<?>> findConnections(String providerId) {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public <A> List<Connection<A>> findConnections(Class<A> apiType) {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public Connection<?> getConnection(ConnectionKey connectionKey) {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
			// todo Auto-generated method stub
			return null;
		}

		@Override
		public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
			// todo Auto-generated method stub
			return null;
		}

		@Transactional
		private void updateUserConnection(UserConnection uc, Connection<?> connection) {
			ConnectionData data = connection.createData();
			uc.setDisplayName(data.getDisplayName());
			uc.setProfileURL(data.getProfileUrl());
			uc.setImageURL(data.getImageUrl());
			uc.setAccessToken(encrypt(data.getAccessToken()));
			uc.setSecret(encrypt(data.getSecret()));
			uc.setRefreshToken(encrypt(data.getRefreshToken()));
			uc.setExpireTime(data.getExpireTime());
			uc.setProviderId(data.getProviderId());
			uc.setProviderUserId(data.getProviderUserId());

			UserServicesImpl.this.em.merge(uc);
		}

		@Override
		@Transactional
		public void addConnection(Connection<?> connection) {
			// create a new user connection
			UserConnection uc = new UserConnection();
			UserServicesImpl.this.em.persist(uc);
			updateUserConnection(uc, connection);
		}

		@Override
		@Transactional
		public void updateConnection(Connection<?> connection) {
			UserConnection uc = findUserConnectionById(Long.valueOf(this.userConnectionId));
			updateUserConnection(uc, connection);
		}

		@Override
		public void removeConnections(String providerId) {
			// todo Auto-generated method stub
			String xpto = "";
		}

		@Override
		public void removeConnection(ConnectionKey connectionKey) {
			// todo Auto-generated method stub
			String xpto = "";
		}

		private String encrypt(String text) {
			return text != null ? this.textEncryptor.encrypt(text) : text;
		}

	}

}
