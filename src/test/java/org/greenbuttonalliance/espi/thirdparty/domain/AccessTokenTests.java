/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.thirdparty.domain;

import org.greenbuttonalliance.espi.common.domain.AccessToken;
import org.junit.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessTokenTests {

	@Test
	public void json() throws IOException {
		HttpInputMessage message = mock(HttpInputMessage.class);
		when(message.getBody())
				.thenReturn(
						new ByteArrayInputStream(
								("{"
										+ "\"access_token\":\"6b945882-8349-471a-915f-25e791971248\","
										+ "\"token_type\":\"Bearer\","
										+ "\"expires_in\":43199,"
										+ "\"scope\":\"read write\","
										+ "\"resourceURI\":\"ResourceURI\","
										+ "\"authorizationURI\":\"AuthorizationURI\""
										+ "}").getBytes()));

		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();

		converter.read(AccessToken.class, message);
	}
}
