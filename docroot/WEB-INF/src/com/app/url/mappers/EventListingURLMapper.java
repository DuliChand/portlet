package com.app.url.mappers;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;

public class EventListingURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public boolean isCheckMappingWithPrefix() {
		return false;
	}

}
