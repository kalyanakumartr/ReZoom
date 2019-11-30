package org.hbs.rezoom.bean.path;

import org.hbs.rezoom.security.resource.IPath.ERole;
import org.hbs.rezoom.util.EnumInterface;

public interface EnumResourceInterface extends EnumInterface
{
	public String getPath();

	public ERole[] getRoles();

}
