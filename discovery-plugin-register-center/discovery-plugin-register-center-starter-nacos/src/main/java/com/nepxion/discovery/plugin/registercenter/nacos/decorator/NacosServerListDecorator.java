package com.nepxion.discovery.plugin.registercenter.nacos.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.cloud.nacos.ribbon.NacosServerList;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;

public class NacosServerListDecorator extends NacosServerList {
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    public NacosServerListDecorator(NacosDiscoveryProperties nacosDiscoveryProperties) {
        super(nacosDiscoveryProperties);
    }

    @Override
    public List<NacosServer> getInitialListOfServers() {
        List<NacosServer> servers = super.getInitialListOfServers();

        filter(servers);

        return servers;
    }

    @Override
    public List<NacosServer> getUpdatedListOfServers() {
        List<NacosServer> servers = super.getUpdatedListOfServers();

        filter(servers);

        return servers;
    }

    private void filter(List<NacosServer> servers) {
        String serviceId = getServiceId();

        loadBalanceListenerExecutor.onGetServers(serviceId, servers);
    }

    public void setLoadBalanceListenerExecutor(LoadBalanceListenerExecutor loadBalanceListenerExecutor) {
        this.loadBalanceListenerExecutor = loadBalanceListenerExecutor;
    }
}