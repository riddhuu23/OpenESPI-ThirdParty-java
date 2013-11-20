package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;

public interface ResourceRESTRepository {
    IdentifiedObject get(Authorization authorization, String uri);
}
