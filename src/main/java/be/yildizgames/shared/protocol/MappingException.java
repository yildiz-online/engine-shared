package be.yildizgames.shared.protocol;

import be.yildizgames.common.exception.business.BusinessException;

class MappingException extends BusinessException {

    MappingException(Exception base) {
        super(base);
    }
}
