package ro.veltanmarius.mkcustomer.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ro.veltanmarius.mkcustomer.model.Customer;
import ro.veltanmarius.mkcustomer.model.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer entityToApi(CustomerEntity entity);

    @Mappings({
            @Mapping(target = "version", ignore = true)
    })
    CustomerEntity apiToEntity(Customer api);
}
