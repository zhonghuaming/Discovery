package com.nepxion.discovery.console.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.console.resource.ServiceResource;

public class VersionViewRestInvoker extends AbstractRestInvoker {
    public VersionViewRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate) {
        super(serviceResource, serviceId, restTemplate);
    }

    @Override
    protected String getDescription() {
        return "Version viewed";
    }

    @Override
    protected String getSuffixPath() {
        return "version/view";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }
}