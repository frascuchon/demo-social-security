package demo.info;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info/twitter")
public class TwitterInfoController {

	private Twitter twitter;

	private ConnectionRepository connectionRepository;

	@Inject
	public TwitterInfoController(Twitter twitter,
			ConnectionRepository connectionRepository) {
		this.twitter = twitter;
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> helloTwitter() {
		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			throw new IllegalArgumentException("No twitter provider connection");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("twitterProfile", twitter.userOperations().getUserProfile());
		model.put("friends", twitter.friendOperations().getFriends());

		return model;
	}

}