/*
 * Copyright © 2018-2019 Bromine0x23 <bromine0x23@163.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package cn.bromine0x23.zabbix.autoconfigure.sender;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

/**
 * Zabbix Sender Condition
 *
 * @author <a href="mailto:bromine0x23@163.com">Bromine0x23</a>
 */
public class ZabbixSenderCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String                   sourceClass = (metadata instanceof ClassMetadata) ? ((ClassMetadata)metadata).getClassName() : "";
		Environment              environment = context.getEnvironment();
		ConditionMessage.Builder message     = ConditionMessage.forCondition("ZabbixSender Condition", sourceClass);
		try {
			Binder                    binder    = Binder.get(environment);
			BindResult<ZabbixSenderType> specified = binder.bind("zabbix.sender.type", ZabbixSenderType.class);
			if (!specified.isBound()) {
				return ConditionOutcome.match(message.because("automatic zabbix sender type"));
			}
			ZabbixSenderType required = ZabbixSenderMappings.getType(((AnnotationMetadata)metadata).getClassName());
			if (specified.get() == required) {
				return ConditionOutcome.match(message.found("zabbix.sender.type property").items(specified.get()));
			}
		} catch (BindException exception) {
			return ConditionOutcome.noMatch(message.found("zabbix.sender.type property").atAll());
		}
		return ConditionOutcome.noMatch(message.didNotFind("zabbix.sender.type property").atAll());
	}
}
