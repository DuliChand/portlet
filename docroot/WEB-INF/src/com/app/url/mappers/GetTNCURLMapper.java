package com.app.url.mappers;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;

public class GetTNCURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public boolean isCheckMappingWithPrefix() {
		return false;
	}

}
