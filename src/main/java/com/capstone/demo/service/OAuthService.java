package com.capstone.demo.service;

import com.capstone.demo.model.domain.OAuthAttributes;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        User user = OAuthAttributes.extract(registrationId, attributes);
        user.updateProvider(registrationId);
        user = saveOrUpdate(user);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, user, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleValue())),
                customAttribute,
                userNameAttributeName
        );
    }

    private Map customAttribute(Map attributes, String userNameAttributeName, User user, String registrationId) {

        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("username", user.getUsername());
        customAttribute.put("email", user.getEmail());
        return customAttribute;
    }

    private User saveOrUpdate(User user){

        User newUser = userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider())
                .map(u -> u.updateUsernameAndEmail(user.getUsername(), user.getEmail()))
                .orElse(User.of(user.getUsername(), user.getEmail(), user.getProvider(), user.getProviderId()));

        return userRepository.save(newUser);
    }
}
