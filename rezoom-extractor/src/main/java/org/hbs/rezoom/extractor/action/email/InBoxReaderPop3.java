package org.hbs.rezoom.extractor.action.email;

import org.hbs.rezoom.bean.model.IConfiguration;
import org.hbs.rezoom.event.service.GenericKafkaProducer;
import org.hbs.rezoom.extractor.bo.ExtractorBo;

public class InBoxReaderPop3 extends InBoxReaderBase
{

	private static final long serialVersionUID = 4813150068221933347L;

	@Override
	public void readDataFromChannel(IConfiguration config, GenericKafkaProducer gKafkaProducer,  ExtractorBo extractorBo)
	{

	}

}
