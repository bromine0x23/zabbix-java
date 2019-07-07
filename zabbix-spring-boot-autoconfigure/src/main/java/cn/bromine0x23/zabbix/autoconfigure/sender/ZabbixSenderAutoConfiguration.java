/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.autoconfigure.sender;

import cn.bromine0x23.zabbix.sender.ZabbixSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.Arrays;

/**
 * Created on 18/9/19.
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
@Configuration
@ConditionalOnClass(ZabbixSender.class)
@ConditionalOnMissingBean(ZabbixSender.class)
@EnableConfigurationProperties(ZabbixSenderProperties.class)
@Import(ZabbixSenderAutoConfiguration.ZabbixSenderConfigurationImportSelector.class)
public class ZabbixSenderAutoConfiguration {

	/**
	 * {@link ImportSelector} to add {@link ZabbixSenderType} configuration classes.
	 */
	static class ZabbixSenderConfigurationImportSelector implements ImportSelector {

		@NonNull
		@Override
		public String[] selectImports(@NonNull AnnotationMetadata importingClassMetadata) {
			return Arrays.stream(ZabbixSenderType.values())
				.map(ZabbixSenderMappings::getConfigurationClass)
				.toArray(String[]::new);
		}
	}
}
