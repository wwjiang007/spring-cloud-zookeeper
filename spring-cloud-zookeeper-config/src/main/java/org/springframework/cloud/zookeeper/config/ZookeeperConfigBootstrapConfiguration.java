/*
 * Copyright 2015-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.zookeeper.ConditionalOnZookeeperEnabled;
import org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * Bootstrap Configuration for Zookeeper Configuration.
 *
 * @author Spencer Gibb
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnZookeeperEnabled
@Import(ZookeeperAutoConfiguration.class)
public class ZookeeperConfigBootstrapConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ZookeeperPropertySourceLocator zookeeperPropertySourceLocator(
			CuratorFramework curator, ZookeeperConfigProperties properties) {
		return new ZookeeperPropertySourceLocator(curator, properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public ZookeeperConfigProperties zookeeperConfigProperties(Environment env) {
		ZookeeperConfigProperties properties = new ZookeeperConfigProperties();
		if (StringUtils.isEmpty(properties.getName())) {
			properties.setName(env.getProperty("spring.application.name", "application"));
		}
		return properties;
	}

}
