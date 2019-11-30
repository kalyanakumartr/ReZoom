package org.hbs.rezoom.admin.bo;

import java.security.InvalidKeyException;
import java.util.List;

import org.apache.kafka.common.errors.InvalidRequestException;
import org.hbs.rezoom.bean.ProducerFormBean;
import org.hbs.rezoom.bean.model.application.CustomerProducer;
import org.hbs.rezoom.util.EnumInterface;
import org.springframework.security.core.Authentication;

public interface ProducerBo
{
	CustomerProducer getProducer(ProducerFormBean request);

	EnumInterface saveProducer(Authentication auth, ProducerFormBean request) throws InvalidKeyException;

	EnumInterface updateProducer(Authentication auth, ProducerFormBean request);

	EnumInterface blockProducer(Authentication auth, ProducerFormBean request);

	List<CustomerProducer> getProducerByName(ProducerFormBean request);

	EnumInterface checkProducerExist(ProducerFormBean request);

	List<CustomerProducer> getProducerList();

	EnumInterface blockProducerById(Authentication auth, ProducerFormBean request);

	EnumInterface deleteProducer(Authentication auth, ProducerFormBean cfBean) throws InvalidRequestException;
}
