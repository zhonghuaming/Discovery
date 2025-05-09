package com.nepxion.discovery.plugin.configcenter.nacos.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;

public class NacosConfigAdapter extends ConfigAdapter {
    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("nacos-config");

    @Autowired
    private NacosOperation nacosOperation;

    private Listener partialListener;
    private Listener globalListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return nacosOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialListener = subscribeConfig(false);
        globalListener = subscribeConfig(true);
    }

    private Listener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logSubscribeStarted(globalConfig);

        try {
            return nacosOperation.subscribeConfig(group, dataId, executorService, new NacosSubscribeCallback() {
                @Override
                public void callback(String config) {
                    callbackConfig(config, globalConfig);
                }
            });
        } catch (Exception e) {
            logSubscribeFailed(e, globalConfig);
        }

        return null;
    }

    @Override
    public void unsubscribeConfig() {
        unsubscribeConfig(partialListener, false);
        unsubscribeConfig(globalListener, true);

        executorService.shutdownNow();
    }

    private void unsubscribeConfig(Listener listener, boolean globalConfig) {
        if (listener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logUnsubscribeStarted(globalConfig);

        try {
            nacosOperation.unsubscribeConfig(group, dataId, listener);
        } catch (Exception e) {
            logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.NACOS;
    }
}