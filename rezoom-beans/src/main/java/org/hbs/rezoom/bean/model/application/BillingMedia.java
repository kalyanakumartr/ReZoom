package org.hbs.rezoom.bean.model.application;

import javax.persistence.Embeddable;

import org.hbs.rezoom.bean.model.CommunicationMediaBase;
import org.hbs.rezoom.util.ICRUDBean;

@Embeddable
public class BillingMedia extends CommunicationMediaBase implements ICRUDBean
{

	private static final long serialVersionUID = -5811387771852247610L;
}
