package demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import demo.social.GoogleProperties;

@Configuration
@EnableSocial
@EnableConfigurationProperties(GoogleProperties.class)
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private GoogleProperties googleProperties;

	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {

		connectionFactoryConfigurer
				.addConnectionFactory(gooleConnectionFactory());
	}

	public GoogleConnectionFactory gooleConnectionFactory() {
		GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(
				googleProperties.getAppId(), googleProperties .getAppSecret());
		
		googleConnectionFactory.setScope("profile");
		return googleConnectionFactory;
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(
				dataSource, connectionFactoryLocator, Encryptors.noOpText());

		jdbcUsersConnectionRepository
				.setConnectionSignUp(new ConnectionSignUp() {

					@Override
					public String execute(Connection<?> connection) {
						return connection.fetchUserProfile().getUsername();
					}
				});

		return jdbcUsersConnectionRepository;
	}

	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator,
				connectionRepository);
	}
}