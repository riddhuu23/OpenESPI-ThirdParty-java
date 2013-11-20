package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@Repository
public class UsagePointRESTRepositoryImpl implements UsagePointRESTRepository {
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate template;

    @Autowired
    UsagePointBuilder builder;

    @Autowired
    private ATOMMarshaller atomMarshaller;

    @Autowired
    private AuthorizationService authorizationService;

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setBuilder(UsagePointBuilder builder) {
        this.builder = builder;
    }

    public void setAtomMarshaller(ATOMMarshaller atomMarshaller) {
        this.atomMarshaller = atomMarshaller;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public List<UsagePoint> findAllByRetailCustomerId(Long retailCustomerId) throws JAXBException {
        return builder.newUsagePoints(unmarshalFeedType(getUsagePoints(findAuthorization(retailCustomerId))));
    }

    @Override
    public UsagePoint findByHashedId(Long retailCustomerId, String usagePointHashedId) throws JAXBException {
        List<UsagePoint> usagePoints = findAllByRetailCustomerId(retailCustomerId);

        for (UsagePoint usagePoint : usagePoints) {
            if (usagePoint.getHashedId().equalsIgnoreCase(usagePointHashedId)) {
                return usagePoint;
            }
        }

        return null;
    }

    private FeedType unmarshalFeedType(HttpEntity<String> response) throws JAXBException {
        ByteArrayInputStream bs = new ByteArrayInputStream(response.getBody().toString().getBytes());
        return atomMarshaller.unmarshal(bs);
    }

    private HttpEntity<String> getUsagePoints(Authorization authorization) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Bearer " + authorization.getAccessToken());
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

        return template.exchange(authorization.getSubscriptionURI(), HttpMethod.GET, requestEntity, String.class);
    }

    private Authorization findAuthorization(Long retailCustomerId) {
        List<Authorization> authorizations = authorizationService.findAllByRetailCustomerId(retailCustomerId);
        return authorizations.get(authorizations.size() - 1);
    }

}
