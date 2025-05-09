package com.nepxion.discovery.plugin.framework.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.netflix.loadbalancer.Server;

public interface DiscoveryEnabledLoadBalance {
    void filter(List<? extends Server> servers);

    boolean apply(Server server);
}