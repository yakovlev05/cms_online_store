package ru.yakovlev05.cms.user.microserviceuser.service.impl;

import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.user.microserviceuser.entity.Address;
import ru.yakovlev05.cms.user.microserviceuser.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public String getString(Address address) {
        if (address == null) {
           return null;
        }
        return String.format("%s, г. %s, ул. %s, дом %s, кв. %s",
                address.getState(),
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getFlatNumber());
    }
}
