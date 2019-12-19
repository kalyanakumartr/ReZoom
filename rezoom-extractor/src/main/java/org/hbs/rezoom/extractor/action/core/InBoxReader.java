package org.hbs.rezoom.extractor.action.core;

import java.io.Serializable;

import org.hbs.rezoom.bean.model.IConfiguration;
import org.hbs.rezoom.event.service.GenericKafkaProducer;

public interface InBoxReader extends Serializable
{

	public void readDataFromChannel(IConfiguration config, GenericKafkaProducer gKafkaProducer);

}
